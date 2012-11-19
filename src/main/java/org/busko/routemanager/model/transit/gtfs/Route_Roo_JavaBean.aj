// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.busko.routemanager.model.transit.gtfs;

import java.util.Set;
import org.busko.routemanager.model.transit.gtfs.Agency;
import org.busko.routemanager.model.transit.gtfs.Route;
import org.busko.routemanager.model.transit.gtfs.Trip;

privileged aspect Route_Roo_JavaBean {
    
    public String Route.getRouteId() {
        return this.routeId;
    }
    
    public void Route.setRouteId(String routeId) {
        this.routeId = routeId;
    }
    
    public String Route.getRouteShortName() {
        return this.routeShortName;
    }
    
    public void Route.setRouteShortName(String routeShortName) {
        this.routeShortName = routeShortName;
    }
    
    public String Route.getRouteLongName() {
        return this.routeLongName;
    }
    
    public void Route.setRouteLongName(String routeLongName) {
        this.routeLongName = routeLongName;
    }
    
    public String Route.getRouteDesc() {
        return this.routeDesc;
    }
    
    public void Route.setRouteDesc(String routeDesc) {
        this.routeDesc = routeDesc;
    }
    
    public String Route.getRouteBlockId() {
        return this.routeBlockId;
    }
    
    public void Route.setRouteBlockId(String routeBlockId) {
        this.routeBlockId = routeBlockId;
    }
    
    public String Route.getRouteColor() {
        return this.routeColor;
    }
    
    public void Route.setRouteColor(String routeColor) {
        this.routeColor = routeColor;
    }
    
    public Agency Route.getAgency() {
        return this.agency;
    }
    
    public void Route.setAgency(Agency agency) {
        this.agency = agency;
    }
    
    public int Route.getRouteType() {
        return this.routeType;
    }
    
    public void Route.setRouteType(int routeType) {
        this.routeType = routeType;
    }
    
    public Set<Trip> Route.getTrips() {
        return this.trips;
    }
    
    public void Route.setTrips(Set<Trip> trips) {
        this.trips = trips;
    }
    
    public String Route.getUniqueEncoding() {
        return this.uniqueEncoding;
    }
    
    public void Route.setUniqueEncoding(String uniqueEncoding) {
        this.uniqueEncoding = uniqueEncoding;
    }
    
    public Boolean Route.getLive() {
        return this.live;
    }
    
    public void Route.setLive(Boolean live) {
        this.live = live;
    }
    
}
