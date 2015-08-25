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
import com.sae.persistence.domain.TipoCitacion;
import com.sae.persistence.domain.Audiencia;
import com.sae.persistence.domain.CitacionAudiencia;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.FiguraCitacionAudiencia;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class CitacionAudienciaJpaController implements Serializable {

    public CitacionAudienciaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(CitacionAudiencia citacionAudiencia) throws PreexistingEntityException, Exception {
        if (citacionAudiencia.getAudienciaList() == null) {
            citacionAudiencia.setAudienciaList(new ArrayList<Audiencia>());
        }
        if (citacionAudiencia.getFiguraCitacionAudienciaList() == null) {
            citacionAudiencia.setFiguraCitacionAudienciaList(new ArrayList<FiguraCitacionAudiencia>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoCitacion idTipo = citacionAudiencia.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                citacionAudiencia.setIdTipo(idTipo);
            }
            List<Audiencia> attachedAudienciaList = new ArrayList<Audiencia>();
            for (Audiencia audienciaListAudienciaToAttach : citacionAudiencia.getAudienciaList()) {
                audienciaListAudienciaToAttach = em.getReference(audienciaListAudienciaToAttach.getClass(), audienciaListAudienciaToAttach.getId());
                attachedAudienciaList.add(audienciaListAudienciaToAttach);
            }
            citacionAudiencia.setAudienciaList(attachedAudienciaList);
            List<FiguraCitacionAudiencia> attachedFiguraCitacionAudienciaList = new ArrayList<FiguraCitacionAudiencia>();
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach : citacionAudiencia.getFiguraCitacionAudienciaList()) {
                figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach = em.getReference(figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach.getClass(), figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach.getId());
                attachedFiguraCitacionAudienciaList.add(figuraCitacionAudienciaListFiguraCitacionAudienciaToAttach);
            }
            citacionAudiencia.setFiguraCitacionAudienciaList(attachedFiguraCitacionAudienciaList);
            em.persist(citacionAudiencia);
            if (idTipo != null) {
                idTipo.getCitacionAudienciaList().add(citacionAudiencia);
                idTipo = em.merge(idTipo);
            }
            for (Audiencia audienciaListAudiencia : citacionAudiencia.getAudienciaList()) {
                CitacionAudiencia oldIdCitacionOfAudienciaListAudiencia = audienciaListAudiencia.getIdCitacion();
                audienciaListAudiencia.setIdCitacion(citacionAudiencia);
                audienciaListAudiencia = em.merge(audienciaListAudiencia);
                if (oldIdCitacionOfAudienciaListAudiencia != null) {
                    oldIdCitacionOfAudienciaListAudiencia.getAudienciaList().remove(audienciaListAudiencia);
                    oldIdCitacionOfAudienciaListAudiencia = em.merge(oldIdCitacionOfAudienciaListAudiencia);
                }
            }
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListFiguraCitacionAudiencia : citacionAudiencia.getFiguraCitacionAudienciaList()) {
                CitacionAudiencia oldIdCitacionOfFiguraCitacionAudienciaListFiguraCitacionAudiencia = figuraCitacionAudienciaListFiguraCitacionAudiencia.getIdCitacion();
                figuraCitacionAudienciaListFiguraCitacionAudiencia.setIdCitacion(citacionAudiencia);
                figuraCitacionAudienciaListFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListFiguraCitacionAudiencia);
                if (oldIdCitacionOfFiguraCitacionAudienciaListFiguraCitacionAudiencia != null) {
                    oldIdCitacionOfFiguraCitacionAudienciaListFiguraCitacionAudiencia.getFiguraCitacionAudienciaList().remove(figuraCitacionAudienciaListFiguraCitacionAudiencia);
                    oldIdCitacionOfFiguraCitacionAudienciaListFiguraCitacionAudiencia = em.merge(oldIdCitacionOfFiguraCitacionAudienciaListFiguraCitacionAudiencia);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findCitacionAudiencia(citacionAudiencia.getId()) != null) {
                throw new PreexistingEntityException("CitacionAudiencia " + citacionAudiencia + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(CitacionAudiencia citacionAudiencia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            CitacionAudiencia persistentCitacionAudiencia = em.find(CitacionAudiencia.class, citacionAudiencia.getId());
            TipoCitacion idTipoOld = persistentCitacionAudiencia.getIdTipo();
            TipoCitacion idTipoNew = citacionAudiencia.getIdTipo();
            List<Audiencia> audienciaListOld = persistentCitacionAudiencia.getAudienciaList();
            List<Audiencia> audienciaListNew = citacionAudiencia.getAudienciaList();
            List<FiguraCitacionAudiencia> figuraCitacionAudienciaListOld = persistentCitacionAudiencia.getFiguraCitacionAudienciaList();
            List<FiguraCitacionAudiencia> figuraCitacionAudienciaListNew = citacionAudiencia.getFiguraCitacionAudienciaList();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                citacionAudiencia.setIdTipo(idTipoNew);
            }
            List<Audiencia> attachedAudienciaListNew = new ArrayList<Audiencia>();
            for (Audiencia audienciaListNewAudienciaToAttach : audienciaListNew) {
                audienciaListNewAudienciaToAttach = em.getReference(audienciaListNewAudienciaToAttach.getClass(), audienciaListNewAudienciaToAttach.getId());
                attachedAudienciaListNew.add(audienciaListNewAudienciaToAttach);
            }
            audienciaListNew = attachedAudienciaListNew;
            citacionAudiencia.setAudienciaList(audienciaListNew);
            List<FiguraCitacionAudiencia> attachedFiguraCitacionAudienciaListNew = new ArrayList<FiguraCitacionAudiencia>();
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach : figuraCitacionAudienciaListNew) {
                figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach = em.getReference(figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach.getClass(), figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach.getId());
                attachedFiguraCitacionAudienciaListNew.add(figuraCitacionAudienciaListNewFiguraCitacionAudienciaToAttach);
            }
            figuraCitacionAudienciaListNew = attachedFiguraCitacionAudienciaListNew;
            citacionAudiencia.setFiguraCitacionAudienciaList(figuraCitacionAudienciaListNew);
            citacionAudiencia = em.merge(citacionAudiencia);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getCitacionAudienciaList().remove(citacionAudiencia);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getCitacionAudienciaList().add(citacionAudiencia);
                idTipoNew = em.merge(idTipoNew);
            }
            for (Audiencia audienciaListOldAudiencia : audienciaListOld) {
                if (!audienciaListNew.contains(audienciaListOldAudiencia)) {
                    audienciaListOldAudiencia.setIdCitacion(null);
                    audienciaListOldAudiencia = em.merge(audienciaListOldAudiencia);
                }
            }
            for (Audiencia audienciaListNewAudiencia : audienciaListNew) {
                if (!audienciaListOld.contains(audienciaListNewAudiencia)) {
                    CitacionAudiencia oldIdCitacionOfAudienciaListNewAudiencia = audienciaListNewAudiencia.getIdCitacion();
                    audienciaListNewAudiencia.setIdCitacion(citacionAudiencia);
                    audienciaListNewAudiencia = em.merge(audienciaListNewAudiencia);
                    if (oldIdCitacionOfAudienciaListNewAudiencia != null && !oldIdCitacionOfAudienciaListNewAudiencia.equals(citacionAudiencia)) {
                        oldIdCitacionOfAudienciaListNewAudiencia.getAudienciaList().remove(audienciaListNewAudiencia);
                        oldIdCitacionOfAudienciaListNewAudiencia = em.merge(oldIdCitacionOfAudienciaListNewAudiencia);
                    }
                }
            }
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListOldFiguraCitacionAudiencia : figuraCitacionAudienciaListOld) {
                if (!figuraCitacionAudienciaListNew.contains(figuraCitacionAudienciaListOldFiguraCitacionAudiencia)) {
                    figuraCitacionAudienciaListOldFiguraCitacionAudiencia.setIdCitacion(null);
                    figuraCitacionAudienciaListOldFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListOldFiguraCitacionAudiencia);
                }
            }
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListNewFiguraCitacionAudiencia : figuraCitacionAudienciaListNew) {
                if (!figuraCitacionAudienciaListOld.contains(figuraCitacionAudienciaListNewFiguraCitacionAudiencia)) {
                    CitacionAudiencia oldIdCitacionOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia = figuraCitacionAudienciaListNewFiguraCitacionAudiencia.getIdCitacion();
                    figuraCitacionAudienciaListNewFiguraCitacionAudiencia.setIdCitacion(citacionAudiencia);
                    figuraCitacionAudienciaListNewFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListNewFiguraCitacionAudiencia);
                    if (oldIdCitacionOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia != null && !oldIdCitacionOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia.equals(citacionAudiencia)) {
                        oldIdCitacionOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia.getFiguraCitacionAudienciaList().remove(figuraCitacionAudienciaListNewFiguraCitacionAudiencia);
                        oldIdCitacionOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia = em.merge(oldIdCitacionOfFiguraCitacionAudienciaListNewFiguraCitacionAudiencia);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = citacionAudiencia.getId();
                if (findCitacionAudiencia(id) == null) {
                    throw new NonexistentEntityException("The citacionAudiencia with id " + id + " no longer exists.");
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
            CitacionAudiencia citacionAudiencia;
            try {
                citacionAudiencia = em.getReference(CitacionAudiencia.class, id);
                citacionAudiencia.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The citacionAudiencia with id " + id + " no longer exists.", enfe);
            }
            TipoCitacion idTipo = citacionAudiencia.getIdTipo();
            if (idTipo != null) {
                idTipo.getCitacionAudienciaList().remove(citacionAudiencia);
                idTipo = em.merge(idTipo);
            }
            List<Audiencia> audienciaList = citacionAudiencia.getAudienciaList();
            for (Audiencia audienciaListAudiencia : audienciaList) {
                audienciaListAudiencia.setIdCitacion(null);
                audienciaListAudiencia = em.merge(audienciaListAudiencia);
            }
            List<FiguraCitacionAudiencia> figuraCitacionAudienciaList = citacionAudiencia.getFiguraCitacionAudienciaList();
            for (FiguraCitacionAudiencia figuraCitacionAudienciaListFiguraCitacionAudiencia : figuraCitacionAudienciaList) {
                figuraCitacionAudienciaListFiguraCitacionAudiencia.setIdCitacion(null);
                figuraCitacionAudienciaListFiguraCitacionAudiencia = em.merge(figuraCitacionAudienciaListFiguraCitacionAudiencia);
            }
            em.remove(citacionAudiencia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<CitacionAudiencia> findCitacionAudienciaEntities() {
        return findCitacionAudienciaEntities(true, -1, -1);
    }

    public List<CitacionAudiencia> findCitacionAudienciaEntities(int maxResults, int firstResult) {
        return findCitacionAudienciaEntities(false, maxResults, firstResult);
    }

    private List<CitacionAudiencia> findCitacionAudienciaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(CitacionAudiencia.class));
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

    public CitacionAudiencia findCitacionAudiencia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(CitacionAudiencia.class, id);
        } finally {
            em.close();
        }
    }

    public int getCitacionAudienciaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<CitacionAudiencia> rt = cq.from(CitacionAudiencia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
