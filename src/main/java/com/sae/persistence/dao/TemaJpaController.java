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
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.Normatividad;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.Proceso;
import com.sae.persistence.domain.Tema;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class TemaJpaController implements Serializable {

    public TemaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Tema tema) throws PreexistingEntityException, Exception {
        if (tema.getNormatividadList() == null) {
            tema.setNormatividadList(new ArrayList<Normatividad>());
        }
        if (tema.getProcesoList() == null) {
            tema.setProcesoList(new ArrayList<Proceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Clase idAreaDerecho = tema.getIdAreaDerecho();
            if (idAreaDerecho != null) {
                idAreaDerecho = em.getReference(idAreaDerecho.getClass(), idAreaDerecho.getId());
                tema.setIdAreaDerecho(idAreaDerecho);
            }
            List<Normatividad> attachedNormatividadList = new ArrayList<Normatividad>();
            for (Normatividad normatividadListNormatividadToAttach : tema.getNormatividadList()) {
                normatividadListNormatividadToAttach = em.getReference(normatividadListNormatividadToAttach.getClass(), normatividadListNormatividadToAttach.getId());
                attachedNormatividadList.add(normatividadListNormatividadToAttach);
            }
            tema.setNormatividadList(attachedNormatividadList);
            List<Proceso> attachedProcesoList = new ArrayList<Proceso>();
            for (Proceso procesoListProcesoToAttach : tema.getProcesoList()) {
                procesoListProcesoToAttach = em.getReference(procesoListProcesoToAttach.getClass(), procesoListProcesoToAttach.getId());
                attachedProcesoList.add(procesoListProcesoToAttach);
            }
            tema.setProcesoList(attachedProcesoList);
            em.persist(tema);
            if (idAreaDerecho != null) {
                idAreaDerecho.getTemaList().add(tema);
                idAreaDerecho = em.merge(idAreaDerecho);
            }
            for (Normatividad normatividadListNormatividad : tema.getNormatividadList()) {
                Tema oldIdTemaOfNormatividadListNormatividad = normatividadListNormatividad.getIdTema();
                normatividadListNormatividad.setIdTema(tema);
                normatividadListNormatividad = em.merge(normatividadListNormatividad);
                if (oldIdTemaOfNormatividadListNormatividad != null) {
                    oldIdTemaOfNormatividadListNormatividad.getNormatividadList().remove(normatividadListNormatividad);
                    oldIdTemaOfNormatividadListNormatividad = em.merge(oldIdTemaOfNormatividadListNormatividad);
                }
            }
            for (Proceso procesoListProceso : tema.getProcesoList()) {
                Tema oldIdTemaOfProcesoListProceso = procesoListProceso.getIdTema();
                procesoListProceso.setIdTema(tema);
                procesoListProceso = em.merge(procesoListProceso);
                if (oldIdTemaOfProcesoListProceso != null) {
                    oldIdTemaOfProcesoListProceso.getProcesoList().remove(procesoListProceso);
                    oldIdTemaOfProcesoListProceso = em.merge(oldIdTemaOfProcesoListProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findTema(tema.getId()) != null) {
                throw new PreexistingEntityException("Tema " + tema + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Tema tema) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Tema persistentTema = em.find(Tema.class, tema.getId());
            Clase idAreaDerechoOld = persistentTema.getIdAreaDerecho();
            Clase idAreaDerechoNew = tema.getIdAreaDerecho();
            List<Normatividad> normatividadListOld = persistentTema.getNormatividadList();
            List<Normatividad> normatividadListNew = tema.getNormatividadList();
            List<Proceso> procesoListOld = persistentTema.getProcesoList();
            List<Proceso> procesoListNew = tema.getProcesoList();
            if (idAreaDerechoNew != null) {
                idAreaDerechoNew = em.getReference(idAreaDerechoNew.getClass(), idAreaDerechoNew.getId());
                tema.setIdAreaDerecho(idAreaDerechoNew);
            }
            List<Normatividad> attachedNormatividadListNew = new ArrayList<Normatividad>();
            for (Normatividad normatividadListNewNormatividadToAttach : normatividadListNew) {
                normatividadListNewNormatividadToAttach = em.getReference(normatividadListNewNormatividadToAttach.getClass(), normatividadListNewNormatividadToAttach.getId());
                attachedNormatividadListNew.add(normatividadListNewNormatividadToAttach);
            }
            normatividadListNew = attachedNormatividadListNew;
            tema.setNormatividadList(normatividadListNew);
            List<Proceso> attachedProcesoListNew = new ArrayList<Proceso>();
            for (Proceso procesoListNewProcesoToAttach : procesoListNew) {
                procesoListNewProcesoToAttach = em.getReference(procesoListNewProcesoToAttach.getClass(), procesoListNewProcesoToAttach.getId());
                attachedProcesoListNew.add(procesoListNewProcesoToAttach);
            }
            procesoListNew = attachedProcesoListNew;
            tema.setProcesoList(procesoListNew);
            tema = em.merge(tema);
            if (idAreaDerechoOld != null && !idAreaDerechoOld.equals(idAreaDerechoNew)) {
                idAreaDerechoOld.getTemaList().remove(tema);
                idAreaDerechoOld = em.merge(idAreaDerechoOld);
            }
            if (idAreaDerechoNew != null && !idAreaDerechoNew.equals(idAreaDerechoOld)) {
                idAreaDerechoNew.getTemaList().add(tema);
                idAreaDerechoNew = em.merge(idAreaDerechoNew);
            }
            for (Normatividad normatividadListOldNormatividad : normatividadListOld) {
                if (!normatividadListNew.contains(normatividadListOldNormatividad)) {
                    normatividadListOldNormatividad.setIdTema(null);
                    normatividadListOldNormatividad = em.merge(normatividadListOldNormatividad);
                }
            }
            for (Normatividad normatividadListNewNormatividad : normatividadListNew) {
                if (!normatividadListOld.contains(normatividadListNewNormatividad)) {
                    Tema oldIdTemaOfNormatividadListNewNormatividad = normatividadListNewNormatividad.getIdTema();
                    normatividadListNewNormatividad.setIdTema(tema);
                    normatividadListNewNormatividad = em.merge(normatividadListNewNormatividad);
                    if (oldIdTemaOfNormatividadListNewNormatividad != null && !oldIdTemaOfNormatividadListNewNormatividad.equals(tema)) {
                        oldIdTemaOfNormatividadListNewNormatividad.getNormatividadList().remove(normatividadListNewNormatividad);
                        oldIdTemaOfNormatividadListNewNormatividad = em.merge(oldIdTemaOfNormatividadListNewNormatividad);
                    }
                }
            }
            for (Proceso procesoListOldProceso : procesoListOld) {
                if (!procesoListNew.contains(procesoListOldProceso)) {
                    procesoListOldProceso.setIdTema(null);
                    procesoListOldProceso = em.merge(procesoListOldProceso);
                }
            }
            for (Proceso procesoListNewProceso : procesoListNew) {
                if (!procesoListOld.contains(procesoListNewProceso)) {
                    Tema oldIdTemaOfProcesoListNewProceso = procesoListNewProceso.getIdTema();
                    procesoListNewProceso.setIdTema(tema);
                    procesoListNewProceso = em.merge(procesoListNewProceso);
                    if (oldIdTemaOfProcesoListNewProceso != null && !oldIdTemaOfProcesoListNewProceso.equals(tema)) {
                        oldIdTemaOfProcesoListNewProceso.getProcesoList().remove(procesoListNewProceso);
                        oldIdTemaOfProcesoListNewProceso = em.merge(oldIdTemaOfProcesoListNewProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = tema.getId();
                if (findTema(id) == null) {
                    throw new NonexistentEntityException("The tema with id " + id + " no longer exists.");
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
            Tema tema;
            try {
                tema = em.getReference(Tema.class, id);
                tema.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The tema with id " + id + " no longer exists.", enfe);
            }
            Clase idAreaDerecho = tema.getIdAreaDerecho();
            if (idAreaDerecho != null) {
                idAreaDerecho.getTemaList().remove(tema);
                idAreaDerecho = em.merge(idAreaDerecho);
            }
            List<Normatividad> normatividadList = tema.getNormatividadList();
            for (Normatividad normatividadListNormatividad : normatividadList) {
                normatividadListNormatividad.setIdTema(null);
                normatividadListNormatividad = em.merge(normatividadListNormatividad);
            }
            List<Proceso> procesoList = tema.getProcesoList();
            for (Proceso procesoListProceso : procesoList) {
                procesoListProceso.setIdTema(null);
                procesoListProceso = em.merge(procesoListProceso);
            }
            em.remove(tema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Tema> findTemaEntities() {
        return findTemaEntities(true, -1, -1);
    }

    public List<Tema> findTemaEntities(int maxResults, int firstResult) {
        return findTemaEntities(false, maxResults, firstResult);
    }

    private List<Tema> findTemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Tema.class));
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

    public Tema findTema(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Tema.class, id);
        } finally {
            em.close();
        }
    }

    public int getTemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Tema> rt = cq.from(Tema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
