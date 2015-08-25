/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.ParametroContableFacturacion;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Puc;
import com.sae.persistence.domain.ParametroFacturacionTipoProyecto;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ParametroContableFacturacionJpaController implements Serializable {

    public ParametroContableFacturacionJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ParametroContableFacturacion parametroContableFacturacion) throws PreexistingEntityException, Exception {
        if (parametroContableFacturacion.getParametroFacturacionTipoProyectoList() == null) {
            parametroContableFacturacion.setParametroFacturacionTipoProyectoList(new ArrayList<ParametroFacturacionTipoProyecto>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Puc idPuc = parametroContableFacturacion.getIdPuc();
            if (idPuc != null) {
                idPuc = em.getReference(idPuc.getClass(), idPuc.getId());
                parametroContableFacturacion.setIdPuc(idPuc);
            }
            List<ParametroFacturacionTipoProyecto> attachedParametroFacturacionTipoProyectoList = new ArrayList<ParametroFacturacionTipoProyecto>();
            for (ParametroFacturacionTipoProyecto parametroFacturacionTipoProyectoListParametroFacturacionTipoProyectoToAttach : parametroContableFacturacion.getParametroFacturacionTipoProyectoList()) {
                parametroFacturacionTipoProyectoListParametroFacturacionTipoProyectoToAttach = em.getReference(parametroFacturacionTipoProyectoListParametroFacturacionTipoProyectoToAttach.getClass(), parametroFacturacionTipoProyectoListParametroFacturacionTipoProyectoToAttach.getId());
                attachedParametroFacturacionTipoProyectoList.add(parametroFacturacionTipoProyectoListParametroFacturacionTipoProyectoToAttach);
            }
            parametroContableFacturacion.setParametroFacturacionTipoProyectoList(attachedParametroFacturacionTipoProyectoList);
            em.persist(parametroContableFacturacion);
            if (idPuc != null) {
                idPuc.getParametroContableFacturacionList().add(parametroContableFacturacion);
                idPuc = em.merge(idPuc);
            }
            for (ParametroFacturacionTipoProyecto parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto : parametroContableFacturacion.getParametroFacturacionTipoProyectoList()) {
                ParametroContableFacturacion oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto = parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto.getIdParametroFacturacion();
                parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto.setIdParametroFacturacion(parametroContableFacturacion);
                parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto = em.merge(parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto);
                if (oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto != null) {
                    oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto.getParametroFacturacionTipoProyectoList().remove(parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto);
                    oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto = em.merge(oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findParametroContableFacturacion(parametroContableFacturacion.getId()) != null) {
                throw new PreexistingEntityException("ParametroContableFacturacion " + parametroContableFacturacion + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ParametroContableFacturacion parametroContableFacturacion) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ParametroContableFacturacion persistentParametroContableFacturacion = em.find(ParametroContableFacturacion.class, parametroContableFacturacion.getId());
            Puc idPucOld = persistentParametroContableFacturacion.getIdPuc();
            Puc idPucNew = parametroContableFacturacion.getIdPuc();
            List<ParametroFacturacionTipoProyecto> parametroFacturacionTipoProyectoListOld = persistentParametroContableFacturacion.getParametroFacturacionTipoProyectoList();
            List<ParametroFacturacionTipoProyecto> parametroFacturacionTipoProyectoListNew = parametroContableFacturacion.getParametroFacturacionTipoProyectoList();
            if (idPucNew != null) {
                idPucNew = em.getReference(idPucNew.getClass(), idPucNew.getId());
                parametroContableFacturacion.setIdPuc(idPucNew);
            }
            List<ParametroFacturacionTipoProyecto> attachedParametroFacturacionTipoProyectoListNew = new ArrayList<ParametroFacturacionTipoProyecto>();
            for (ParametroFacturacionTipoProyecto parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyectoToAttach : parametroFacturacionTipoProyectoListNew) {
                parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyectoToAttach = em.getReference(parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyectoToAttach.getClass(), parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyectoToAttach.getId());
                attachedParametroFacturacionTipoProyectoListNew.add(parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyectoToAttach);
            }
            parametroFacturacionTipoProyectoListNew = attachedParametroFacturacionTipoProyectoListNew;
            parametroContableFacturacion.setParametroFacturacionTipoProyectoList(parametroFacturacionTipoProyectoListNew);
            parametroContableFacturacion = em.merge(parametroContableFacturacion);
            if (idPucOld != null && !idPucOld.equals(idPucNew)) {
                idPucOld.getParametroContableFacturacionList().remove(parametroContableFacturacion);
                idPucOld = em.merge(idPucOld);
            }
            if (idPucNew != null && !idPucNew.equals(idPucOld)) {
                idPucNew.getParametroContableFacturacionList().add(parametroContableFacturacion);
                idPucNew = em.merge(idPucNew);
            }
            for (ParametroFacturacionTipoProyecto parametroFacturacionTipoProyectoListOldParametroFacturacionTipoProyecto : parametroFacturacionTipoProyectoListOld) {
                if (!parametroFacturacionTipoProyectoListNew.contains(parametroFacturacionTipoProyectoListOldParametroFacturacionTipoProyecto)) {
                    parametroFacturacionTipoProyectoListOldParametroFacturacionTipoProyecto.setIdParametroFacturacion(null);
                    parametroFacturacionTipoProyectoListOldParametroFacturacionTipoProyecto = em.merge(parametroFacturacionTipoProyectoListOldParametroFacturacionTipoProyecto);
                }
            }
            for (ParametroFacturacionTipoProyecto parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto : parametroFacturacionTipoProyectoListNew) {
                if (!parametroFacturacionTipoProyectoListOld.contains(parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto)) {
                    ParametroContableFacturacion oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto = parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto.getIdParametroFacturacion();
                    parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto.setIdParametroFacturacion(parametroContableFacturacion);
                    parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto = em.merge(parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto);
                    if (oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto != null && !oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto.equals(parametroContableFacturacion)) {
                        oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto.getParametroFacturacionTipoProyectoList().remove(parametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto);
                        oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto = em.merge(oldIdParametroFacturacionOfParametroFacturacionTipoProyectoListNewParametroFacturacionTipoProyecto);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = parametroContableFacturacion.getId();
                if (findParametroContableFacturacion(id) == null) {
                    throw new NonexistentEntityException("The parametroContableFacturacion with id " + id + " no longer exists.");
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
            ParametroContableFacturacion parametroContableFacturacion;
            try {
                parametroContableFacturacion = em.getReference(ParametroContableFacturacion.class, id);
                parametroContableFacturacion.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The parametroContableFacturacion with id " + id + " no longer exists.", enfe);
            }
            Puc idPuc = parametroContableFacturacion.getIdPuc();
            if (idPuc != null) {
                idPuc.getParametroContableFacturacionList().remove(parametroContableFacturacion);
                idPuc = em.merge(idPuc);
            }
            List<ParametroFacturacionTipoProyecto> parametroFacturacionTipoProyectoList = parametroContableFacturacion.getParametroFacturacionTipoProyectoList();
            for (ParametroFacturacionTipoProyecto parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto : parametroFacturacionTipoProyectoList) {
                parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto.setIdParametroFacturacion(null);
                parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto = em.merge(parametroFacturacionTipoProyectoListParametroFacturacionTipoProyecto);
            }
            em.remove(parametroContableFacturacion);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ParametroContableFacturacion> findParametroContableFacturacionEntities() {
        return findParametroContableFacturacionEntities(true, -1, -1);
    }

    public List<ParametroContableFacturacion> findParametroContableFacturacionEntities(int maxResults, int firstResult) {
        return findParametroContableFacturacionEntities(false, maxResults, firstResult);
    }

    private List<ParametroContableFacturacion> findParametroContableFacturacionEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ParametroContableFacturacion.class));
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

    public ParametroContableFacturacion findParametroContableFacturacion(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ParametroContableFacturacion.class, id);
        } finally {
            em.close();
        }
    }

    public int getParametroContableFacturacionCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ParametroContableFacturacion> rt = cq.from(ParametroContableFacturacion.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
