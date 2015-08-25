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
import com.sae.persistence.domain.Comunicado;
import com.sae.persistence.domain.ObjetoComunicado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ObjetoComunicadoJpaController implements Serializable {

    public ObjetoComunicadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ObjetoComunicado objetoComunicado) throws PreexistingEntityException, Exception {
        if (objetoComunicado.getComunicadoList() == null) {
            objetoComunicado.setComunicadoList(new ArrayList<Comunicado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Comunicado> attachedComunicadoList = new ArrayList<Comunicado>();
            for (Comunicado comunicadoListComunicadoToAttach : objetoComunicado.getComunicadoList()) {
                comunicadoListComunicadoToAttach = em.getReference(comunicadoListComunicadoToAttach.getClass(), comunicadoListComunicadoToAttach.getId());
                attachedComunicadoList.add(comunicadoListComunicadoToAttach);
            }
            objetoComunicado.setComunicadoList(attachedComunicadoList);
            em.persist(objetoComunicado);
            for (Comunicado comunicadoListComunicado : objetoComunicado.getComunicadoList()) {
                ObjetoComunicado oldObjetoOfComunicadoListComunicado = comunicadoListComunicado.getObjeto();
                comunicadoListComunicado.setObjeto(objetoComunicado);
                comunicadoListComunicado = em.merge(comunicadoListComunicado);
                if (oldObjetoOfComunicadoListComunicado != null) {
                    oldObjetoOfComunicadoListComunicado.getComunicadoList().remove(comunicadoListComunicado);
                    oldObjetoOfComunicadoListComunicado = em.merge(oldObjetoOfComunicadoListComunicado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findObjetoComunicado(objetoComunicado.getId()) != null) {
                throw new PreexistingEntityException("ObjetoComunicado " + objetoComunicado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ObjetoComunicado objetoComunicado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ObjetoComunicado persistentObjetoComunicado = em.find(ObjetoComunicado.class, objetoComunicado.getId());
            List<Comunicado> comunicadoListOld = persistentObjetoComunicado.getComunicadoList();
            List<Comunicado> comunicadoListNew = objetoComunicado.getComunicadoList();
            List<Comunicado> attachedComunicadoListNew = new ArrayList<Comunicado>();
            for (Comunicado comunicadoListNewComunicadoToAttach : comunicadoListNew) {
                comunicadoListNewComunicadoToAttach = em.getReference(comunicadoListNewComunicadoToAttach.getClass(), comunicadoListNewComunicadoToAttach.getId());
                attachedComunicadoListNew.add(comunicadoListNewComunicadoToAttach);
            }
            comunicadoListNew = attachedComunicadoListNew;
            objetoComunicado.setComunicadoList(comunicadoListNew);
            objetoComunicado = em.merge(objetoComunicado);
            for (Comunicado comunicadoListOldComunicado : comunicadoListOld) {
                if (!comunicadoListNew.contains(comunicadoListOldComunicado)) {
                    comunicadoListOldComunicado.setObjeto(null);
                    comunicadoListOldComunicado = em.merge(comunicadoListOldComunicado);
                }
            }
            for (Comunicado comunicadoListNewComunicado : comunicadoListNew) {
                if (!comunicadoListOld.contains(comunicadoListNewComunicado)) {
                    ObjetoComunicado oldObjetoOfComunicadoListNewComunicado = comunicadoListNewComunicado.getObjeto();
                    comunicadoListNewComunicado.setObjeto(objetoComunicado);
                    comunicadoListNewComunicado = em.merge(comunicadoListNewComunicado);
                    if (oldObjetoOfComunicadoListNewComunicado != null && !oldObjetoOfComunicadoListNewComunicado.equals(objetoComunicado)) {
                        oldObjetoOfComunicadoListNewComunicado.getComunicadoList().remove(comunicadoListNewComunicado);
                        oldObjetoOfComunicadoListNewComunicado = em.merge(oldObjetoOfComunicadoListNewComunicado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = objetoComunicado.getId();
                if (findObjetoComunicado(id) == null) {
                    throw new NonexistentEntityException("The objetoComunicado with id " + id + " no longer exists.");
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
            ObjetoComunicado objetoComunicado;
            try {
                objetoComunicado = em.getReference(ObjetoComunicado.class, id);
                objetoComunicado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The objetoComunicado with id " + id + " no longer exists.", enfe);
            }
            List<Comunicado> comunicadoList = objetoComunicado.getComunicadoList();
            for (Comunicado comunicadoListComunicado : comunicadoList) {
                comunicadoListComunicado.setObjeto(null);
                comunicadoListComunicado = em.merge(comunicadoListComunicado);
            }
            em.remove(objetoComunicado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ObjetoComunicado> findObjetoComunicadoEntities() {
        return findObjetoComunicadoEntities(true, -1, -1);
    }

    public List<ObjetoComunicado> findObjetoComunicadoEntities(int maxResults, int firstResult) {
        return findObjetoComunicadoEntities(false, maxResults, firstResult);
    }

    private List<ObjetoComunicado> findObjetoComunicadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ObjetoComunicado.class));
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

    public ObjetoComunicado findObjetoComunicado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ObjetoComunicado.class, id);
        } finally {
            em.close();
        }
    }

    public int getObjetoComunicadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ObjetoComunicado> rt = cq.from(ObjetoComunicado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
