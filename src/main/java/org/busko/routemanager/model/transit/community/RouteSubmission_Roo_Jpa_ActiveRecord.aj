// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.busko.routemanager.model.transit.community;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.busko.routemanager.model.transit.community.RouteSubmission;
import org.springframework.transaction.annotation.Transactional;

privileged aspect RouteSubmission_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager RouteSubmission.entityManager;
    
    public static final EntityManager RouteSubmission.entityManager() {
        EntityManager em = new RouteSubmission().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long RouteSubmission.countRouteSubmissions() {
        return entityManager().createQuery("SELECT COUNT(o) FROM RouteSubmission o", Long.class).getSingleResult();
    }
    
    public static List<RouteSubmission> RouteSubmission.findAllRouteSubmissions() {
        return entityManager().createQuery("SELECT o FROM RouteSubmission o", RouteSubmission.class).getResultList();
    }
    
    public static RouteSubmission RouteSubmission.findRouteSubmission(Long id) {
        if (id == null) return null;
        return entityManager().find(RouteSubmission.class, id);
    }
    
    public static List<RouteSubmission> RouteSubmission.findRouteSubmissionEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM RouteSubmission o", RouteSubmission.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void RouteSubmission.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void RouteSubmission.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            RouteSubmission attached = RouteSubmission.findRouteSubmission(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void RouteSubmission.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void RouteSubmission.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public RouteSubmission RouteSubmission.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        RouteSubmission merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
