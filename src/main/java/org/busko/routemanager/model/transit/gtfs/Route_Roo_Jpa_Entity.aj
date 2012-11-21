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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;
import org.busko.routemanager.model.transit.gtfs.Route;

privileged aspect Route_Roo_Jpa_Entity {
    
    declare @type: Route: @Entity;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long Route.id;
    
    @Version
    @Column(name = "version")
    private Integer Route.version;
    
    public Long Route.getId() {
        return this.id;
    }
    
    public void Route.setId(Long id) {
        this.id = id;
    }
    
    public Integer Route.getVersion() {
        return this.version;
    }
    
    public void Route.setVersion(Integer version) {
        this.version = version;
    }
    
}
