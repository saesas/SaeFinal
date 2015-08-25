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
import com.sae.persistence.domain.TipoPropiedad;
import com.sae.persistence.domain.Parentesco;
import com.sae.persistence.domain.NivelEducacion;
import com.sae.persistence.domain.ExperienciaLaboral;
import com.sae.persistence.domain.HojaVida;
import java.util.ArrayList;
import java.util.List;
import com.sae.persistence.domain.ReferenciaPersonal;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class HojaVidaJpaController implements Serializable {

    public HojaVidaJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(HojaVida hojaVida) throws PreexistingEntityException, Exception {
        if (hojaVida.getExperienciaLaboralList() == null) {
            hojaVida.setExperienciaLaboralList(new ArrayList<ExperienciaLaboral>());
        }
        if (hojaVida.getReferenciaPersonalList() == null) {
            hojaVida.setReferenciaPersonalList(new ArrayList<ReferenciaPersonal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            TipoPropiedad idTipoPropiedadHabita = hojaVida.getIdTipoPropiedadHabita();
            if (idTipoPropiedadHabita != null) {
                idTipoPropiedadHabita = em.getReference(idTipoPropiedadHabita.getClass(), idTipoPropiedadHabita.getId());
                hojaVida.setIdTipoPropiedadHabita(idTipoPropiedadHabita);
            }
            Parentesco idParentescoContacto = hojaVida.getIdParentescoContacto();
            if (idParentescoContacto != null) {
                idParentescoContacto = em.getReference(idParentescoContacto.getClass(), idParentescoContacto.getId());
                hojaVida.setIdParentescoContacto(idParentescoContacto);
            }
            NivelEducacion idNivelEducacion = hojaVida.getIdNivelEducacion();
            if (idNivelEducacion != null) {
                idNivelEducacion = em.getReference(idNivelEducacion.getClass(), idNivelEducacion.getId());
                hojaVida.setIdNivelEducacion(idNivelEducacion);
            }
            List<ExperienciaLaboral> attachedExperienciaLaboralList = new ArrayList<ExperienciaLaboral>();
            for (ExperienciaLaboral experienciaLaboralListExperienciaLaboralToAttach : hojaVida.getExperienciaLaboralList()) {
                experienciaLaboralListExperienciaLaboralToAttach = em.getReference(experienciaLaboralListExperienciaLaboralToAttach.getClass(), experienciaLaboralListExperienciaLaboralToAttach.getId());
                attachedExperienciaLaboralList.add(experienciaLaboralListExperienciaLaboralToAttach);
            }
            hojaVida.setExperienciaLaboralList(attachedExperienciaLaboralList);
            List<ReferenciaPersonal> attachedReferenciaPersonalList = new ArrayList<ReferenciaPersonal>();
            for (ReferenciaPersonal referenciaPersonalListReferenciaPersonalToAttach : hojaVida.getReferenciaPersonalList()) {
                referenciaPersonalListReferenciaPersonalToAttach = em.getReference(referenciaPersonalListReferenciaPersonalToAttach.getClass(), referenciaPersonalListReferenciaPersonalToAttach.getId());
                attachedReferenciaPersonalList.add(referenciaPersonalListReferenciaPersonalToAttach);
            }
            hojaVida.setReferenciaPersonalList(attachedReferenciaPersonalList);
            em.persist(hojaVida);
            if (idTipoPropiedadHabita != null) {
                idTipoPropiedadHabita.getHojaVidaList().add(hojaVida);
                idTipoPropiedadHabita = em.merge(idTipoPropiedadHabita);
            }
            if (idParentescoContacto != null) {
                idParentescoContacto.getHojaVidaList().add(hojaVida);
                idParentescoContacto = em.merge(idParentescoContacto);
            }
            if (idNivelEducacion != null) {
                idNivelEducacion.getHojaVidaList().add(hojaVida);
                idNivelEducacion = em.merge(idNivelEducacion);
            }
            for (ExperienciaLaboral experienciaLaboralListExperienciaLaboral : hojaVida.getExperienciaLaboralList()) {
                HojaVida oldIdHojaVidaOfExperienciaLaboralListExperienciaLaboral = experienciaLaboralListExperienciaLaboral.getIdHojaVida();
                experienciaLaboralListExperienciaLaboral.setIdHojaVida(hojaVida);
                experienciaLaboralListExperienciaLaboral = em.merge(experienciaLaboralListExperienciaLaboral);
                if (oldIdHojaVidaOfExperienciaLaboralListExperienciaLaboral != null) {
                    oldIdHojaVidaOfExperienciaLaboralListExperienciaLaboral.getExperienciaLaboralList().remove(experienciaLaboralListExperienciaLaboral);
                    oldIdHojaVidaOfExperienciaLaboralListExperienciaLaboral = em.merge(oldIdHojaVidaOfExperienciaLaboralListExperienciaLaboral);
                }
            }
            for (ReferenciaPersonal referenciaPersonalListReferenciaPersonal : hojaVida.getReferenciaPersonalList()) {
                HojaVida oldIdHojaVidaOfReferenciaPersonalListReferenciaPersonal = referenciaPersonalListReferenciaPersonal.getIdHojaVida();
                referenciaPersonalListReferenciaPersonal.setIdHojaVida(hojaVida);
                referenciaPersonalListReferenciaPersonal = em.merge(referenciaPersonalListReferenciaPersonal);
                if (oldIdHojaVidaOfReferenciaPersonalListReferenciaPersonal != null) {
                    oldIdHojaVidaOfReferenciaPersonalListReferenciaPersonal.getReferenciaPersonalList().remove(referenciaPersonalListReferenciaPersonal);
                    oldIdHojaVidaOfReferenciaPersonalListReferenciaPersonal = em.merge(oldIdHojaVidaOfReferenciaPersonalListReferenciaPersonal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findHojaVida(hojaVida.getId()) != null) {
                throw new PreexistingEntityException("HojaVida " + hojaVida + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(HojaVida hojaVida) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            HojaVida persistentHojaVida = em.find(HojaVida.class, hojaVida.getId());
            TipoPropiedad idTipoPropiedadHabitaOld = persistentHojaVida.getIdTipoPropiedadHabita();
            TipoPropiedad idTipoPropiedadHabitaNew = hojaVida.getIdTipoPropiedadHabita();
            Parentesco idParentescoContactoOld = persistentHojaVida.getIdParentescoContacto();
            Parentesco idParentescoContactoNew = hojaVida.getIdParentescoContacto();
            NivelEducacion idNivelEducacionOld = persistentHojaVida.getIdNivelEducacion();
            NivelEducacion idNivelEducacionNew = hojaVida.getIdNivelEducacion();
            List<ExperienciaLaboral> experienciaLaboralListOld = persistentHojaVida.getExperienciaLaboralList();
            List<ExperienciaLaboral> experienciaLaboralListNew = hojaVida.getExperienciaLaboralList();
            List<ReferenciaPersonal> referenciaPersonalListOld = persistentHojaVida.getReferenciaPersonalList();
            List<ReferenciaPersonal> referenciaPersonalListNew = hojaVida.getReferenciaPersonalList();
            if (idTipoPropiedadHabitaNew != null) {
                idTipoPropiedadHabitaNew = em.getReference(idTipoPropiedadHabitaNew.getClass(), idTipoPropiedadHabitaNew.getId());
                hojaVida.setIdTipoPropiedadHabita(idTipoPropiedadHabitaNew);
            }
            if (idParentescoContactoNew != null) {
                idParentescoContactoNew = em.getReference(idParentescoContactoNew.getClass(), idParentescoContactoNew.getId());
                hojaVida.setIdParentescoContacto(idParentescoContactoNew);
            }
            if (idNivelEducacionNew != null) {
                idNivelEducacionNew = em.getReference(idNivelEducacionNew.getClass(), idNivelEducacionNew.getId());
                hojaVida.setIdNivelEducacion(idNivelEducacionNew);
            }
            List<ExperienciaLaboral> attachedExperienciaLaboralListNew = new ArrayList<ExperienciaLaboral>();
            for (ExperienciaLaboral experienciaLaboralListNewExperienciaLaboralToAttach : experienciaLaboralListNew) {
                experienciaLaboralListNewExperienciaLaboralToAttach = em.getReference(experienciaLaboralListNewExperienciaLaboralToAttach.getClass(), experienciaLaboralListNewExperienciaLaboralToAttach.getId());
                attachedExperienciaLaboralListNew.add(experienciaLaboralListNewExperienciaLaboralToAttach);
            }
            experienciaLaboralListNew = attachedExperienciaLaboralListNew;
            hojaVida.setExperienciaLaboralList(experienciaLaboralListNew);
            List<ReferenciaPersonal> attachedReferenciaPersonalListNew = new ArrayList<ReferenciaPersonal>();
            for (ReferenciaPersonal referenciaPersonalListNewReferenciaPersonalToAttach : referenciaPersonalListNew) {
                referenciaPersonalListNewReferenciaPersonalToAttach = em.getReference(referenciaPersonalListNewReferenciaPersonalToAttach.getClass(), referenciaPersonalListNewReferenciaPersonalToAttach.getId());
                attachedReferenciaPersonalListNew.add(referenciaPersonalListNewReferenciaPersonalToAttach);
            }
            referenciaPersonalListNew = attachedReferenciaPersonalListNew;
            hojaVida.setReferenciaPersonalList(referenciaPersonalListNew);
            hojaVida = em.merge(hojaVida);
            if (idTipoPropiedadHabitaOld != null && !idTipoPropiedadHabitaOld.equals(idTipoPropiedadHabitaNew)) {
                idTipoPropiedadHabitaOld.getHojaVidaList().remove(hojaVida);
                idTipoPropiedadHabitaOld = em.merge(idTipoPropiedadHabitaOld);
            }
            if (idTipoPropiedadHabitaNew != null && !idTipoPropiedadHabitaNew.equals(idTipoPropiedadHabitaOld)) {
                idTipoPropiedadHabitaNew.getHojaVidaList().add(hojaVida);
                idTipoPropiedadHabitaNew = em.merge(idTipoPropiedadHabitaNew);
            }
            if (idParentescoContactoOld != null && !idParentescoContactoOld.equals(idParentescoContactoNew)) {
                idParentescoContactoOld.getHojaVidaList().remove(hojaVida);
                idParentescoContactoOld = em.merge(idParentescoContactoOld);
            }
            if (idParentescoContactoNew != null && !idParentescoContactoNew.equals(idParentescoContactoOld)) {
                idParentescoContactoNew.getHojaVidaList().add(hojaVida);
                idParentescoContactoNew = em.merge(idParentescoContactoNew);
            }
            if (idNivelEducacionOld != null && !idNivelEducacionOld.equals(idNivelEducacionNew)) {
                idNivelEducacionOld.getHojaVidaList().remove(hojaVida);
                idNivelEducacionOld = em.merge(idNivelEducacionOld);
            }
            if (idNivelEducacionNew != null && !idNivelEducacionNew.equals(idNivelEducacionOld)) {
                idNivelEducacionNew.getHojaVidaList().add(hojaVida);
                idNivelEducacionNew = em.merge(idNivelEducacionNew);
            }
            for (ExperienciaLaboral experienciaLaboralListOldExperienciaLaboral : experienciaLaboralListOld) {
                if (!experienciaLaboralListNew.contains(experienciaLaboralListOldExperienciaLaboral)) {
                    experienciaLaboralListOldExperienciaLaboral.setIdHojaVida(null);
                    experienciaLaboralListOldExperienciaLaboral = em.merge(experienciaLaboralListOldExperienciaLaboral);
                }
            }
            for (ExperienciaLaboral experienciaLaboralListNewExperienciaLaboral : experienciaLaboralListNew) {
                if (!experienciaLaboralListOld.contains(experienciaLaboralListNewExperienciaLaboral)) {
                    HojaVida oldIdHojaVidaOfExperienciaLaboralListNewExperienciaLaboral = experienciaLaboralListNewExperienciaLaboral.getIdHojaVida();
                    experienciaLaboralListNewExperienciaLaboral.setIdHojaVida(hojaVida);
                    experienciaLaboralListNewExperienciaLaboral = em.merge(experienciaLaboralListNewExperienciaLaboral);
                    if (oldIdHojaVidaOfExperienciaLaboralListNewExperienciaLaboral != null && !oldIdHojaVidaOfExperienciaLaboralListNewExperienciaLaboral.equals(hojaVida)) {
                        oldIdHojaVidaOfExperienciaLaboralListNewExperienciaLaboral.getExperienciaLaboralList().remove(experienciaLaboralListNewExperienciaLaboral);
                        oldIdHojaVidaOfExperienciaLaboralListNewExperienciaLaboral = em.merge(oldIdHojaVidaOfExperienciaLaboralListNewExperienciaLaboral);
                    }
                }
            }
            for (ReferenciaPersonal referenciaPersonalListOldReferenciaPersonal : referenciaPersonalListOld) {
                if (!referenciaPersonalListNew.contains(referenciaPersonalListOldReferenciaPersonal)) {
                    referenciaPersonalListOldReferenciaPersonal.setIdHojaVida(null);
                    referenciaPersonalListOldReferenciaPersonal = em.merge(referenciaPersonalListOldReferenciaPersonal);
                }
            }
            for (ReferenciaPersonal referenciaPersonalListNewReferenciaPersonal : referenciaPersonalListNew) {
                if (!referenciaPersonalListOld.contains(referenciaPersonalListNewReferenciaPersonal)) {
                    HojaVida oldIdHojaVidaOfReferenciaPersonalListNewReferenciaPersonal = referenciaPersonalListNewReferenciaPersonal.getIdHojaVida();
                    referenciaPersonalListNewReferenciaPersonal.setIdHojaVida(hojaVida);
                    referenciaPersonalListNewReferenciaPersonal = em.merge(referenciaPersonalListNewReferenciaPersonal);
                    if (oldIdHojaVidaOfReferenciaPersonalListNewReferenciaPersonal != null && !oldIdHojaVidaOfReferenciaPersonalListNewReferenciaPersonal.equals(hojaVida)) {
                        oldIdHojaVidaOfReferenciaPersonalListNewReferenciaPersonal.getReferenciaPersonalList().remove(referenciaPersonalListNewReferenciaPersonal);
                        oldIdHojaVidaOfReferenciaPersonalListNewReferenciaPersonal = em.merge(oldIdHojaVidaOfReferenciaPersonalListNewReferenciaPersonal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = hojaVida.getId();
                if (findHojaVida(id) == null) {
                    throw new NonexistentEntityException("The hojaVida with id " + id + " no longer exists.");
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
            HojaVida hojaVida;
            try {
                hojaVida = em.getReference(HojaVida.class, id);
                hojaVida.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The hojaVida with id " + id + " no longer exists.", enfe);
            }
            TipoPropiedad idTipoPropiedadHabita = hojaVida.getIdTipoPropiedadHabita();
            if (idTipoPropiedadHabita != null) {
                idTipoPropiedadHabita.getHojaVidaList().remove(hojaVida);
                idTipoPropiedadHabita = em.merge(idTipoPropiedadHabita);
            }
            Parentesco idParentescoContacto = hojaVida.getIdParentescoContacto();
            if (idParentescoContacto != null) {
                idParentescoContacto.getHojaVidaList().remove(hojaVida);
                idParentescoContacto = em.merge(idParentescoContacto);
            }
            NivelEducacion idNivelEducacion = hojaVida.getIdNivelEducacion();
            if (idNivelEducacion != null) {
                idNivelEducacion.getHojaVidaList().remove(hojaVida);
                idNivelEducacion = em.merge(idNivelEducacion);
            }
            List<ExperienciaLaboral> experienciaLaboralList = hojaVida.getExperienciaLaboralList();
            for (ExperienciaLaboral experienciaLaboralListExperienciaLaboral : experienciaLaboralList) {
                experienciaLaboralListExperienciaLaboral.setIdHojaVida(null);
                experienciaLaboralListExperienciaLaboral = em.merge(experienciaLaboralListExperienciaLaboral);
            }
            List<ReferenciaPersonal> referenciaPersonalList = hojaVida.getReferenciaPersonalList();
            for (ReferenciaPersonal referenciaPersonalListReferenciaPersonal : referenciaPersonalList) {
                referenciaPersonalListReferenciaPersonal.setIdHojaVida(null);
                referenciaPersonalListReferenciaPersonal = em.merge(referenciaPersonalListReferenciaPersonal);
            }
            em.remove(hojaVida);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<HojaVida> findHojaVidaEntities() {
        return findHojaVidaEntities(true, -1, -1);
    }

    public List<HojaVida> findHojaVidaEntities(int maxResults, int firstResult) {
        return findHojaVidaEntities(false, maxResults, firstResult);
    }

    private List<HojaVida> findHojaVidaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(HojaVida.class));
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

    public HojaVida findHojaVida(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(HojaVida.class, id);
        } finally {
            em.close();
        }
    }

    public int getHojaVidaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<HojaVida> rt = cq.from(HojaVida.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
