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
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.busko.routemanager.model.transit.gtfs.Route;
import org.busko.routemanager.model.transit.gtfs.Stop;

privileged aspect Stop_Roo_Finder {
    
    public static TypedQuery<Stop> Stop.findStopsByRoute(Route route) {
        if (route == null) throw new IllegalArgumentException("The route argument is required");
        EntityManager em = Stop.entityManager();
        TypedQuery<Stop> q = em.createQuery("SELECT o FROM Stop AS o WHERE o.route = :route", Stop.class);
        q.setParameter("route", route);
        return q;
    }
    
}
