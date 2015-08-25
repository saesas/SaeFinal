/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.MatrizRiesgo;
import com.sae.persistence.domain.RegistroMatrizRiesgo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MatrizRiesgoJpaController implements Serializable {

    public MatrizRiesgoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(MatrizRiesgo matrizRiesgo) throws PreexistingEntityException, Exception {
        if (matrizRiesgo.getRegistroMatrizRiesgoList() == null) {
            matrizRiesgo.setRegistroMatrizRiesgoList(new ArrayList<RegistroMatrizRiesgo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Convocatoria idConvocatoria = matrizRiesgo.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                matrizRiesgo.setIdConvocatoria(idConvocatoria);
            }
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoList = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoListRegistroMatrizRiesgoToAttach : matrizRiesgo.getRegistroMatrizRiesgoList()) {
                registroMatrizRiesgoListRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoListRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoListRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoList.add(registroMatrizRiesgoListRegistroMatrizRiesgoToAttach);
            }
            matrizRiesgo.setRegistroMatrizRiesgoList(attachedRegistroMatrizRiesgoList);
            em.persist(matrizRiesgo);
            if (idConvocatoria != null) {
                idConvocatoria.getMatrizRiesgoList().add(matrizRiesgo);
                idConvocatoria = em.merge(idConvocatoria);
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoListRegistroMatrizRiesgo : matrizRiesgo.getRegistroMatrizRiesgoList()) {
                MatrizRiesgo oldIdMatrizOfRegistroMatrizRiesgoListRegistroMatrizRiesgo = registroMatrizRiesgoListRegistroMatrizRiesgo.getIdMatriz();
                registroMatrizRiesgoListRegistroMatrizRiesgo.setIdMatriz(matrizRiesgo);
                registroMatrizRiesgoListRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListRegistroMatrizRiesgo);
                if (oldIdMatrizOfRegistroMatrizRiesgoListRegistroMatrizRiesgo != null) {
                    oldIdMatrizOfRegistroMatrizRiesgoListRegistroMatrizRiesgo.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgoListRegistroMatrizRiesgo);
                    oldIdMatrizOfRegistroMatrizRiesgoListRegistroMatrizRiesgo = em.merge(oldIdMatrizOfRegistroMatrizRiesgoListRegistroMatrizRiesgo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMatrizRiesgo(matrizRiesgo.getId()) != null) {
                throw new PreexistingEntityException("MatrizRiesgo " + matrizRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(MatrizRiesgo matrizRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MatrizRiesgo persistentMatrizRiesgo = em.find(MatrizRiesgo.class, matrizRiesgo.getId());
            Convocatoria idConvocatoriaOld = persistentMatrizRiesgo.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = matrizRiesgo.getIdConvocatoria();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoListOld = persistentMatrizRiesgo.getRegistroMatrizRiesgoList();
            List<RegistroMatrizRiesgo> registroMatrizRiesgoListNew = matrizRiesgo.getRegistroMatrizRiesgoList();
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                matrizRiesgo.setIdConvocatoria(idConvocatoriaNew);
            }
            List<RegistroMatrizRiesgo> attachedRegistroMatrizRiesgoListNew = new ArrayList<RegistroMatrizRiesgo>();
            for (RegistroMatrizRiesgo registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach : registroMatrizRiesgoListNew) {
                registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach = em.getReference(registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach.getClass(), registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach.getId());
                attachedRegistroMatrizRiesgoListNew.add(registroMatrizRiesgoListNewRegistroMatrizRiesgoToAttach);
            }
            registroMatrizRiesgoListNew = attachedRegistroMatrizRiesgoListNew;
            matrizRiesgo.setRegistroMatrizRiesgoList(registroMatrizRiesgoListNew);
            matrizRiesgo = em.merge(matrizRiesgo);
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getMatrizRiesgoList().remove(matrizRiesgo);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getMatrizRiesgoList().add(matrizRiesgo);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoListOldRegistroMatrizRiesgo : registroMatrizRiesgoListOld) {
                if (!registroMatrizRiesgoListNew.contains(registroMatrizRiesgoListOldRegistroMatrizRiesgo)) {
                    registroMatrizRiesgoListOldRegistroMatrizRiesgo.setIdMatriz(null);
                    registroMatrizRiesgoListOldRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListOldRegistroMatrizRiesgo);
                }
            }
            for (RegistroMatrizRiesgo registroMatrizRiesgoListNewRegistroMatrizRiesgo : registroMatrizRiesgoListNew) {
                if (!registroMatrizRiesgoListOld.contains(registroMatrizRiesgoListNewRegistroMatrizRiesgo)) {
                    MatrizRiesgo oldIdMatrizOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo = registroMatrizRiesgoListNewRegistroMatrizRiesgo.getIdMatriz();
                    registroMatrizRiesgoListNewRegistroMatrizRiesgo.setIdMatriz(matrizRiesgo);
                    registroMatrizRiesgoListNewRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListNewRegistroMatrizRiesgo);
                    if (oldIdMatrizOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo != null && !oldIdMatrizOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo.equals(matrizRiesgo)) {
                        oldIdMatrizOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgoListNewRegistroMatrizRiesgo);
                        oldIdMatrizOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo = em.merge(oldIdMatrizOfRegistroMatrizRiesgoListNewRegistroMatrizRiesgo);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = matrizRiesgo.getId();
                if (findMatrizRiesgo(id) == null) {
                    throw new NonexistentEntityException("The matrizRiesgo with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            MatrizRiesgo matrizRiesgo;
            try {
                matrizRiesgo = em.getReference(MatrizRiesgo.class, id);
                matrizRiesgo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The matrizRiesgo with id " + id + " no longer exists.", enfe);
            }
            Convocatoria idConvocatoria = matrizRiesgo.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getMatrizRiesgoList().remove(matrizRiesgo);
                idConvocatoria = em.merge(idConvocatoria);
            }
            List<RegistroMatrizRiesgo> registroMatrizRiesgoList = matrizRiesgo.getRegistroMatrizRiesgoList();
            for (RegistroMatrizRiesgo registroMatrizRiesgoListRegistroMatrizRiesgo : registroMatrizRiesgoList) {
                registroMatrizRiesgoListRegistroMatrizRiesgo.setIdMatriz(null);
                registroMatrizRiesgoListRegistroMatrizRiesgo = em.merge(registroMatrizRiesgoListRegistroMatrizRiesgo);
            }
            em.remove(matrizRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<MatrizRiesgo> findMatrizRiesgoEntities() {
        return findMatrizRiesgoEntities(true, -1, -1);
    }

    public List<MatrizRiesgo> findMatrizRiesgoEntities(int maxResults, int firstResult) {
        return findMatrizRiesgoEntities(false, maxResults, firstResult);
    }

    private List<MatrizRiesgo> findMatrizRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(MatrizRiesgo.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public MatrizRiesgo findMatrizRiesgo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(MatrizRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getMatrizRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<MatrizRiesgo> rt = cq.from(MatrizRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
