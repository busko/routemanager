// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.busko.routemanager.model.transit;
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
import java.util.Set;
import org.busko.routemanager.model.transit.Country;
import org.busko.routemanager.model.transit.Region;
import org.busko.routemanager.model.transit.gtfs.Agency;

privileged aspect Country_Roo_JavaBean {
    
    public String Country.getCode() {
        return this.code;
    }
    
    public void Country.setCode(String code) {
        this.code = code;
    }
    
    public String Country.getName() {
        return this.name;
    }
    
    public void Country.setName(String name) {
        this.name = name;
    }
    
    public Set<Region> Country.getRegions() {
        return this.regions;
    }
    
    public void Country.setRegions(Set<Region> regions) {
        this.regions = regions;
    }
    
    public Set<Agency> Country.getAgencies() {
        return this.agencies;
    }
    
    public void Country.setAgencies(Set<Agency> agencies) {
        this.agencies = agencies;
    }
    
}
