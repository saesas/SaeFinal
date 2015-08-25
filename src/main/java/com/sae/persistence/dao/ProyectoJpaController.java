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
import com.sae.persistence.domain.AlcanceProyecto;
import com.sae.persistence.domain.Almacen;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.Bitacora;
import com.sae.persistence.domain.EstadoProyecto;
import com.sae.persistence.domain.ContratoProyectoProveedor;
import com.sae.persistence.domain.Proyecto;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ProyectoJpaController implements Serializable {

    public ProyectoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Proyecto proyecto) throws PreexistingEntityException, Exception {
        if (proyecto.getAlmacenList() == null) {
            proyecto.setAlmacenList(new ArrayList<Almacen>());
        }
        if (proyecto.getBitacoraList() == null) {
            proyecto.setBitacoraList(new ArrayList<Bitacora>());
        }
        if (proyecto.getEstadoProyectoList() == null) {
            proyecto.setEstadoProyectoList(new ArrayList<EstadoProyecto>());
        }
        if (proyecto.getContratoProyectoProveedorList() == null) {
            proyecto.setContratoProyectoProveedorList(new ArrayList<ContratoProyectoProveedor>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            AlcanceProyecto idAlcance = proyecto.getIdAlcance();
            if (idAlcance != null) {
                idAlcance = em.getReference(idAlcance.getClass(), idAlcance.getId());
                proyecto.setIdAlcance(idAlcance);
            }
            List<Almacen> attachedAlmacenList = new ArrayList<Almacen>();
            for (Almacen almacenListAlmacenToAttach : proyecto.getAlmacenList()) {
                almacenListAlmacenToAttach = em.getReference(almacenListAlmacenToAttach.getClass(), almacenListAlmacenToAttach.getId());
                attachedAlmacenList.add(almacenListAlmacenToAttach);
            }
            proyecto.setAlmacenList(attachedAlmacenList);
            List<Bitacora> attachedBitacoraList = new ArrayList<Bitacora>();
            for (Bitacora bitacoraListBitacoraToAttach : proyecto.getBitacoraList()) {
                bitacoraListBitacoraToAttach = em.getReference(bitacoraListBitacoraToAttach.getClass(), bitacoraListBitacoraToAttach.getId());
                attachedBitacoraList.add(bitacoraListBitacoraToAttach);
            }
            proyecto.setBitacoraList(attachedBitacoraList);
            List<EstadoProyecto> attachedEstadoProyectoList = new ArrayList<EstadoProyecto>();
            for (EstadoProyecto estadoProyectoListEstadoProyectoToAttach : proyecto.getEstadoProyectoList()) {
                estadoProyectoListEstadoProyectoToAttach = em.getReference(estadoProyectoListEstadoProyectoToAttach.getClass(), estadoProyectoListEstadoProyectoToAttach.getId());
                attachedEstadoProyectoList.add(estadoProyectoListEstadoProyectoToAttach);
            }
            proyecto.setEstadoProyectoList(attachedEstadoProyectoList);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorList = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedorToAttach : proyecto.getContratoProyectoProveedorList()) {
                contratoProyectoProveedorListContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorList.add(contratoProyectoProveedorListContratoProyectoProveedorToAttach);
            }
            proyecto.setContratoProyectoProveedorList(attachedContratoProyectoProveedorList);
            em.persist(proyecto);
            if (idAlcance != null) {
                idAlcance.getProyectoList().add(proyecto);
                idAlcance = em.merge(idAlcance);
            }
            for (Almacen almacenListAlmacen : proyecto.getAlmacenList()) {
                Proyecto oldIdProyectoOfAlmacenListAlmacen = almacenListAlmacen.getIdProyecto();
                almacenListAlmacen.setIdProyecto(proyecto);
                almacenListAlmacen = em.merge(almacenListAlmacen);
                if (oldIdProyectoOfAlmacenListAlmacen != null) {
                    oldIdProyectoOfAlmacenListAlmacen.getAlmacenList().remove(almacenListAlmacen);
                    oldIdProyectoOfAlmacenListAlmacen = em.merge(oldIdProyectoOfAlmacenListAlmacen);
                }
            }
            for (Bitacora bitacoraListBitacora : proyecto.getBitacoraList()) {
                Proyecto oldIdProyectoOfBitacoraListBitacora = bitacoraListBitacora.getIdProyecto();
                bitacoraListBitacora.setIdProyecto(proyecto);
                bitacoraListBitacora = em.merge(bitacoraListBitacora);
                if (oldIdProyectoOfBitacoraListBitacora != null) {
                    oldIdProyectoOfBitacoraListBitacora.getBitacoraList().remove(bitacoraListBitacora);
                    oldIdProyectoOfBitacoraListBitacora = em.merge(oldIdProyectoOfBitacoraListBitacora);
                }
            }
            for (EstadoProyecto estadoProyectoListEstadoProyecto : proyecto.getEstadoProyectoList()) {
                Proyecto oldIdProyectoOfEstadoProyectoListEstadoProyecto = estadoProyectoListEstadoProyecto.getIdProyecto();
                estadoProyectoListEstadoProyecto.setIdProyecto(proyecto);
                estadoProyectoListEstadoProyecto = em.merge(estadoProyectoListEstadoProyecto);
                if (oldIdProyectoOfEstadoProyectoListEstadoProyecto != null) {
                    oldIdProyectoOfEstadoProyectoListEstadoProyecto.getEstadoProyectoList().remove(estadoProyectoListEstadoProyecto);
                    oldIdProyectoOfEstadoProyectoListEstadoProyecto = em.merge(oldIdProyectoOfEstadoProyectoListEstadoProyecto);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : proyecto.getContratoProyectoProveedorList()) {
                Proyecto oldIdProyectoOfContratoProyectoProveedorListContratoProyectoProveedor = contratoProyectoProveedorListContratoProyectoProveedor.getIdProyecto();
                contratoProyectoProveedorListContratoProyectoProveedor.setIdProyecto(proyecto);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
                if (oldIdProyectoOfContratoProyectoProveedorListContratoProyectoProveedor != null) {
                    oldIdProyectoOfContratoProyectoProveedorListContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListContratoProyectoProveedor);
                    oldIdProyectoOfContratoProyectoProveedorListContratoProyectoProveedor = em.merge(oldIdProyectoOfContratoProyectoProveedorListContratoProyectoProveedor);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProyecto(proyecto.getId()) != null) {
                throw new PreexistingEntityException("Proyecto " + proyecto + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Proyecto proyecto) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Proyecto persistentProyecto = em.find(Proyecto.class, proyecto.getId());
            AlcanceProyecto idAlcanceOld = persistentProyecto.getIdAlcance();
            AlcanceProyecto idAlcanceNew = proyecto.getIdAlcance();
            List<Almacen> almacenListOld = persistentProyecto.getAlmacenList();
            List<Almacen> almacenListNew = proyecto.getAlmacenList();
            List<Bitacora> bitacoraListOld = persistentProyecto.getBitacoraList();
            List<Bitacora> bitacoraListNew = proyecto.getBitacoraList();
            List<EstadoProyecto> estadoProyectoListOld = persistentProyecto.getEstadoProyectoList();
            List<EstadoProyecto> estadoProyectoListNew = proyecto.getEstadoProyectoList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListOld = persistentProyecto.getContratoProyectoProveedorList();
            List<ContratoProyectoProveedor> contratoProyectoProveedorListNew = proyecto.getContratoProyectoProveedorList();
            if (idAlcanceNew != null) {
                idAlcanceNew = em.getReference(idAlcanceNew.getClass(), idAlcanceNew.getId());
                proyecto.setIdAlcance(idAlcanceNew);
            }
            List<Almacen> attachedAlmacenListNew = new ArrayList<Almacen>();
            for (Almacen almacenListNewAlmacenToAttach : almacenListNew) {
                almacenListNewAlmacenToAttach = em.getReference(almacenListNewAlmacenToAttach.getClass(), almacenListNewAlmacenToAttach.getId());
                attachedAlmacenListNew.add(almacenListNewAlmacenToAttach);
            }
            almacenListNew = attachedAlmacenListNew;
            proyecto.setAlmacenList(almacenListNew);
            List<Bitacora> attachedBitacoraListNew = new ArrayList<Bitacora>();
            for (Bitacora bitacoraListNewBitacoraToAttach : bitacoraListNew) {
                bitacoraListNewBitacoraToAttach = em.getReference(bitacoraListNewBitacoraToAttach.getClass(), bitacoraListNewBitacoraToAttach.getId());
                attachedBitacoraListNew.add(bitacoraListNewBitacoraToAttach);
            }
            bitacoraListNew = attachedBitacoraListNew;
            proyecto.setBitacoraList(bitacoraListNew);
            List<EstadoProyecto> attachedEstadoProyectoListNew = new ArrayList<EstadoProyecto>();
            for (EstadoProyecto estadoProyectoListNewEstadoProyectoToAttach : estadoProyectoListNew) {
                estadoProyectoListNewEstadoProyectoToAttach = em.getReference(estadoProyectoListNewEstadoProyectoToAttach.getClass(), estadoProyectoListNewEstadoProyectoToAttach.getId());
                attachedEstadoProyectoListNew.add(estadoProyectoListNewEstadoProyectoToAttach);
            }
            estadoProyectoListNew = attachedEstadoProyectoListNew;
            proyecto.setEstadoProyectoList(estadoProyectoListNew);
            List<ContratoProyectoProveedor> attachedContratoProyectoProveedorListNew = new ArrayList<ContratoProyectoProveedor>();
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedorToAttach : contratoProyectoProveedorListNew) {
                contratoProyectoProveedorListNewContratoProyectoProveedorToAttach = em.getReference(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getClass(), contratoProyectoProveedorListNewContratoProyectoProveedorToAttach.getId());
                attachedContratoProyectoProveedorListNew.add(contratoProyectoProveedorListNewContratoProyectoProveedorToAttach);
            }
            contratoProyectoProveedorListNew = attachedContratoProyectoProveedorListNew;
            proyecto.setContratoProyectoProveedorList(contratoProyectoProveedorListNew);
            proyecto = em.merge(proyecto);
            if (idAlcanceOld != null && !idAlcanceOld.equals(idAlcanceNew)) {
                idAlcanceOld.getProyectoList().remove(proyecto);
                idAlcanceOld = em.merge(idAlcanceOld);
            }
            if (idAlcanceNew != null && !idAlcanceNew.equals(idAlcanceOld)) {
                idAlcanceNew.getProyectoList().add(proyecto);
                idAlcanceNew = em.merge(idAlcanceNew);
            }
            for (Almacen almacenListOldAlmacen : almacenListOld) {
                if (!almacenListNew.contains(almacenListOldAlmacen)) {
                    almacenListOldAlmacen.setIdProyecto(null);
                    almacenListOldAlmacen = em.merge(almacenListOldAlmacen);
                }
            }
            for (Almacen almacenListNewAlmacen : almacenListNew) {
                if (!almacenListOld.contains(almacenListNewAlmacen)) {
                    Proyecto oldIdProyectoOfAlmacenListNewAlmacen = almacenListNewAlmacen.getIdProyecto();
                    almacenListNewAlmacen.setIdProyecto(proyecto);
                    almacenListNewAlmacen = em.merge(almacenListNewAlmacen);
                    if (oldIdProyectoOfAlmacenListNewAlmacen != null && !oldIdProyectoOfAlmacenListNewAlmacen.equals(proyecto)) {
                        oldIdProyectoOfAlmacenListNewAlmacen.getAlmacenList().remove(almacenListNewAlmacen);
                        oldIdProyectoOfAlmacenListNewAlmacen = em.merge(oldIdProyectoOfAlmacenListNewAlmacen);
                    }
                }
            }
            for (Bitacora bitacoraListOldBitacora : bitacoraListOld) {
                if (!bitacoraListNew.contains(bitacoraListOldBitacora)) {
                    bitacoraListOldBitacora.setIdProyecto(null);
                    bitacoraListOldBitacora = em.merge(bitacoraListOldBitacora);
                }
            }
            for (Bitacora bitacoraListNewBitacora : bitacoraListNew) {
                if (!bitacoraListOld.contains(bitacoraListNewBitacora)) {
                    Proyecto oldIdProyectoOfBitacoraListNewBitacora = bitacoraListNewBitacora.getIdProyecto();
                    bitacoraListNewBitacora.setIdProyecto(proyecto);
                    bitacoraListNewBitacora = em.merge(bitacoraListNewBitacora);
                    if (oldIdProyectoOfBitacoraListNewBitacora != null && !oldIdProyectoOfBitacoraListNewBitacora.equals(proyecto)) {
                        oldIdProyectoOfBitacoraListNewBitacora.getBitacoraList().remove(bitacoraListNewBitacora);
                        oldIdProyectoOfBitacoraListNewBitacora = em.merge(oldIdProyectoOfBitacoraListNewBitacora);
                    }
                }
            }
            for (EstadoProyecto estadoProyectoListOldEstadoProyecto : estadoProyectoListOld) {
                if (!estadoProyectoListNew.contains(estadoProyectoListOldEstadoProyecto)) {
                    estadoProyectoListOldEstadoProyecto.setIdProyecto(null);
                    estadoProyectoListOldEstadoProyecto = em.merge(estadoProyectoListOldEstadoProyecto);
                }
            }
            for (EstadoProyecto estadoProyectoListNewEstadoProyecto : estadoProyectoListNew) {
                if (!estadoProyectoListOld.contains(estadoProyectoListNewEstadoProyecto)) {
                    Proyecto oldIdProyectoOfEstadoProyectoListNewEstadoProyecto = estadoProyectoListNewEstadoProyecto.getIdProyecto();
                    estadoProyectoListNewEstadoProyecto.setIdProyecto(proyecto);
                    estadoProyectoListNewEstadoProyecto = em.merge(estadoProyectoListNewEstadoProyecto);
                    if (oldIdProyectoOfEstadoProyectoListNewEstadoProyecto != null && !oldIdProyectoOfEstadoProyectoListNewEstadoProyecto.equals(proyecto)) {
                        oldIdProyectoOfEstadoProyectoListNewEstadoProyecto.getEstadoProyectoList().remove(estadoProyectoListNewEstadoProyecto);
                        oldIdProyectoOfEstadoProyectoListNewEstadoProyecto = em.merge(oldIdProyectoOfEstadoProyectoListNewEstadoProyecto);
                    }
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListOldContratoProyectoProveedor : contratoProyectoProveedorListOld) {
                if (!contratoProyectoProveedorListNew.contains(contratoProyectoProveedorListOldContratoProyectoProveedor)) {
                    contratoProyectoProveedorListOldContratoProyectoProveedor.setIdProyecto(null);
                    contratoProyectoProveedorListOldContratoProyectoProveedor = em.merge(contratoProyectoProveedorListOldContratoProyectoProveedor);
                }
            }
            for (ContratoProyectoProveedor contratoProyectoProveedorListNewContratoProyectoProveedor : contratoProyectoProveedorListNew) {
                if (!contratoProyectoProveedorListOld.contains(contratoProyectoProveedorListNewContratoProyectoProveedor)) {
                    Proyecto oldIdProyectoOfContratoProyectoProveedorListNewContratoProyectoProveedor = contratoProyectoProveedorListNewContratoProyectoProveedor.getIdProyecto();
                    contratoProyectoProveedorListNewContratoProyectoProveedor.setIdProyecto(proyecto);
                    contratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(contratoProyectoProveedorListNewContratoProyectoProveedor);
                    if (oldIdProyectoOfContratoProyectoProveedorListNewContratoProyectoProveedor != null && !oldIdProyectoOfContratoProyectoProveedorListNewContratoProyectoProveedor.equals(proyecto)) {
                        oldIdProyectoOfContratoProyectoProveedorListNewContratoProyectoProveedor.getContratoProyectoProveedorList().remove(contratoProyectoProveedorListNewContratoProyectoProveedor);
                        oldIdProyectoOfContratoProyectoProveedorListNewContratoProyectoProveedor = em.merge(oldIdProyectoOfContratoProyectoProveedorListNewContratoProyectoProveedor);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = proyecto.getId();
                if (findProyecto(id) == null) {
                    throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.");
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
            Proyecto proyecto;
            try {
                proyecto = em.getReference(Proyecto.class, id);
                proyecto.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The proyecto with id " + id + " no longer exists.", enfe);
            }
            AlcanceProyecto idAlcance = proyecto.getIdAlcance();
            if (idAlcance != null) {
                idAlcance.getProyectoList().remove(proyecto);
                idAlcance = em.merge(idAlcance);
            }
            List<Almacen> almacenList = proyecto.getAlmacenList();
            for (Almacen almacenListAlmacen : almacenList) {
                almacenListAlmacen.setIdProyecto(null);
                almacenListAlmacen = em.merge(almacenListAlmacen);
            }
            List<Bitacora> bitacoraList = proyecto.getBitacoraList();
            for (Bitacora bitacoraListBitacora : bitacoraList) {
                bitacoraListBitacora.setIdProyecto(null);
                bitacoraListBitacora = em.merge(bitacoraListBitacora);
            }
            List<EstadoProyecto> estadoProyectoList = proyecto.getEstadoProyectoList();
            for (EstadoProyecto estadoProyectoListEstadoProyecto : estadoProyectoList) {
                estadoProyectoListEstadoProyecto.setIdProyecto(null);
                estadoProyectoListEstadoProyecto = em.merge(estadoProyectoListEstadoProyecto);
            }
            List<ContratoProyectoProveedor> contratoProyectoProveedorList = proyecto.getContratoProyectoProveedorList();
            for (ContratoProyectoProveedor contratoProyectoProveedorListContratoProyectoProveedor : contratoProyectoProveedorList) {
                contratoProyectoProveedorListContratoProyectoProveedor.setIdProyecto(null);
                contratoProyectoProveedorListContratoProyectoProveedor = em.merge(contratoProyectoProveedorListContratoProyectoProveedor);
            }
            em.remove(proyecto);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Proyecto> findProyectoEntities() {
        return findProyectoEntities(true, -1, -1);
    }

    public List<Proyecto> findProyectoEntities(int maxResults, int firstResult) {
        return findProyectoEntities(false, maxResults, firstResult);
    }

    private List<Proyecto> findProyectoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Proyecto.class));
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

    public Proyecto findProyecto(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Proyecto.class, id);
        } finally {
            em.close();
        }
    }

    public int getProyectoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Proyecto> rt = cq.from(Proyecto.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
