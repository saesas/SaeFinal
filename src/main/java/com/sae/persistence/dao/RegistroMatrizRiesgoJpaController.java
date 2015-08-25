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
import com.sae.persistence.domain.SubtipoDatoMatrizRiesgo;
import com.sae.persistence.domain.MatrizRiesgo;
import com.sae.persistence.domain.RegistroMatrizRiesgo;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class RegistroMatrizRiesgoJpaController implements Serializable {

    public RegistroMatrizRiesgoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(RegistroMatrizRiesgo registroMatrizRiesgo) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            SubtipoDatoMatrizRiesgo idTipo = registroMatrizRiesgo.getIdTipo();
            if (idTipo != null) {
                idTipo = em.getReference(idTipo.getClass(), idTipo.getId());
                registroMatrizRiesgo.setIdTipo(idTipo);
            }
            SubtipoDatoMatrizRiesgo idProbabilidadTratamiento = registroMatrizRiesgo.getIdProbabilidadTratamiento();
            if (idProbabilidadTratamiento != null) {
                idProbabilidadTratamiento = em.getReference(idProbabilidadTratamiento.getClass(), idProbabilidadTratamiento.getId());
                registroMatrizRiesgo.setIdProbabilidadTratamiento(idProbabilidadTratamiento);
            }
            SubtipoDatoMatrizRiesgo idProbabilidad = registroMatrizRiesgo.getIdProbabilidad();
            if (idProbabilidad != null) {
                idProbabilidad = em.getReference(idProbabilidad.getClass(), idProbabilidad.getId());
                registroMatrizRiesgo.setIdProbabilidad(idProbabilidad);
            }
            SubtipoDatoMatrizRiesgo idMonitoreo = registroMatrizRiesgo.getIdMonitoreo();
            if (idMonitoreo != null) {
                idMonitoreo = em.getReference(idMonitoreo.getClass(), idMonitoreo.getId());
                registroMatrizRiesgo.setIdMonitoreo(idMonitoreo);
            }
            SubtipoDatoMatrizRiesgo idFuente = registroMatrizRiesgo.getIdFuente();
            if (idFuente != null) {
                idFuente = em.getReference(idFuente.getClass(), idFuente.getId());
                registroMatrizRiesgo.setIdFuente(idFuente);
            }
            SubtipoDatoMatrizRiesgo idEtapa = registroMatrizRiesgo.getIdEtapa();
            if (idEtapa != null) {
                idEtapa = em.getReference(idEtapa.getClass(), idEtapa.getId());
                registroMatrizRiesgo.setIdEtapa(idEtapa);
            }
            SubtipoDatoMatrizRiesgo idClase = registroMatrizRiesgo.getIdClase();
            if (idClase != null) {
                idClase = em.getReference(idClase.getClass(), idClase.getId());
                registroMatrizRiesgo.setIdClase(idClase);
            }
            SubtipoDatoMatrizRiesgo idCategoriaTratamiento = registroMatrizRiesgo.getIdCategoriaTratamiento();
            if (idCategoriaTratamiento != null) {
                idCategoriaTratamiento = em.getReference(idCategoriaTratamiento.getClass(), idCategoriaTratamiento.getId());
                registroMatrizRiesgo.setIdCategoriaTratamiento(idCategoriaTratamiento);
            }
            SubtipoDatoMatrizRiesgo idCategoria = registroMatrizRiesgo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria = em.getReference(idCategoria.getClass(), idCategoria.getId());
                registroMatrizRiesgo.setIdCategoria(idCategoria);
            }
            MatrizRiesgo idMatriz = registroMatrizRiesgo.getIdMatriz();
            if (idMatriz != null) {
                idMatriz = em.getReference(idMatriz.getClass(), idMatriz.getId());
                registroMatrizRiesgo.setIdMatriz(idMatriz);
            }
            em.persist(registroMatrizRiesgo);
            if (idTipo != null) {
                idTipo.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idTipo = em.merge(idTipo);
            }
            if (idProbabilidadTratamiento != null) {
                idProbabilidadTratamiento.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idProbabilidadTratamiento = em.merge(idProbabilidadTratamiento);
            }
            if (idProbabilidad != null) {
                idProbabilidad.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idProbabilidad = em.merge(idProbabilidad);
            }
            if (idMonitoreo != null) {
                idMonitoreo.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idMonitoreo = em.merge(idMonitoreo);
            }
            if (idFuente != null) {
                idFuente.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idFuente = em.merge(idFuente);
            }
            if (idEtapa != null) {
                idEtapa.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idEtapa = em.merge(idEtapa);
            }
            if (idClase != null) {
                idClase.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idClase = em.merge(idClase);
            }
            if (idCategoriaTratamiento != null) {
                idCategoriaTratamiento.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idCategoriaTratamiento = em.merge(idCategoriaTratamiento);
            }
            if (idCategoria != null) {
                idCategoria.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idCategoria = em.merge(idCategoria);
            }
            if (idMatriz != null) {
                idMatriz.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idMatriz = em.merge(idMatriz);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findRegistroMatrizRiesgo(registroMatrizRiesgo.getId()) != null) {
                throw new PreexistingEntityException("RegistroMatrizRiesgo " + registroMatrizRiesgo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(RegistroMatrizRiesgo registroMatrizRiesgo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            RegistroMatrizRiesgo persistentRegistroMatrizRiesgo = em.find(RegistroMatrizRiesgo.class, registroMatrizRiesgo.getId());
            SubtipoDatoMatrizRiesgo idTipoOld = persistentRegistroMatrizRiesgo.getIdTipo();
            SubtipoDatoMatrizRiesgo idTipoNew = registroMatrizRiesgo.getIdTipo();
            SubtipoDatoMatrizRiesgo idProbabilidadTratamientoOld = persistentRegistroMatrizRiesgo.getIdProbabilidadTratamiento();
            SubtipoDatoMatrizRiesgo idProbabilidadTratamientoNew = registroMatrizRiesgo.getIdProbabilidadTratamiento();
            SubtipoDatoMatrizRiesgo idProbabilidadOld = persistentRegistroMatrizRiesgo.getIdProbabilidad();
            SubtipoDatoMatrizRiesgo idProbabilidadNew = registroMatrizRiesgo.getIdProbabilidad();
            SubtipoDatoMatrizRiesgo idMonitoreoOld = persistentRegistroMatrizRiesgo.getIdMonitoreo();
            SubtipoDatoMatrizRiesgo idMonitoreoNew = registroMatrizRiesgo.getIdMonitoreo();
            SubtipoDatoMatrizRiesgo idFuenteOld = persistentRegistroMatrizRiesgo.getIdFuente();
            SubtipoDatoMatrizRiesgo idFuenteNew = registroMatrizRiesgo.getIdFuente();
            SubtipoDatoMatrizRiesgo idEtapaOld = persistentRegistroMatrizRiesgo.getIdEtapa();
            SubtipoDatoMatrizRiesgo idEtapaNew = registroMatrizRiesgo.getIdEtapa();
            SubtipoDatoMatrizRiesgo idClaseOld = persistentRegistroMatrizRiesgo.getIdClase();
            SubtipoDatoMatrizRiesgo idClaseNew = registroMatrizRiesgo.getIdClase();
            SubtipoDatoMatrizRiesgo idCategoriaTratamientoOld = persistentRegistroMatrizRiesgo.getIdCategoriaTratamiento();
            SubtipoDatoMatrizRiesgo idCategoriaTratamientoNew = registroMatrizRiesgo.getIdCategoriaTratamiento();
            SubtipoDatoMatrizRiesgo idCategoriaOld = persistentRegistroMatrizRiesgo.getIdCategoria();
            SubtipoDatoMatrizRiesgo idCategoriaNew = registroMatrizRiesgo.getIdCategoria();
            MatrizRiesgo idMatrizOld = persistentRegistroMatrizRiesgo.getIdMatriz();
            MatrizRiesgo idMatrizNew = registroMatrizRiesgo.getIdMatriz();
            if (idTipoNew != null) {
                idTipoNew = em.getReference(idTipoNew.getClass(), idTipoNew.getId());
                registroMatrizRiesgo.setIdTipo(idTipoNew);
            }
            if (idProbabilidadTratamientoNew != null) {
                idProbabilidadTratamientoNew = em.getReference(idProbabilidadTratamientoNew.getClass(), idProbabilidadTratamientoNew.getId());
                registroMatrizRiesgo.setIdProbabilidadTratamiento(idProbabilidadTratamientoNew);
            }
            if (idProbabilidadNew != null) {
                idProbabilidadNew = em.getReference(idProbabilidadNew.getClass(), idProbabilidadNew.getId());
                registroMatrizRiesgo.setIdProbabilidad(idProbabilidadNew);
            }
            if (idMonitoreoNew != null) {
                idMonitoreoNew = em.getReference(idMonitoreoNew.getClass(), idMonitoreoNew.getId());
                registroMatrizRiesgo.setIdMonitoreo(idMonitoreoNew);
            }
            if (idFuenteNew != null) {
                idFuenteNew = em.getReference(idFuenteNew.getClass(), idFuenteNew.getId());
                registroMatrizRiesgo.setIdFuente(idFuenteNew);
            }
            if (idEtapaNew != null) {
                idEtapaNew = em.getReference(idEtapaNew.getClass(), idEtapaNew.getId());
                registroMatrizRiesgo.setIdEtapa(idEtapaNew);
            }
            if (idClaseNew != null) {
                idClaseNew = em.getReference(idClaseNew.getClass(), idClaseNew.getId());
                registroMatrizRiesgo.setIdClase(idClaseNew);
            }
            if (idCategoriaTratamientoNew != null) {
                idCategoriaTratamientoNew = em.getReference(idCategoriaTratamientoNew.getClass(), idCategoriaTratamientoNew.getId());
                registroMatrizRiesgo.setIdCategoriaTratamiento(idCategoriaTratamientoNew);
            }
            if (idCategoriaNew != null) {
                idCategoriaNew = em.getReference(idCategoriaNew.getClass(), idCategoriaNew.getId());
                registroMatrizRiesgo.setIdCategoria(idCategoriaNew);
            }
            if (idMatrizNew != null) {
                idMatrizNew = em.getReference(idMatrizNew.getClass(), idMatrizNew.getId());
                registroMatrizRiesgo.setIdMatriz(idMatrizNew);
            }
            registroMatrizRiesgo = em.merge(registroMatrizRiesgo);
            if (idTipoOld != null && !idTipoOld.equals(idTipoNew)) {
                idTipoOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idTipoOld = em.merge(idTipoOld);
            }
            if (idTipoNew != null && !idTipoNew.equals(idTipoOld)) {
                idTipoNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idTipoNew = em.merge(idTipoNew);
            }
            if (idProbabilidadTratamientoOld != null && !idProbabilidadTratamientoOld.equals(idProbabilidadTratamientoNew)) {
                idProbabilidadTratamientoOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idProbabilidadTratamientoOld = em.merge(idProbabilidadTratamientoOld);
            }
            if (idProbabilidadTratamientoNew != null && !idProbabilidadTratamientoNew.equals(idProbabilidadTratamientoOld)) {
                idProbabilidadTratamientoNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idProbabilidadTratamientoNew = em.merge(idProbabilidadTratamientoNew);
            }
            if (idProbabilidadOld != null && !idProbabilidadOld.equals(idProbabilidadNew)) {
                idProbabilidadOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idProbabilidadOld = em.merge(idProbabilidadOld);
            }
            if (idProbabilidadNew != null && !idProbabilidadNew.equals(idProbabilidadOld)) {
                idProbabilidadNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idProbabilidadNew = em.merge(idProbabilidadNew);
            }
            if (idMonitoreoOld != null && !idMonitoreoOld.equals(idMonitoreoNew)) {
                idMonitoreoOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idMonitoreoOld = em.merge(idMonitoreoOld);
            }
            if (idMonitoreoNew != null && !idMonitoreoNew.equals(idMonitoreoOld)) {
                idMonitoreoNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idMonitoreoNew = em.merge(idMonitoreoNew);
            }
            if (idFuenteOld != null && !idFuenteOld.equals(idFuenteNew)) {
                idFuenteOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idFuenteOld = em.merge(idFuenteOld);
            }
            if (idFuenteNew != null && !idFuenteNew.equals(idFuenteOld)) {
                idFuenteNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idFuenteNew = em.merge(idFuenteNew);
            }
            if (idEtapaOld != null && !idEtapaOld.equals(idEtapaNew)) {
                idEtapaOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idEtapaOld = em.merge(idEtapaOld);
            }
            if (idEtapaNew != null && !idEtapaNew.equals(idEtapaOld)) {
                idEtapaNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idEtapaNew = em.merge(idEtapaNew);
            }
            if (idClaseOld != null && !idClaseOld.equals(idClaseNew)) {
                idClaseOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idClaseOld = em.merge(idClaseOld);
            }
            if (idClaseNew != null && !idClaseNew.equals(idClaseOld)) {
                idClaseNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idClaseNew = em.merge(idClaseNew);
            }
            if (idCategoriaTratamientoOld != null && !idCategoriaTratamientoOld.equals(idCategoriaTratamientoNew)) {
                idCategoriaTratamientoOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idCategoriaTratamientoOld = em.merge(idCategoriaTratamientoOld);
            }
            if (idCategoriaTratamientoNew != null && !idCategoriaTratamientoNew.equals(idCategoriaTratamientoOld)) {
                idCategoriaTratamientoNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idCategoriaTratamientoNew = em.merge(idCategoriaTratamientoNew);
            }
            if (idCategoriaOld != null && !idCategoriaOld.equals(idCategoriaNew)) {
                idCategoriaOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idCategoriaOld = em.merge(idCategoriaOld);
            }
            if (idCategoriaNew != null && !idCategoriaNew.equals(idCategoriaOld)) {
                idCategoriaNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idCategoriaNew = em.merge(idCategoriaNew);
            }
            if (idMatrizOld != null && !idMatrizOld.equals(idMatrizNew)) {
                idMatrizOld.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idMatrizOld = em.merge(idMatrizOld);
            }
            if (idMatrizNew != null && !idMatrizNew.equals(idMatrizOld)) {
                idMatrizNew.getRegistroMatrizRiesgoList().add(registroMatrizRiesgo);
                idMatrizNew = em.merge(idMatrizNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = registroMatrizRiesgo.getId();
                if (findRegistroMatrizRiesgo(id) == null) {
                    throw new NonexistentEntityException("The registroMatrizRiesgo with id " + id + " no longer exists.");
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
            RegistroMatrizRiesgo registroMatrizRiesgo;
            try {
                registroMatrizRiesgo = em.getReference(RegistroMatrizRiesgo.class, id);
                registroMatrizRiesgo.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The registroMatrizRiesgo with id " + id + " no longer exists.", enfe);
            }
            SubtipoDatoMatrizRiesgo idTipo = registroMatrizRiesgo.getIdTipo();
            if (idTipo != null) {
                idTipo.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idTipo = em.merge(idTipo);
            }
            SubtipoDatoMatrizRiesgo idProbabilidadTratamiento = registroMatrizRiesgo.getIdProbabilidadTratamiento();
            if (idProbabilidadTratamiento != null) {
                idProbabilidadTratamiento.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idProbabilidadTratamiento = em.merge(idProbabilidadTratamiento);
            }
            SubtipoDatoMatrizRiesgo idProbabilidad = registroMatrizRiesgo.getIdProbabilidad();
            if (idProbabilidad != null) {
                idProbabilidad.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idProbabilidad = em.merge(idProbabilidad);
            }
            SubtipoDatoMatrizRiesgo idMonitoreo = registroMatrizRiesgo.getIdMonitoreo();
            if (idMonitoreo != null) {
                idMonitoreo.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idMonitoreo = em.merge(idMonitoreo);
            }
            SubtipoDatoMatrizRiesgo idFuente = registroMatrizRiesgo.getIdFuente();
            if (idFuente != null) {
                idFuente.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idFuente = em.merge(idFuente);
            }
            SubtipoDatoMatrizRiesgo idEtapa = registroMatrizRiesgo.getIdEtapa();
            if (idEtapa != null) {
                idEtapa.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idEtapa = em.merge(idEtapa);
            }
            SubtipoDatoMatrizRiesgo idClase = registroMatrizRiesgo.getIdClase();
            if (idClase != null) {
                idClase.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idClase = em.merge(idClase);
            }
            SubtipoDatoMatrizRiesgo idCategoriaTratamiento = registroMatrizRiesgo.getIdCategoriaTratamiento();
            if (idCategoriaTratamiento != null) {
                idCategoriaTratamiento.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idCategoriaTratamiento = em.merge(idCategoriaTratamiento);
            }
            SubtipoDatoMatrizRiesgo idCategoria = registroMatrizRiesgo.getIdCategoria();
            if (idCategoria != null) {
                idCategoria.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idCategoria = em.merge(idCategoria);
            }
            MatrizRiesgo idMatriz = registroMatrizRiesgo.getIdMatriz();
            if (idMatriz != null) {
                idMatriz.getRegistroMatrizRiesgoList().remove(registroMatrizRiesgo);
                idMatriz = em.merge(idMatriz);
            }
            em.remove(registroMatrizRiesgo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<RegistroMatrizRiesgo> findRegistroMatrizRiesgoEntities() {
        return findRegistroMatrizRiesgoEntities(true, -1, -1);
    }

    public List<RegistroMatrizRiesgo> findRegistroMatrizRiesgoEntities(int maxResults, int firstResult) {
        return findRegistroMatrizRiesgoEntities(false, maxResults, firstResult);
    }

    private List<RegistroMatrizRiesgo> findRegistroMatrizRiesgoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(RegistroMatrizRiesgo.class));
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

    public RegistroMatrizRiesgo findRegistroMatrizRiesgo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(RegistroMatrizRiesgo.class, id);
        } finally {
            em.close();
        }
    }

    public int getRegistroMatrizRiesgoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<RegistroMatrizRiesgo> rt = cq.from(RegistroMatrizRiesgo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
