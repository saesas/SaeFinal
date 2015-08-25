/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.sae.persistence.util.JpaUtil; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Actividad;
import com.sae.persistence.domain.ActaCobro;
import com.sae.persistence.domain.ActaCobroActividad;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ActaCobroActividadJpaController implements Serializable {

    public ActaCobroActividadJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ActaCobroActividad actaCobroActividad) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Actividad idActividad = actaCobroActividad.getIdActividad();
            if (idActividad != null) {
                idActividad = em.getReference(idActividad.getClass(), idActividad.getId());
                actaCobroActividad.setIdActividad(idActividad);
            }
            ActaCobro idActaCobro = actaCobroActividad.getIdActaCobro();
            if (idActaCobro != null) {
                idActaCobro = em.getReference(idActaCobro.getClass(), idActaCobro.getId());
                actaCobroActividad.setIdActaCobro(idActaCobro);
            }
            em.persist(actaCobroActividad);
            if (idActividad != null) {
                idActividad.getActaCobroActividadList().add(actaCobroActividad);
                idActividad = em.merge(idActividad);
            }
            if (idActaCobro != null) {
                idActaCobro.getActaCobroActividadList().add(actaCobroActividad);
                idActaCobro = em.merge(idActaCobro);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findActaCobroActividad(actaCobroActividad.getId()) != null) {
                throw new PreexistingEntityException("ActaCobroActividad " + actaCobroActividad + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ActaCobroActividad actaCobroActividad) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ActaCobroActividad persistentActaCobroActividad = em.find(ActaCobroActividad.class, actaCobroActividad.getId());
            Actividad idActividadOld = persistentActaCobroActividad.getIdActividad();
            Actividad idActividadNew = actaCobroActividad.getIdActividad();
            ActaCobro idActaCobroOld = persistentActaCobroActividad.getIdActaCobro();
            ActaCobro idActaCobroNew = actaCobroActividad.getIdActaCobro();
            if (idActividadNew != null) {
                idActividadNew = em.getReference(idActividadNew.getClass(), idActividadNew.getId());
                actaCobroActividad.setIdActividad(idActividadNew);
            }
            if (idActaCobroNew != null) {
                idActaCobroNew = em.getReference(idActaCobroNew.getClass(), idActaCobroNew.getId());
                actaCobroActividad.setIdActaCobro(idActaCobroNew);
            }
            actaCobroActividad = em.merge(actaCobroActividad);
            if (idActividadOld != null && !idActividadOld.equals(idActividadNew)) {
                idActividadOld.getActaCobroActividadList().remove(actaCobroActividad);
                idActividadOld = em.merge(idActividadOld);
            }
            if (idActividadNew != null && !idActividadNew.equals(idActividadOld)) {
                idActividadNew.getActaCobroActividadList().add(actaCobroActividad);
                idActividadNew = em.merge(idActividadNew);
            }
            if (idActaCobroOld != null && !idActaCobroOld.equals(idActaCobroNew)) {
                idActaCobroOld.getActaCobroActividadList().remove(actaCobroActividad);
                idActaCobroOld = em.merge(idActaCobroOld);
            }
            if (idActaCobroNew != null && !idActaCobroNew.equals(idActaCobroOld)) {
                idActaCobroNew.getActaCobroActividadList().add(actaCobroActividad);
                idActaCobroNew = em.merge(idActaCobroNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = actaCobroActividad.getId();
                if (findActaCobroActividad(id) == null) {
                    throw new NonexistentEntityException("The actaCobroActividad with id " + id + " no longer exists.");
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
            ActaCobroActividad actaCobroActividad;
            try {
                actaCobroActividad = em.getReference(ActaCobroActividad.class, id);
                actaCobroActividad.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The actaCobroActividad with id " + id + " no longer exists.", enfe);
            }
            Actividad idActividad = actaCobroActividad.getIdActividad();
            if (idActividad != null) {
                idActividad.getActaCobroActividadList().remove(actaCobroActividad);
                idActividad = em.merge(idActividad);
            }
            ActaCobro idActaCobro = actaCobroActividad.getIdActaCobro();
            if (idActaCobro != null) {
                idActaCobro.getActaCobroActividadList().remove(actaCobroActividad);
                idActaCobro = em.merge(idActaCobro);
            }
            em.remove(actaCobroActividad);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ActaCobroActividad> findActaCobroActividadEntities() {
        return findActaCobroActividadEntities(true, -1, -1);
    }

    public List<ActaCobroActividad> findActaCobroActividadEntities(int maxResults, int firstResult) {
        return findActaCobroActividadEntities(false, maxResults, firstResult);
    }

    private List<ActaCobroActividad> findActaCobroActividadEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ActaCobroActividad.class));
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

    public ActaCobroActividad findActaCobroActividad(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ActaCobroActividad.class, id);
        } finally {
            em.close();
        }
    }

    public int getActaCobroActividadCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ActaCobroActividad> rt = cq.from(ActaCobroActividad.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
