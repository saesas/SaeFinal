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
import com.sae.persistence.domain.FiguraPropuesta;
import com.sae.persistence.domain.Convocatoria;
import com.sae.persistence.domain.AdjuntoPropuesta;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.InformacionSobrePropuesta;
import com.sae.persistence.domain.Propuesta;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class PropuestaJpaController implements Serializable {

    public PropuestaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Propuesta propuesta) throws PreexistingEntityException, Exception {
        if (propuesta.getAdjuntoPropuestaList() == null) {
            propuesta.setAdjuntoPropuestaList(new ArrayList<AdjuntoPropuesta>());
        }
        if (propuesta.getInformacionSobrePropuestaList() == null) {
            propuesta.setInformacionSobrePropuestaList(new ArrayList<InformacionSobrePropuesta>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            FiguraPropuesta idFigura = propuesta.getIdFigura();
            if (idFigura != null) {
                idFigura = em.getReference(idFigura.getClass(), idFigura.getId());
                propuesta.setIdFigura(idFigura);
            }
            Convocatoria idConvocatoria = propuesta.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria = em.getReference(idConvocatoria.getClass(), idConvocatoria.getId());
                propuesta.setIdConvocatoria(idConvocatoria);
            }
            List<AdjuntoPropuesta> attachedAdjuntoPropuestaList = new ArrayList<AdjuntoPropuesta>();
            for (AdjuntoPropuesta adjuntoPropuestaListAdjuntoPropuestaToAttach : propuesta.getAdjuntoPropuestaList()) {
                adjuntoPropuestaListAdjuntoPropuestaToAttach = em.getReference(adjuntoPropuestaListAdjuntoPropuestaToAttach.getClass(), adjuntoPropuestaListAdjuntoPropuestaToAttach.getId());
                attachedAdjuntoPropuestaList.add(adjuntoPropuestaListAdjuntoPropuestaToAttach);
            }
            propuesta.setAdjuntoPropuestaList(attachedAdjuntoPropuestaList);
            List<InformacionSobrePropuesta> attachedInformacionSobrePropuestaList = new ArrayList<InformacionSobrePropuesta>();
            for (InformacionSobrePropuesta informacionSobrePropuestaListInformacionSobrePropuestaToAttach : propuesta.getInformacionSobrePropuestaList()) {
                informacionSobrePropuestaListInformacionSobrePropuestaToAttach = em.getReference(informacionSobrePropuestaListInformacionSobrePropuestaToAttach.getClass(), informacionSobrePropuestaListInformacionSobrePropuestaToAttach.getId());
                attachedInformacionSobrePropuestaList.add(informacionSobrePropuestaListInformacionSobrePropuestaToAttach);
            }
            propuesta.setInformacionSobrePropuestaList(attachedInformacionSobrePropuestaList);
            em.persist(propuesta);
            if (idFigura != null) {
                idFigura.getPropuestaList().add(propuesta);
                idFigura = em.merge(idFigura);
            }
            if (idConvocatoria != null) {
                idConvocatoria.getPropuestaList().add(propuesta);
                idConvocatoria = em.merge(idConvocatoria);
            }
            for (AdjuntoPropuesta adjuntoPropuestaListAdjuntoPropuesta : propuesta.getAdjuntoPropuestaList()) {
                Propuesta oldIdPropuestaOfAdjuntoPropuestaListAdjuntoPropuesta = adjuntoPropuestaListAdjuntoPropuesta.getIdPropuesta();
                adjuntoPropuestaListAdjuntoPropuesta.setIdPropuesta(propuesta);
                adjuntoPropuestaListAdjuntoPropuesta = em.merge(adjuntoPropuestaListAdjuntoPropuesta);
                if (oldIdPropuestaOfAdjuntoPropuestaListAdjuntoPropuesta != null) {
                    oldIdPropuestaOfAdjuntoPropuestaListAdjuntoPropuesta.getAdjuntoPropuestaList().remove(adjuntoPropuestaListAdjuntoPropuesta);
                    oldIdPropuestaOfAdjuntoPropuestaListAdjuntoPropuesta = em.merge(oldIdPropuestaOfAdjuntoPropuestaListAdjuntoPropuesta);
                }
            }
            for (InformacionSobrePropuesta informacionSobrePropuestaListInformacionSobrePropuesta : propuesta.getInformacionSobrePropuestaList()) {
                Propuesta oldIdPropuestaOfInformacionSobrePropuestaListInformacionSobrePropuesta = informacionSobrePropuestaListInformacionSobrePropuesta.getIdPropuesta();
                informacionSobrePropuestaListInformacionSobrePropuesta.setIdPropuesta(propuesta);
                informacionSobrePropuestaListInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListInformacionSobrePropuesta);
                if (oldIdPropuestaOfInformacionSobrePropuestaListInformacionSobrePropuesta != null) {
                    oldIdPropuestaOfInformacionSobrePropuestaListInformacionSobrePropuesta.getInformacionSobrePropuestaList().remove(informacionSobrePropuestaListInformacionSobrePropuesta);
                    oldIdPropuestaOfInformacionSobrePropuestaListInformacionSobrePropuesta = em.merge(oldIdPropuestaOfInformacionSobrePropuestaListInformacionSobrePropuesta);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPropuesta(propuesta.getId()) != null) {
                throw new PreexistingEntityException("Propuesta " + propuesta + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Propuesta propuesta) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Propuesta persistentPropuesta = em.find(Propuesta.class, propuesta.getId());
            FiguraPropuesta idFiguraOld = persistentPropuesta.getIdFigura();
            FiguraPropuesta idFiguraNew = propuesta.getIdFigura();
            Convocatoria idConvocatoriaOld = persistentPropuesta.getIdConvocatoria();
            Convocatoria idConvocatoriaNew = propuesta.getIdConvocatoria();
            List<AdjuntoPropuesta> adjuntoPropuestaListOld = persistentPropuesta.getAdjuntoPropuestaList();
            List<AdjuntoPropuesta> adjuntoPropuestaListNew = propuesta.getAdjuntoPropuestaList();
            List<InformacionSobrePropuesta> informacionSobrePropuestaListOld = persistentPropuesta.getInformacionSobrePropuestaList();
            List<InformacionSobrePropuesta> informacionSobrePropuestaListNew = propuesta.getInformacionSobrePropuestaList();
            if (idFiguraNew != null) {
                idFiguraNew = em.getReference(idFiguraNew.getClass(), idFiguraNew.getId());
                propuesta.setIdFigura(idFiguraNew);
            }
            if (idConvocatoriaNew != null) {
                idConvocatoriaNew = em.getReference(idConvocatoriaNew.getClass(), idConvocatoriaNew.getId());
                propuesta.setIdConvocatoria(idConvocatoriaNew);
            }
            List<AdjuntoPropuesta> attachedAdjuntoPropuestaListNew = new ArrayList<AdjuntoPropuesta>();
            for (AdjuntoPropuesta adjuntoPropuestaListNewAdjuntoPropuestaToAttach : adjuntoPropuestaListNew) {
                adjuntoPropuestaListNewAdjuntoPropuestaToAttach = em.getReference(adjuntoPropuestaListNewAdjuntoPropuestaToAttach.getClass(), adjuntoPropuestaListNewAdjuntoPropuestaToAttach.getId());
                attachedAdjuntoPropuestaListNew.add(adjuntoPropuestaListNewAdjuntoPropuestaToAttach);
            }
            adjuntoPropuestaListNew = attachedAdjuntoPropuestaListNew;
            propuesta.setAdjuntoPropuestaList(adjuntoPropuestaListNew);
            List<InformacionSobrePropuesta> attachedInformacionSobrePropuestaListNew = new ArrayList<InformacionSobrePropuesta>();
            for (InformacionSobrePropuesta informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach : informacionSobrePropuestaListNew) {
                informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach = em.getReference(informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach.getClass(), informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach.getId());
                attachedInformacionSobrePropuestaListNew.add(informacionSobrePropuestaListNewInformacionSobrePropuestaToAttach);
            }
            informacionSobrePropuestaListNew = attachedInformacionSobrePropuestaListNew;
            propuesta.setInformacionSobrePropuestaList(informacionSobrePropuestaListNew);
            propuesta = em.merge(propuesta);
            if (idFiguraOld != null && !idFiguraOld.equals(idFiguraNew)) {
                idFiguraOld.getPropuestaList().remove(propuesta);
                idFiguraOld = em.merge(idFiguraOld);
            }
            if (idFiguraNew != null && !idFiguraNew.equals(idFiguraOld)) {
                idFiguraNew.getPropuestaList().add(propuesta);
                idFiguraNew = em.merge(idFiguraNew);
            }
            if (idConvocatoriaOld != null && !idConvocatoriaOld.equals(idConvocatoriaNew)) {
                idConvocatoriaOld.getPropuestaList().remove(propuesta);
                idConvocatoriaOld = em.merge(idConvocatoriaOld);
            }
            if (idConvocatoriaNew != null && !idConvocatoriaNew.equals(idConvocatoriaOld)) {
                idConvocatoriaNew.getPropuestaList().add(propuesta);
                idConvocatoriaNew = em.merge(idConvocatoriaNew);
            }
            for (AdjuntoPropuesta adjuntoPropuestaListOldAdjuntoPropuesta : adjuntoPropuestaListOld) {
                if (!adjuntoPropuestaListNew.contains(adjuntoPropuestaListOldAdjuntoPropuesta)) {
                    adjuntoPropuestaListOldAdjuntoPropuesta.setIdPropuesta(null);
                    adjuntoPropuestaListOldAdjuntoPropuesta = em.merge(adjuntoPropuestaListOldAdjuntoPropuesta);
                }
            }
            for (AdjuntoPropuesta adjuntoPropuestaListNewAdjuntoPropuesta : adjuntoPropuestaListNew) {
                if (!adjuntoPropuestaListOld.contains(adjuntoPropuestaListNewAdjuntoPropuesta)) {
                    Propuesta oldIdPropuestaOfAdjuntoPropuestaListNewAdjuntoPropuesta = adjuntoPropuestaListNewAdjuntoPropuesta.getIdPropuesta();
                    adjuntoPropuestaListNewAdjuntoPropuesta.setIdPropuesta(propuesta);
                    adjuntoPropuestaListNewAdjuntoPropuesta = em.merge(adjuntoPropuestaListNewAdjuntoPropuesta);
                    if (oldIdPropuestaOfAdjuntoPropuestaListNewAdjuntoPropuesta != null && !oldIdPropuestaOfAdjuntoPropuestaListNewAdjuntoPropuesta.equals(propuesta)) {
                        oldIdPropuestaOfAdjuntoPropuestaListNewAdjuntoPropuesta.getAdjuntoPropuestaList().remove(adjuntoPropuestaListNewAdjuntoPropuesta);
                        oldIdPropuestaOfAdjuntoPropuestaListNewAdjuntoPropuesta = em.merge(oldIdPropuestaOfAdjuntoPropuestaListNewAdjuntoPropuesta);
                    }
                }
            }
            for (InformacionSobrePropuesta informacionSobrePropuestaListOldInformacionSobrePropuesta : informacionSobrePropuestaListOld) {
                if (!informacionSobrePropuestaListNew.contains(informacionSobrePropuestaListOldInformacionSobrePropuesta)) {
                    informacionSobrePropuestaListOldInformacionSobrePropuesta.setIdPropuesta(null);
                    informacionSobrePropuestaListOldInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListOldInformacionSobrePropuesta);
                }
            }
            for (InformacionSobrePropuesta informacionSobrePropuestaListNewInformacionSobrePropuesta : informacionSobrePropuestaListNew) {
                if (!informacionSobrePropuestaListOld.contains(informacionSobrePropuestaListNewInformacionSobrePropuesta)) {
                    Propuesta oldIdPropuestaOfInformacionSobrePropuestaListNewInformacionSobrePropuesta = informacionSobrePropuestaListNewInformacionSobrePropuesta.getIdPropuesta();
                    informacionSobrePropuestaListNewInformacionSobrePropuesta.setIdPropuesta(propuesta);
                    informacionSobrePropuestaListNewInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListNewInformacionSobrePropuesta);
                    if (oldIdPropuestaOfInformacionSobrePropuestaListNewInformacionSobrePropuesta != null && !oldIdPropuestaOfInformacionSobrePropuestaListNewInformacionSobrePropuesta.equals(propuesta)) {
                        oldIdPropuestaOfInformacionSobrePropuestaListNewInformacionSobrePropuesta.getInformacionSobrePropuestaList().remove(informacionSobrePropuestaListNewInformacionSobrePropuesta);
                        oldIdPropuestaOfInformacionSobrePropuestaListNewInformacionSobrePropuesta = em.merge(oldIdPropuestaOfInformacionSobrePropuestaListNewInformacionSobrePropuesta);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = propuesta.getId();
                if (findPropuesta(id) == null) {
                    throw new NonexistentEntityException("The propuesta with id " + id + " no longer exists.");
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
            Propuesta propuesta;
            try {
                propuesta = em.getReference(Propuesta.class, id);
                propuesta.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The propuesta with id " + id + " no longer exists.", enfe);
            }
            FiguraPropuesta idFigura = propuesta.getIdFigura();
            if (idFigura != null) {
                idFigura.getPropuestaList().remove(propuesta);
                idFigura = em.merge(idFigura);
            }
            Convocatoria idConvocatoria = propuesta.getIdConvocatoria();
            if (idConvocatoria != null) {
                idConvocatoria.getPropuestaList().remove(propuesta);
                idConvocatoria = em.merge(idConvocatoria);
            }
            List<AdjuntoPropuesta> adjuntoPropuestaList = propuesta.getAdjuntoPropuestaList();
            for (AdjuntoPropuesta adjuntoPropuestaListAdjuntoPropuesta : adjuntoPropuestaList) {
                adjuntoPropuestaListAdjuntoPropuesta.setIdPropuesta(null);
                adjuntoPropuestaListAdjuntoPropuesta = em.merge(adjuntoPropuestaListAdjuntoPropuesta);
            }
            List<InformacionSobrePropuesta> informacionSobrePropuestaList = propuesta.getInformacionSobrePropuestaList();
            for (InformacionSobrePropuesta informacionSobrePropuestaListInformacionSobrePropuesta : informacionSobrePropuestaList) {
                informacionSobrePropuestaListInformacionSobrePropuesta.setIdPropuesta(null);
                informacionSobrePropuestaListInformacionSobrePropuesta = em.merge(informacionSobrePropuestaListInformacionSobrePropuesta);
            }
            em.remove(propuesta);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Propuesta> findPropuestaEntities() {
        return findPropuestaEntities(true, -1, -1);
    }

    public List<Propuesta> findPropuestaEntities(int maxResults, int firstResult) {
        return findPropuestaEntities(false, maxResults, firstResult);
    }

    private List<Propuesta> findPropuestaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Propuesta.class));
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

    public Propuesta findPropuesta(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Propuesta.class, id);
        } finally {
            em.close();
        }
    }

    public int getPropuestaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Propuesta> rt = cq.from(Propuesta.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
