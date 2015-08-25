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
import com.sae.persistence.domain.TipoIva;
import com.sae.persistence.domain.NivelPuc;
import com.sae.persistence.domain.ParametroContableProyecto;
import com.sae.persistence.domain.ParametroContableTipoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ParametroContableProyectoJpaController implements Serializable {

    public ParametroContableProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroContableProyecto parametroContableProyecto) throws PreexistingEntityException, Exception {
        if (parametroContableProyecto.getParametroContableTipoProyectoList() == null) {
            parametroContableProyecto.setParametroContableTipoProyectoList(new ArrayList<ParametroContableTipoProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoIva idCausacionIva = parametroContableProyecto.getIdCausacionIva();
            if (idCausacionIva != null) {
                idCausacionIva = em.getReference(idCausacionIva.getClass(), idCausacionIva.getId());
                parametroContableProyecto.setIdCausacionIva(idCausacionIva);
            }
            NivelPuc idNivelPuc = parametroContableProyecto.getIdNivelPuc();
            if (idNivelPuc != null) {
                idNivelPuc = em.getReference(idNivelPuc.getClass(), idNivelPuc.getId());
                parametroContableProyecto.setIdNivelPuc(idNivelPuc);
            }
            List<ParametroContableTipoProyecto> attachedParametroContableTipoProyectoList = new ArrayList<ParametroContableTipoProyecto>();
            for (ParametroContableTipoProyecto parametroContableTipoProyectoListParametroContableTipoProyectoToAttach : parametroContableProyecto.getParametroContableTipoProyectoList()) {
                parametroContableTipoProyectoListParametroContableTipoProyectoToAttach = em.getReference(parametroContableTipoProyectoListParametroContableTipoProyectoToAttach.getClass(), parametroContableTipoProyectoListParametroContableTipoProyectoToAttach.getId());
                attachedParametroContableTipoProyectoList.add(parametroContableTipoProyectoListParametroContableTipoProyectoToAttach);
            }
            parametroContableProyecto.setParametroContableTipoProyectoList(attachedParametroContableTipoProyectoList);
            em.persist(parametroContableProyecto);
            if (idCausacionIva != null) {
                idCausacionIva.getParametroContableProyectoList().add(parametroContableProyecto);
                idCausacionIva = em.merge(idCausacionIva);
            }
            if (idNivelPuc != null) {
                idNivelPuc.getParametroContableProyectoList().add(parametroContableProyecto);
                idNivelPuc = em.merge(idNivelPuc);
            }
            for (ParametroContableTipoProyecto parametroContableTipoProyectoListParametroContableTipoProyecto : parametroContableProyecto.getParametroContableTipoProyectoList()) {
                ParametroContableProyecto oldIdParametroOfParametroContableTipoProyectoListParametroContableTipoProyecto = parametroContableTipoProyectoListParametroContableTipoProyecto.getIdParametro();
                parametroContableTipoProyectoListParametroContableTipoProyecto.setIdParametro(parametroContableProyecto);
                parametroContableTipoProyectoListParametroContableTipoProyecto = em.merge(parametroContableTipoProyectoListParametroContableTipoProyecto);
                if (oldIdParametroOfParametroContableTipoProyectoListParametroContableTipoProyecto != null) {
                    oldIdParametroOfParametroContableTipoProyectoListParametroContableTipoProyecto.getParametroContableTipoProyectoList().remove(parametroContableTipoProyectoListParametroContableTipoProyecto);
                    oldIdParametroOfParametroContableTipoProyectoListParametroContableTipoProyecto = em.merge(oldIdParametroOfParametroContableTipoProyectoListParametroContableTipoProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametroContableProyecto(parametroContableProyecto.getId()) != null) {
                throw new PreexistingEntityException("ParametroContableProyecto " + parametroContableProyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroContableProyecto parametroContableProyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroContableProyecto persistentParametroContableProyecto = em.find(ParametroContableProyecto.class, parametroContableProyecto.getId());
            TipoIva idCausacionIvaOld = persistentParametroContableProyecto.getIdCausacionIva();
            TipoIva idCausacionIvaNew = parametroContableProyecto.getIdCausacionIva();
            NivelPuc idNivelPucOld = persistentParametroContableProyecto.getIdNivelPuc();
            NivelPuc idNivelPucNew = parametroContableProyecto.getIdNivelPuc();
            List<ParametroContableTipoProyecto> parametroContableTipoProyectoListOld = persistentParametroContableProyecto.getParametroContableTipoProyectoList();
            List<ParametroContableTipoProyecto> parametroContableTipoProyectoListNew = parametroContableProyecto.getParametroContableTipoProyectoList();
            if (idCausacionIvaNew != null) {
                idCausacionIvaNew = em.getReference(idCausacionIvaNew.getClass(), idCausacionIvaNew.getId());
                parametroContableProyecto.setIdCausacionIva(idCausacionIvaNew);
            }
            if (idNivelPucNew != null) {
                idNivelPucNew = em.getReference(idNivelPucNew.getClass(), idNivelPucNew.getId());
                parametroContableProyecto.setIdNivelPuc(idNivelPucNew);
            }
            List<ParametroContableTipoProyecto> attachedParametroContableTipoProyectoListNew = new ArrayList<ParametroContableTipoProyecto>();
            for (ParametroContableTipoProyecto parametroContableTipoProyectoListNewParametroContableTipoProyectoToAttach : parametroContableTipoProyectoListNew) {
                parametroContableTipoProyectoListNewParametroContableTipoProyectoToAttach = em.getReference(parametroContableTipoProyectoListNewParametroContableTipoProyectoToAttach.getClass(), parametroContableTipoProyectoListNewParametroContableTipoProyectoToAttach.getId());
                attachedParametroContableTipoProyectoListNew.add(parametroContableTipoProyectoListNewParametroContableTipoProyectoToAttach);
            }
            parametroContableTipoProyectoListNew = attachedParametroContableTipoProyectoListNew;
            parametroContableProyecto.setParametroContableTipoProyectoList(parametroContableTipoProyectoListNew);
            parametroContableProyecto = em.merge(parametroContableProyecto);
            if (idCausacionIvaOld != null && !idCausacionIvaOld.equals(idCausacionIvaNew)) {
                idCausacionIvaOld.getParametroContableProyectoList().remove(parametroContableProyecto);
                idCausacionIvaOld = em.merge(idCausacionIvaOld);
            }
            if (idCausacionIvaNew != null && !idCausacionIvaNew.equals(idCausacionIvaOld)) {
                idCausacionIvaNew.getParametroContableProyectoList().add(parametroContableProyecto);
                idCausacionIvaNew = em.merge(idCausacionIvaNew);
            }
            if (idNivelPucOld != null && !idNivelPucOld.equals(idNivelPucNew)) {
                idNivelPucOld.getParametroContableProyectoList().remove(parametroContableProyecto);
                idNivelPucOld = em.merge(idNivelPucOld);
            }
            if (idNivelPucNew != null && !idNivelPucNew.equals(idNivelPucOld)) {
                idNivelPucNew.getParametroContableProyectoList().add(parametroContableProyecto);
                idNivelPucNew = em.merge(idNivelPucNew);
            }
            for (ParametroContableTipoProyecto parametroContableTipoProyectoListOldParametroContableTipoProyecto : parametroContableTipoProyectoListOld) {
                if (!parametroContableTipoProyectoListNew.contains(parametroContableTipoProyectoListOldParametroContableTipoProyecto)) {
                    parametroContableTipoProyectoListOldParametroContableTipoProyecto.setIdParametro(null);
                    parametroContableTipoProyectoListOldParametroContableTipoProyecto = em.merge(parametroContableTipoProyectoListOldParametroContableTipoProyecto);
                }
            }
            for (ParametroContableTipoProyecto parametroContableTipoProyectoListNewParametroContableTipoProyecto : parametroContableTipoProyectoListNew) {
                if (!parametroContableTipoProyectoListOld.contains(parametroContableTipoProyectoListNewParametroContableTipoProyecto)) {
                    ParametroContableProyecto oldIdParametroOfParametroContableTipoProyectoListNewParametroContableTipoProyecto = parametroContableTipoProyectoListNewParametroContableTipoProyecto.getIdParametro();
                    parametroContableTipoProyectoListNewParametroContableTipoProyecto.setIdParametro(parametroContableProyecto);
                    parametroContableTipoProyectoListNewParametroContableTipoProyecto = em.merge(parametroContableTipoProyectoListNewParametroContableTipoProyecto);
                    if (oldIdParametroOfParametroContableTipoProyectoListNewParametroContableTipoProyecto != null && !oldIdParametroOfParametroContableTipoProyectoListNewParametroContableTipoProyecto.equals(parametroContableProyecto)) {
                        oldIdParametroOfParametroContableTipoProyectoListNewParametroContableTipoProyecto.getParametroContableTipoProyectoList().remove(parametroContableTipoProyectoListNewParametroContableTipoProyecto);
                        oldIdParametroOfParametroContableTipoProyectoListNewParametroContableTipoProyecto = em.merge(oldIdParametroOfParametroContableTipoProyectoListNewParametroContableTipoProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametroContableProyecto.getId();
                if (findParametroContableProyecto(id) == null) {
                    throw new NonexistentEntityException("The parametroContableProyecto with id " + id + " no longer exists.");
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
            ParametroContableProyecto parametroContableProyecto;
            try {
                parametroContableProyecto = em.getReference(ParametroContableProyecto.class, id);
                parametroContableProyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroContableProyecto with id " + id + " no longer exists.", enfe);
            }
            TipoIva idCausacionIva = parametroContableProyecto.getIdCausacionIva();
            if (idCausacionIva != null) {
                idCausacionIva.getParametroContableProyectoList().remove(parametroContableProyecto);
                idCausacionIva = em.merge(idCausacionIva);
            }
            NivelPuc idNivelPuc = parametroContableProyecto.getIdNivelPuc();
            if (idNivelPuc != null) {
                idNivelPuc.getParametroContableProyectoList().remove(parametroContableProyecto);
                idNivelPuc = em.merge(idNivelPuc);
            }
            List<ParametroContableTipoProyecto> parametroContableTipoProyectoList = parametroContableProyecto.getParametroContableTipoProyectoList();
            for (ParametroContableTipoProyecto parametroContableTipoProyectoListParametroContableTipoProyecto : parametroContableTipoProyectoList) {
                parametroContableTipoProyectoListParametroContableTipoProyecto.setIdParametro(null);
                parametroContableTipoProyectoListParametroContableTipoProyecto = em.merge(parametroContableTipoProyectoListParametroContableTipoProyecto);
            }
            em.remove(parametroContableProyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroContableProyecto> findParametroContableProyectoEntities() {
        return findParametroContableProyectoEntities(true, -1, -1);
    }

    public List<ParametroContableProyecto> findParametroContableProyectoEntities(int maxResults, int firstResult) {
        return findParametroContableProyectoEntities(false, maxResults, firstResult);
    }

    private List<ParametroContableProyecto> findParametroContableProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroContableProyecto.class));
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

    public ParametroContableProyecto findParametroContableProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroContableProyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroContableProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroContableProyecto> rt = cq.from(ParametroContableProyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
