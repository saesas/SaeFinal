/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Almacen;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Proyecto;
import com.sae.persistence.domain.Bodega;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class AlmacenJpaController implements Serializable {

    public AlmacenJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Almacen almacen) throws PreexistingEntityException, Exception {
        if (almacen.getBodegaList() == null) {
            almacen.setBodegaList(new ArrayList<Bodega>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto idProyecto = almacen.getIdProyecto();
            if (idProyecto != null) {
                idProyecto = em.getReference(idProyecto.getClass(), idProyecto.getId());
                almacen.setIdProyecto(idProyecto);
            }
            List<Bodega> attachedBodegaList = new ArrayList<Bodega>();
            for (Bodega bodegaListBodegaToAttach : almacen.getBodegaList()) {
                bodegaListBodegaToAttach = em.getReference(bodegaListBodegaToAttach.getClass(), bodegaListBodegaToAttach.getId());
                attachedBodegaList.add(bodegaListBodegaToAttach);
            }
            almacen.setBodegaList(attachedBodegaList);
            em.persist(almacen);
            if (idProyecto != null) {
                idProyecto.getAlmacenList().add(almacen);
                idProyecto = em.merge(idProyecto);
            }
            for (Bodega bodegaListBodega : almacen.getBodegaList()) {
                Almacen oldIdAlmacenOfBodegaListBodega = bodegaListBodega.getIdAlmacen();
                bodegaListBodega.setIdAlmacen(almacen);
                bodegaListBodega = em.merge(bodegaListBodega);
                if (oldIdAlmacenOfBodegaListBodega != null) {
                    oldIdAlmacenOfBodegaListBodega.getBodegaList().remove(bodegaListBodega);
                    oldIdAlmacenOfBodegaListBodega = em.merge(oldIdAlmacenOfBodegaListBodega);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findAlmacen(almacen.getId()) != null) {
                throw new PreexistingEntityException("Almacen " + almacen + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Almacen almacen) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Almacen persistentAlmacen = em.find(Almacen.class, almacen.getId());
            Proyecto idProyectoOld = persistentAlmacen.getIdProyecto();
            Proyecto idProyectoNew = almacen.getIdProyecto();
            List<Bodega> bodegaListOld = persistentAlmacen.getBodegaList();
            List<Bodega> bodegaListNew = almacen.getBodegaList();
            if (idProyectoNew != null) {
                idProyectoNew = em.getReference(idProyectoNew.getClass(), idProyectoNew.getId());
                almacen.setIdProyecto(idProyectoNew);
            }
            List<Bodega> attachedBodegaListNew = new ArrayList<Bodega>();
            for (Bodega bodegaListNewBodegaToAttach : bodegaListNew) {
                bodegaListNewBodegaToAttach = em.getReference(bodegaListNewBodegaToAttach.getClass(), bodegaListNewBodegaToAttach.getId());
                attachedBodegaListNew.add(bodegaListNewBodegaToAttach);
            }
            bodegaListNew = attachedBodegaListNew;
            almacen.setBodegaList(bodegaListNew);
            almacen = em.merge(almacen);
            if (idProyectoOld != null && !idProyectoOld.equals(idProyectoNew)) {
                idProyectoOld.getAlmacenList().remove(almacen);
                idProyectoOld = em.merge(idProyectoOld);
            }
            if (idProyectoNew != null && !idProyectoNew.equals(idProyectoOld)) {
                idProyectoNew.getAlmacenList().add(almacen);
                idProyectoNew = em.merge(idProyectoNew);
            }
            for (Bodega bodegaListOldBodega : bodegaListOld) {
                if (!bodegaListNew.contains(bodegaListOldBodega)) {
                    bodegaListOldBodega.setIdAlmacen(null);
                    bodegaListOldBodega = em.merge(bodegaListOldBodega);
                }
            }
            for (Bodega bodegaListNewBodega : bodegaListNew) {
                if (!bodegaListOld.contains(bodegaListNewBodega)) {
                    Almacen oldIdAlmacenOfBodegaListNewBodega = bodegaListNewBodega.getIdAlmacen();
                    bodegaListNewBodega.setIdAlmacen(almacen);
                    bodegaListNewBodega = em.merge(bodegaListNewBodega);
                    if (oldIdAlmacenOfBodegaListNewBodega != null && !oldIdAlmacenOfBodegaListNewBodega.equals(almacen)) {
                        oldIdAlmacenOfBodegaListNewBodega.getBodegaList().remove(bodegaListNewBodega);
                        oldIdAlmacenOfBodegaListNewBodega = em.merge(oldIdAlmacenOfBodegaListNewBodega);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = almacen.getId();
                if (findAlmacen(id) == null) {
                    throw new NonexistentEntityException("The almacen with id " + id + " no longer exists.");
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
            Almacen almacen;
            try {
                almacen = em.getReference(Almacen.class, id);
                almacen.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The almacen with id " + id + " no longer exists.", enfe);
            }
            Proyecto idProyecto = almacen.getIdProyecto();
            if (idProyecto != null) {
                idProyecto.getAlmacenList().remove(almacen);
                idProyecto = em.merge(idProyecto);
            }
            List<Bodega> bodegaList = almacen.getBodegaList();
            for (Bodega bodegaListBodega : bodegaList) {
                bodegaListBodega.setIdAlmacen(null);
                bodegaListBodega = em.merge(bodegaListBodega);
            }
            em.remove(almacen);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Almacen> findAlmacenEntities() {
        return findAlmacenEntities(true, -1, -1);
    }

    public List<Almacen> findAlmacenEntities(int maxResults, int firstResult) {
        return findAlmacenEntities(false, maxResults, firstResult);
    }

    private List<Almacen> findAlmacenEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Almacen.class));
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

    public Almacen findAlmacen(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Almacen.class, id);
        } finally {
            em.close();
        }
    }

    public int getAlmacenCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Almacen> rt = cq.from(Almacen.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
