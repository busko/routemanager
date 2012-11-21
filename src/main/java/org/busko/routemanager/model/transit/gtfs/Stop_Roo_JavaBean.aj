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
import java.util.Set;
import org.busko.routemanager.model.transit.gtfs.Agency;
import org.busko.routemanager.model.transit.gtfs.Route;
import org.busko.routemanager.model.transit.gtfs.Stop;
import org.busko.routemanager.model.transit.gtfs.StopTime;

privileged aspect Stop_Roo_JavaBean {
    
    public String Stop.getStopId() {
        return this.stopId;
    }
    
    public void Stop.setStopId(String stopId) {
        this.stopId = stopId;
    }
    
    public String Stop.getStopName() {
        return this.stopName;
    }
    
    public void Stop.setStopName(String stopName) {
        this.stopName = stopName;
    }
    
    public String Stop.getStopDesc() {
        return this.stopDesc;
    }
    
    public void Stop.setStopDesc(String stopDesc) {
        this.stopDesc = stopDesc;
    }
    
    public String Stop.getStopLat() {
        return this.stopLat;
    }
    
    public void Stop.setStopLat(String stopLat) {
        this.stopLat = stopLat;
    }
    
    public String Stop.getStopLon() {
        return this.stopLon;
    }
    
    public void Stop.setStopLon(String stopLon) {
        this.stopLon = stopLon;
    }
    
    public Set<StopTime> Stop.getStopTimes() {
        return this.stopTimes;
    }
    
    public void Stop.setStopTimes(Set<StopTime> stopTimes) {
        this.stopTimes = stopTimes;
    }
    
    public Agency Stop.getAgency() {
        return this.agency;
    }
    
    public void Stop.setAgency(Agency agency) {
        this.agency = agency;
    }
    
    public Route Stop.getRoute() {
        return this.route;
    }
    
    public void Stop.setRoute(Route route) {
        this.route = route;
    }
    
    public Boolean Stop.getExplicitStopId() {
        return this.explicitStopId;
    }
    
    public void Stop.setExplicitStopId(Boolean explicitStopId) {
        this.explicitStopId = explicitStopId;
    }
    
}
