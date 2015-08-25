/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.sae.persistence.dao; import com.sae.persistence.util.JpaUtil;

import com.sae.persistence.dao.exceptions.NonexistentEntityException;
import com.sae.persistence.dao.exceptions.PreexistingEntityException;
import com.sae.persistence.domain.Comunicado;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import com.sae.persistence.domain.ObjetoComunicado;
import com.sae.persistence.domain.CopiaComunicado;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author SAE2
 */
public class ComunicadoJpaController implements Serializable {

    public ComunicadoJpaController() {
        this.emf = JpaUtil.getEmf();
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Comunicado comunicado) throws PreexistingEntityException, Exception {
        if (comunicado.getCopiaComunicadoList() == null) {
            comunicado.setCopiaComunicadoList(new ArrayList<CopiaComunicado>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ObjetoComunicado objeto = comunicado.getObjeto();
            if (objeto != null) {
                objeto = em.getReference(objeto.getClass(), objeto.getId());
                comunicado.setObjeto(objeto);
            }
            List<CopiaComunicado> attachedCopiaComunicadoList = new ArrayList<CopiaComunicado>();
            for (CopiaComunicado copiaComunicadoListCopiaComunicadoToAttach : comunicado.getCopiaComunicadoList()) {
                copiaComunicadoListCopiaComunicadoToAttach = em.getReference(copiaComunicadoListCopiaComunicadoToAttach.getClass(), copiaComunicadoListCopiaComunicadoToAttach.getId());
                attachedCopiaComunicadoList.add(copiaComunicadoListCopiaComunicadoToAttach);
            }
            comunicado.setCopiaComunicadoList(attachedCopiaComunicadoList);
            em.persist(comunicado);
            if (objeto != null) {
                objeto.getComunicadoList().add(comunicado);
                objeto = em.merge(objeto);
            }
            for (CopiaComunicado copiaComunicadoListCopiaComunicado : comunicado.getCopiaComunicadoList()) {
                Comunicado oldIdComunicadoOfCopiaComunicadoListCopiaComunicado = copiaComunicadoListCopiaComunicado.getIdComunicado();
                copiaComunicadoListCopiaComunicado.setIdComunicado(comunicado);
                copiaComunicadoListCopiaComunicado = em.merge(copiaComunicadoListCopiaComunicado);
                if (oldIdComunicadoOfCopiaComunicadoListCopiaComunicado != null) {
                    oldIdComunicadoOfCopiaComunicadoListCopiaComunicado.getCopiaComunicadoList().remove(copiaComunicadoListCopiaComunicado);
                    oldIdComunicadoOfCopiaComunicadoListCopiaComunicado = em.merge(oldIdComunicadoOfCopiaComunicadoListCopiaComunicado);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findComunicado(comunicado.getId()) != null) {
                throw new PreexistingEntityException("Comunicado " + comunicado + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Comunicado comunicado) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Comunicado persistentComunicado = em.find(Comunicado.class, comunicado.getId());
            ObjetoComunicado objetoOld = persistentComunicado.getObjeto();
            ObjetoComunicado objetoNew = comunicado.getObjeto();
            List<CopiaComunicado> copiaComunicadoListOld = persistentComunicado.getCopiaComunicadoList();
            List<CopiaComunicado> copiaComunicadoListNew = comunicado.getCopiaComunicadoList();
            if (objetoNew != null) {
                objetoNew = em.getReference(objetoNew.getClass(), objetoNew.getId());
                comunicado.setObjeto(objetoNew);
            }
            List<CopiaComunicado> attachedCopiaComunicadoListNew = new ArrayList<CopiaComunicado>();
            for (CopiaComunicado copiaComunicadoListNewCopiaComunicadoToAttach : copiaComunicadoListNew) {
                copiaComunicadoListNewCopiaComunicadoToAttach = em.getReference(copiaComunicadoListNewCopiaComunicadoToAttach.getClass(), copiaComunicadoListNewCopiaComunicadoToAttach.getId());
                attachedCopiaComunicadoListNew.add(copiaComunicadoListNewCopiaComunicadoToAttach);
            }
            copiaComunicadoListNew = attachedCopiaComunicadoListNew;
            comunicado.setCopiaComunicadoList(copiaComunicadoListNew);
            comunicado = em.merge(comunicado);
            if (objetoOld != null && !objetoOld.equals(objetoNew)) {
                objetoOld.getComunicadoList().remove(comunicado);
                objetoOld = em.merge(objetoOld);
            }
            if (objetoNew != null && !objetoNew.equals(objetoOld)) {
                objetoNew.getComunicadoList().add(comunicado);
                objetoNew = em.merge(objetoNew);
            }
            for (CopiaComunicado copiaComunicadoListOldCopiaComunicado : copiaComunicadoListOld) {
                if (!copiaComunicadoListNew.contains(copiaComunicadoListOldCopiaComunicado)) {
                    copiaComunicadoListOldCopiaComunicado.setIdComunicado(null);
                    copiaComunicadoListOldCopiaComunicado = em.merge(copiaComunicadoListOldCopiaComunicado);
                }
            }
            for (CopiaComunicado copiaComunicadoListNewCopiaComunicado : copiaComunicadoListNew) {
                if (!copiaComunicadoListOld.contains(copiaComunicadoListNewCopiaComunicado)) {
                    Comunicado oldIdComunicadoOfCopiaComunicadoListNewCopiaComunicado = copiaComunicadoListNewCopiaComunicado.getIdComunicado();
                    copiaComunicadoListNewCopiaComunicado.setIdComunicado(comunicado);
                    copiaComunicadoListNewCopiaComunicado = em.merge(copiaComunicadoListNewCopiaComunicado);
                    if (oldIdComunicadoOfCopiaComunicadoListNewCopiaComunicado != null && !oldIdComunicadoOfCopiaComunicadoListNewCopiaComunicado.equals(comunicado)) {
                        oldIdComunicadoOfCopiaComunicadoListNewCopiaComunicado.getCopiaComunicadoList().remove(copiaComunicadoListNewCopiaComunicado);
                        oldIdComunicadoOfCopiaComunicadoListNewCopiaComunicado = em.merge(oldIdComunicadoOfCopiaComunicadoListNewCopiaComunicado);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = comunicado.getId();
                if (findComunicado(id) == null) {
                    throw new NonexistentEntityException("The comunicado with id " + id + " no longer exists.");
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
            Comunicado comunicado;
            try {
                comunicado = em.getReference(Comunicado.class, id);
                comunicado.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The comunicado with id " + id + " no longer exists.", enfe);
            }
            ObjetoComunicado objeto = comunicado.getObjeto();
            if (objeto != null) {
                objeto.getComunicadoList().remove(comunicado);
                objeto = em.merge(objeto);
            }
            List<CopiaComunicado> copiaComunicadoList = comunicado.getCopiaComunicadoList();
            for (CopiaComunicado copiaComunicadoListCopiaComunicado : copiaComunicadoList) {
                copiaComunicadoListCopiaComunicado.setIdComunicado(null);
                copiaComunicadoListCopiaComunicado = em.merge(copiaComunicadoListCopiaComunicado);
            }
            em.remove(comunicado);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Comunicado> findComunicadoEntities() {
        return findComunicadoEntities(true, -1, -1);
    }

    public List<Comunicado> findComunicadoEntities(int maxResults, int firstResult) {
        return findComunicadoEntities(false, maxResults, firstResult);
    }

    private List<Comunicado> findComunicadoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Comunicado.class));
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

    public Comunicado findComunicado(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Comunicado.class, id);
        } finally {
            em.close();
        }
    }

    public int getComunicadoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Comunicado> rt = cq.from(Comunicado.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
