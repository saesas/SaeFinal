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
import com.sae.persistence.domain.TipoSolucionProceso;
import com.sae.persistence.domain.Proceso;
import com.sae.persistence.domain.FormaProceso;
import com.sae.persistence.domain.EtapaProceso;
import com.sae.persistence.domain.Clase;
import com.sae.persistence.domain.SeguimientoProceso;
import com.sae.persistence.domain.SolucionProceso;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class SeguimientoProcesoJpaController implements Serializable {

    public SeguimientoProcesoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(SeguimientoProceso seguimientoProceso) throws PreexistingEntityException, Exception {
        if (seguimientoProceso.getSolucionProcesoList() == null) {
            seguimientoProceso.setSolucionProcesoList(new ArrayList<SolucionProceso>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoSolucionProceso idSolucion = seguimientoProceso.getIdSolucion();
            if (idSolucion != null) {
                idSolucion = em.getReference(idSolucion.getClass(), idSolucion.getId());
                seguimientoProceso.setIdSolucion(idSolucion);
            }
            Proceso idProceso = seguimientoProceso.getIdProceso();
            if (idProceso != null) {
                idProceso = em.getReference(idProceso.getClass(), idProceso.getId());
                seguimientoProceso.setIdProceso(idProceso);
            }
            FormaProceso idForma = seguimientoProceso.getIdForma();
            if (idForma != null) {
                idForma = em.getReference(idForma.getClass(), idForma.getId());
                seguimientoProceso.setIdForma(idForma);
            }
            EtapaProceso idEtapa = seguimientoProceso.getIdEtapa();
            if (idEtapa != null) {
                idEtapa = em.getReference(idEtapa.getClass(), idEtapa.getId());
                seguimientoProceso.setIdEtapa(idEtapa);
            }
            Clase idInstancia = seguimientoProceso.getIdInstancia();
            if (idInstancia != null) {
                idInstancia = em.getReference(idInstancia.getClass(), idInstancia.getId());
                seguimientoProceso.setIdInstancia(idInstancia);
            }
            List<SolucionProceso> attachedSolucionProcesoList = new ArrayList<SolucionProceso>();
            for (SolucionProceso solucionProcesoListSolucionProcesoToAttach : seguimientoProceso.getSolucionProcesoList()) {
                solucionProcesoListSolucionProcesoToAttach = em.getReference(solucionProcesoListSolucionProcesoToAttach.getClass(), solucionProcesoListSolucionProcesoToAttach.getId());
                attachedSolucionProcesoList.add(solucionProcesoListSolucionProcesoToAttach);
            }
            seguimientoProceso.setSolucionProcesoList(attachedSolucionProcesoList);
            em.persist(seguimientoProceso);
            if (idSolucion != null) {
                idSolucion.getSeguimientoProcesoList().add(seguimientoProceso);
                idSolucion = em.merge(idSolucion);
            }
            if (idProceso != null) {
                idProceso.getSeguimientoProcesoList().add(seguimientoProceso);
                idProceso = em.merge(idProceso);
            }
            if (idForma != null) {
                idForma.getSeguimientoProcesoList().add(seguimientoProceso);
                idForma = em.merge(idForma);
            }
            if (idEtapa != null) {
                idEtapa.getSeguimientoProcesoList().add(seguimientoProceso);
                idEtapa = em.merge(idEtapa);
            }
            if (idInstancia != null) {
                idInstancia.getSeguimientoProcesoList().add(seguimientoProceso);
                idInstancia = em.merge(idInstancia);
            }
            for (SolucionProceso solucionProcesoListSolucionProceso : seguimientoProceso.getSolucionProcesoList()) {
                SeguimientoProceso oldIdSeguimientoOfSolucionProcesoListSolucionProceso = solucionProcesoListSolucionProceso.getIdSeguimiento();
                solucionProcesoListSolucionProceso.setIdSeguimiento(seguimientoProceso);
                solucionProcesoListSolucionProceso = em.merge(solucionProcesoListSolucionProceso);
                if (oldIdSeguimientoOfSolucionProcesoListSolucionProceso != null) {
                    oldIdSeguimientoOfSolucionProcesoListSolucionProceso.getSolucionProcesoList().remove(solucionProcesoListSolucionProceso);
                    oldIdSeguimientoOfSolucionProcesoListSolucionProceso = em.merge(oldIdSeguimientoOfSolucionProcesoListSolucionProceso);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findSeguimientoProceso(seguimientoProceso.getId()) != null) {
                throw new PreexistingEntityException("SeguimientoProceso " + seguimientoProceso + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(SeguimientoProceso seguimientoProceso) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SeguimientoProceso persistentSeguimientoProceso = em.find(SeguimientoProceso.class, seguimientoProceso.getId());
            TipoSolucionProceso idSolucionOld = persistentSeguimientoProceso.getIdSolucion();
            TipoSolucionProceso idSolucionNew = seguimientoProceso.getIdSolucion();
            Proceso idProcesoOld = persistentSeguimientoProceso.getIdProceso();
            Proceso idProcesoNew = seguimientoProceso.getIdProceso();
            FormaProceso idFormaOld = persistentSeguimientoProceso.getIdForma();
            FormaProceso idFormaNew = seguimientoProceso.getIdForma();
            EtapaProceso idEtapaOld = persistentSeguimientoProceso.getIdEtapa();
            EtapaProceso idEtapaNew = seguimientoProceso.getIdEtapa();
            Clase idInstanciaOld = persistentSeguimientoProceso.getIdInstancia();
            Clase idInstanciaNew = seguimientoProceso.getIdInstancia();
            List<SolucionProceso> solucionProcesoListOld = persistentSeguimientoProceso.getSolucionProcesoList();
            List<SolucionProceso> solucionProcesoListNew = seguimientoProceso.getSolucionProcesoList();
            if (idSolucionNew != null) {
                idSolucionNew = em.getReference(idSolucionNew.getClass(), idSolucionNew.getId());
                seguimientoProceso.setIdSolucion(idSolucionNew);
            }
            if (idProcesoNew != null) {
                idProcesoNew = em.getReference(idProcesoNew.getClass(), idProcesoNew.getId());
                seguimientoProceso.setIdProceso(idProcesoNew);
            }
            if (idFormaNew != null) {
                idFormaNew = em.getReference(idFormaNew.getClass(), idFormaNew.getId());
                seguimientoProceso.setIdForma(idFormaNew);
            }
            if (idEtapaNew != null) {
                idEtapaNew = em.getReference(idEtapaNew.getClass(), idEtapaNew.getId());
                seguimientoProceso.setIdEtapa(idEtapaNew);
            }
            if (idInstanciaNew != null) {
                idInstanciaNew = em.getReference(idInstanciaNew.getClass(), idInstanciaNew.getId());
                seguimientoProceso.setIdInstancia(idInstanciaNew);
            }
            List<SolucionProceso> attachedSolucionProcesoListNew = new ArrayList<SolucionProceso>();
            for (SolucionProceso solucionProcesoListNewSolucionProcesoToAttach : solucionProcesoListNew) {
                solucionProcesoListNewSolucionProcesoToAttach = em.getReference(solucionProcesoListNewSolucionProcesoToAttach.getClass(), solucionProcesoListNewSolucionProcesoToAttach.getId());
                attachedSolucionProcesoListNew.add(solucionProcesoListNewSolucionProcesoToAttach);
            }
            solucionProcesoListNew = attachedSolucionProcesoListNew;
            seguimientoProceso.setSolucionProcesoList(solucionProcesoListNew);
            seguimientoProceso = em.merge(seguimientoProceso);
            if (idSolucionOld != null && !idSolucionOld.equals(idSolucionNew)) {
                idSolucionOld.getSeguimientoProcesoList().remove(seguimientoProceso);
                idSolucionOld = em.merge(idSolucionOld);
            }
            if (idSolucionNew != null && !idSolucionNew.equals(idSolucionOld)) {
                idSolucionNew.getSeguimientoProcesoList().add(seguimientoProceso);
                idSolucionNew = em.merge(idSolucionNew);
            }
            if (idProcesoOld != null && !idProcesoOld.equals(idProcesoNew)) {
                idProcesoOld.getSeguimientoProcesoList().remove(seguimientoProceso);
                idProcesoOld = em.merge(idProcesoOld);
            }
            if (idProcesoNew != null && !idProcesoNew.equals(idProcesoOld)) {
                idProcesoNew.getSeguimientoProcesoList().add(seguimientoProceso);
                idProcesoNew = em.merge(idProcesoNew);
            }
            if (idFormaOld != null && !idFormaOld.equals(idFormaNew)) {
                idFormaOld.getSeguimientoProcesoList().remove(seguimientoProceso);
                idFormaOld = em.merge(idFormaOld);
            }
            if (idFormaNew != null && !idFormaNew.equals(idFormaOld)) {
                idFormaNew.getSeguimientoProcesoList().add(seguimientoProceso);
                idFormaNew = em.merge(idFormaNew);
            }
            if (idEtapaOld != null && !idEtapaOld.equals(idEtapaNew)) {
                idEtapaOld.getSeguimientoProcesoList().remove(seguimientoProceso);
                idEtapaOld = em.merge(idEtapaOld);
            }
            if (idEtapaNew != null && !idEtapaNew.equals(idEtapaOld)) {
                idEtapaNew.getSeguimientoProcesoList().add(seguimientoProceso);
                idEtapaNew = em.merge(idEtapaNew);
            }
            if (idInstanciaOld != null && !idInstanciaOld.equals(idInstanciaNew)) {
                idInstanciaOld.getSeguimientoProcesoList().remove(seguimientoProceso);
                idInstanciaOld = em.merge(idInstanciaOld);
            }
            if (idInstanciaNew != null && !idInstanciaNew.equals(idInstanciaOld)) {
                idInstanciaNew.getSeguimientoProcesoList().add(seguimientoProceso);
                idInstanciaNew = em.merge(idInstanciaNew);
            }
            for (SolucionProceso solucionProcesoListOldSolucionProceso : solucionProcesoListOld) {
                if (!solucionProcesoListNew.contains(solucionProcesoListOldSolucionProceso)) {
                    solucionProcesoListOldSolucionProceso.setIdSeguimiento(null);
                    solucionProcesoListOldSolucionProceso = em.merge(solucionProcesoListOldSolucionProceso);
                }
            }
            for (SolucionProceso solucionProcesoListNewSolucionProceso : solucionProcesoListNew) {
                if (!solucionProcesoListOld.contains(solucionProcesoListNewSolucionProceso)) {
                    SeguimientoProceso oldIdSeguimientoOfSolucionProcesoListNewSolucionProceso = solucionProcesoListNewSolucionProceso.getIdSeguimiento();
                    solucionProcesoListNewSolucionProceso.setIdSeguimiento(seguimientoProceso);
                    solucionProcesoListNewSolucionProceso = em.merge(solucionProcesoListNewSolucionProceso);
                    if (oldIdSeguimientoOfSolucionProcesoListNewSolucionProceso != null && !oldIdSeguimientoOfSolucionProcesoListNewSolucionProceso.equals(seguimientoProceso)) {
                        oldIdSeguimientoOfSolucionProcesoListNewSolucionProceso.getSolucionProcesoList().remove(solucionProcesoListNewSolucionProceso);
                        oldIdSeguimientoOfSolucionProcesoListNewSolucionProceso = em.merge(oldIdSeguimientoOfSolucionProcesoListNewSolucionProceso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = seguimientoProceso.getId();
                if (findSeguimientoProceso(id) == null) {
                    throw new NonexistentEntityException("The seguimientoProceso with id " + id + " no longer exists.");
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
            SeguimientoProceso seguimientoProceso;
            try {
                seguimientoProceso = em.getReference(SeguimientoProceso.class, id);
                seguimientoProceso.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The seguimientoProceso with id " + id + " no longer exists.", enfe);
            }
            TipoSolucionProceso idSolucion = seguimientoProceso.getIdSolucion();
            if (idSolucion != null) {
                idSolucion.getSeguimientoProcesoList().remove(seguimientoProceso);
                idSolucion = em.merge(idSolucion);
            }
            Proceso idProceso = seguimientoProceso.getIdProceso();
            if (idProceso != null) {
                idProceso.getSeguimientoProcesoList().remove(seguimientoProceso);
                idProceso = em.merge(idProceso);
            }
            FormaProceso idForma = seguimientoProceso.getIdForma();
            if (idForma != null) {
                idForma.getSeguimientoProcesoList().remove(seguimientoProceso);
                idForma = em.merge(idForma);
            }
            EtapaProceso idEtapa = seguimientoProceso.getIdEtapa();
            if (idEtapa != null) {
                idEtapa.getSeguimientoProcesoList().remove(seguimientoProceso);
                idEtapa = em.merge(idEtapa);
            }
            Clase idInstancia = seguimientoProceso.getIdInstancia();
            if (idInstancia != null) {
                idInstancia.getSeguimientoProcesoList().remove(seguimientoProceso);
                idInstancia = em.merge(idInstancia);
            }
            List<SolucionProceso> solucionProcesoList = seguimientoProceso.getSolucionProcesoList();
            for (SolucionProceso solucionProcesoListSolucionProceso : solucionProcesoList) {
                solucionProcesoListSolucionProceso.setIdSeguimiento(null);
                solucionProcesoListSolucionProceso = em.merge(solucionProcesoListSolucionProceso);
            }
            em.remove(seguimientoProceso);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<SeguimientoProceso> findSeguimientoProcesoEntities() {
        return findSeguimientoProcesoEntities(true, -1, -1);
    }

    public List<SeguimientoProceso> findSeguimientoProcesoEntities(int maxResults, int firstResult) {
        return findSeguimientoProcesoEntities(false, maxResults, firstResult);
    }

    private List<SeguimientoProceso> findSeguimientoProcesoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(SeguimientoProceso.class));
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

    public SeguimientoProceso findSeguimientoProceso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(SeguimientoProceso.class, id);
        } finally {
            em.close();
        }
    }

    public int getSeguimientoProcesoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<SeguimientoProceso> rt = cq.from(SeguimientoProceso.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
