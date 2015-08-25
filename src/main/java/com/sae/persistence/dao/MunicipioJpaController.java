/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.IllegalOrphanException;
import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.Departamento;
import com.sae.persistence.domain.Municipio;
import com.sae.persistence.domain.SucursalRazonSocialTercero;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class MunicipioJpaController implements Serializable {

    public MunicipioJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Municipio municipio) throws IllegalOrphanException, PreexistingEntityException, Exception {
        if (municipio.getSucursalRazonSocialTerceroList() == null) {
            municipio.setSucursalRazonSocialTerceroList(new ArrayList<SucursalRazonSocialTercero>());
        }
        List<String> illegalOrphanMessages = null;
        Departamento departamentoOrphanCheck = municipio.getDepartamento();
        if (departamentoOrphanCheck != null) {
            Municipio oldMunicipioOfDepartamento = departamentoOrphanCheck.getMunicipio();
            if (oldMunicipioOfDepartamento != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Departamento " + departamentoOrphanCheck + " already has an item of type Municipio whose departamento column cannot be null. Please make another selection for the departamento field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Departamento departamento = municipio.getDepartamento();
            if (departamento != null) {
                departamento = em.getReference(departamento.getClass(), departamento.getId());
                municipio.setDepartamento(departamento);
            }
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroList = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach : municipio.getSucursalRazonSocialTerceroList()) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroList.add(sucursalRazonSocialTerceroListSucursalRazonSocialTerceroToAttach);
            }
            municipio.setSucursalRazonSocialTerceroList(attachedSucursalRazonSocialTerceroList);
            em.persist(municipio);
            if (departamento != null) {
                departamento.setMunicipio(municipio);
                departamento = em.merge(departamento);
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : municipio.getSucursalRazonSocialTerceroList()) {
                Municipio oldIdMunicipioOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = sucursalRazonSocialTerceroListSucursalRazonSocialTercero.getIdMunicipio();
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdMunicipio(municipio);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                if (oldIdMunicipioOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero != null) {
                    oldIdMunicipioOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                    oldIdMunicipioOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(oldIdMunicipioOfSucursalRazonSocialTerceroListSucursalRazonSocialTercero);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findMunicipio(municipio.getId()) != null) {
                throw new PreexistingEntityException("Municipio " + municipio + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Municipio municipio) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Municipio persistentMunicipio = em.find(Municipio.class, municipio.getId());
            Departamento departamentoOld = persistentMunicipio.getDepartamento();
            Departamento departamentoNew = municipio.getDepartamento();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListOld = persistentMunicipio.getSucursalRazonSocialTerceroList();
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroListNew = municipio.getSucursalRazonSocialTerceroList();
            List<String> illegalOrphanMessages = null;
            if (departamentoNew != null && !departamentoNew.equals(departamentoOld)) {
                Municipio oldMunicipioOfDepartamento = departamentoNew.getMunicipio();
                if (oldMunicipioOfDepartamento != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Departamento " + departamentoNew + " already has an item of type Municipio whose departamento column cannot be null. Please make another selection for the departamento field.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (departamentoNew != null) {
                departamentoNew = em.getReference(departamentoNew.getClass(), departamentoNew.getId());
                municipio.setDepartamento(departamentoNew);
            }
            List<SucursalRazonSocialTercero> attachedSucursalRazonSocialTerceroListNew = new ArrayList<SucursalRazonSocialTercero>();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach : sucursalRazonSocialTerceroListNew) {
                sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach = em.getReference(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getClass(), sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach.getId());
                attachedSucursalRazonSocialTerceroListNew.add(sucursalRazonSocialTerceroListNewSucursalRazonSocialTerceroToAttach);
            }
            sucursalRazonSocialTerceroListNew = attachedSucursalRazonSocialTerceroListNew;
            municipio.setSucursalRazonSocialTerceroList(sucursalRazonSocialTerceroListNew);
            municipio = em.merge(municipio);
            if (departamentoOld != null && !departamentoOld.equals(departamentoNew)) {
                departamentoOld.setMunicipio(null);
                departamentoOld = em.merge(departamentoOld);
            }
            if (departamentoNew != null && !departamentoNew.equals(departamentoOld)) {
                departamentoNew.setMunicipio(municipio);
                departamentoNew = em.merge(departamentoNew);
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero : sucursalRazonSocialTerceroListOld) {
                if (!sucursalRazonSocialTerceroListNew.contains(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero)) {
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero.setIdMunicipio(null);
                    sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListOldSucursalRazonSocialTercero);
                }
            }
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero : sucursalRazonSocialTerceroListNew) {
                if (!sucursalRazonSocialTerceroListOld.contains(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero)) {
                    Municipio oldIdMunicipioOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getIdMunicipio();
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.setIdMunicipio(municipio);
                    sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    if (oldIdMunicipioOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero != null && !oldIdMunicipioOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.equals(municipio)) {
                        oldIdMunicipioOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero.getSucursalRazonSocialTerceroList().remove(sucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                        oldIdMunicipioOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero = em.merge(oldIdMunicipioOfSucursalRazonSocialTerceroListNewSucursalRazonSocialTercero);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = municipio.getId();
                if (findMunicipio(id) == null) {
                    throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.");
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
            Municipio municipio;
            try {
                municipio = em.getReference(Municipio.class, id);
                municipio.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The municipio with id " + id + " no longer exists.", enfe);
            }
            Departamento departamento = municipio.getDepartamento();
            if (departamento != null) {
                departamento.setMunicipio(null);
                departamento = em.merge(departamento);
            }
            List<SucursalRazonSocialTercero> sucursalRazonSocialTerceroList = municipio.getSucursalRazonSocialTerceroList();
            for (SucursalRazonSocialTercero sucursalRazonSocialTerceroListSucursalRazonSocialTercero : sucursalRazonSocialTerceroList) {
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero.setIdMunicipio(null);
                sucursalRazonSocialTerceroListSucursalRazonSocialTercero = em.merge(sucursalRazonSocialTerceroListSucursalRazonSocialTercero);
            }
            em.remove(municipio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Municipio> findMunicipioEntities() {
        return findMunicipioEntities(true, -1, -1);
    }

    public List<Municipio> findMunicipioEntities(int maxResults, int firstResult) {
        return findMunicipioEntities(false, maxResults, firstResult);
    }

    private List<Municipio> findMunicipioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Municipio.class));
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

    public Municipio findMunicipio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Municipio.class, id);
        } finally {
            em.close();
        }
    }

    public int getMunicipioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Municipio> rt = cq.from(Municipio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
