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
import org.busko.routemanager.model.transit.gtfs.Shape;
import org.springframework.transaction.annotation.Transactional;

privileged aspect Shape_Roo_Jpa_ActiveRecord {
    
    @PersistenceContext
    transient EntityManager Shape.entityManager;
    
    public static final EntityManager Shape.entityManager() {
        EntityManager em = new Shape().entityManager;
        if (em == null) throw new IllegalStateException("Entity manager has not been injected (is the Spring Aspects JAR configured as an AJC/AJDT aspects library?)");
        return em;
    }
    
    public static long Shape.countShapes() {
        return entityManager().createQuery("SELECT COUNT(o) FROM Shape o", Long.class).getSingleResult();
    }
    
    public static List<Shape> Shape.findAllShapes() {
        return entityManager().createQuery("SELECT o FROM Shape o", Shape.class).getResultList();
    }
    
    public static Shape Shape.findShape(Long id) {
        if (id == null) return null;
        return entityManager().find(Shape.class, id);
    }
    
    public static List<Shape> Shape.findShapeEntries(int firstResult, int maxResults) {
        return entityManager().createQuery("SELECT o FROM Shape o", Shape.class).setFirstResult(firstResult).setMaxResults(maxResults).getResultList();
    }
    
    @Transactional
    public void Shape.persist() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.persist(this);
    }
    
    @Transactional
    public void Shape.remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        if (this.entityManager.contains(this)) {
            this.entityManager.remove(this);
        } else {
            Shape attached = Shape.findShape(this.id);
            this.entityManager.remove(attached);
        }
    }
    
    @Transactional
    public void Shape.flush() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.flush();
    }
    
    @Transactional
    public void Shape.clear() {
        if (this.entityManager == null) this.entityManager = entityManager();
        this.entityManager.clear();
    }
    
    @Transactional
    public Shape Shape.merge() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Shape merged = this.entityManager.merge(this);
        this.entityManager.flush();
        return merged;
    }
    
}
