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
package org.busko.routemanager.model.transit.gtfs;

import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

/**
 * Maps to the GTFS trips.txt file. All fields are named to match the CSV headers in the file.
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Trip implements GtfsFormatted, Displayable {

    @NotNull
    @Size(max = 30)
    private String serviceId;

    @Size(max = 20)
    private String tripId;

    /**
     * Used to differentiate different service patterns on same route.
     * Can be overridden in stopHeadsign in Stop class.
     */
    @NotNull
    @Size(max = 50)
    private String tripHeadsign;

    /**
     * 0 - out.
     * 1 - in.
     */
    @NotNull
    private int directionId;

    @ManyToOne
    private Route route;
    
    @ManyToOne
    private Calendar calendar;

    // TODO blockId Idea is used when transfers are allowed between two routes

    private String shapeId;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "trip")
    @OrderBy("stopSequence")
    private Set<StopTime> stopTimes = new HashSet<StopTime>();

    @NotNull
    private Boolean explicitTripId;

    public Trip() {
        this.explicitTripId = false;
    }

    public Trip(String serviceId, String tripId, String tripHeadsign, int directionId) {
        this.serviceId = serviceId;
        this.tripId = tripId;
        this.tripHeadsign = tripHeadsign;
        this.directionId = directionId;
        this.explicitTripId = false;
    }

    public String getRouteId() {
        if (route == null) return null;
        return route.getRouteId();
    }

//    public String getServiceId() {
//        if (calendar == null) return null;
//        return calendar.getServiceId();
//    }

    public void addStopTime(StopTime stopTime) {
        stopTime.setTrip(this);
        stopTimes.add(stopTime);
    }

    /**
     * If the tripId is to be calculated then the first StopTime is used as the name of the route.
     *
     * directionId: 0 - out, 1 - in.
     */
    public String getFullTripId() {
        if (explicitTripId) return tripId;
        StringBuilder builder = new StringBuilder();
        if (route != null) {
            builder.append(route.getRouteId());
        }
        builder.append(directionId == 1 ? "in_" : "out_");
        if (!stopTimes.isEmpty()) {
            builder.append(stopTimes.iterator().next().getArrivalTime());
        }
        return builder.toString();
    }

    @Override
    public String getGtfsFileName() {
        return "trips.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getRouteId()).append(',').append(serviceId).append(',').append(getFullTripId()).append(',')
                                  .append(tripHeadsign).append(',').append(directionId).append(",,").append(shapeId).toString();
    }

    @Override
    public boolean isInclude() {
        if (route != null) return route.isInclude();
        return false;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }
}
