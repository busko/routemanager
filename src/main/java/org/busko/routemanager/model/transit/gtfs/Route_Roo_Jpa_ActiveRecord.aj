// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.busko.routemanager.model.transit.gtfs;
/**
 * Copyright (c) 2012 Busko Trust
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.busko.routemanager.model.transit.gtfs.Route;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Route_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Route.entityManager;
    
    public static final EntityManager Route.entityManager() {
        EntityManager em = new Route().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Route.countRoutes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Route o", Long.class).getSingleResult();
    }
    
    public static List<Route> Route.findAllRoutes() {
        return entityManager().createQuery("SELECT o FROM Route o", Route.class).getResultList();
    }
    
    public static Route Route.findRoute(Long id) {
        if (id == null) return null;
        return entityManager().find(Route.class, id);
    }
    
    public static List<Route> Route.findRouteEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Route o", Route.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Route.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Route.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Route.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Route Route.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Route merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
