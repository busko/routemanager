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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Maps to the GTFS stop_times.txt file. All fields are named to match the CSV headers in the file.
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class StopTime implements GtfsFormatted, Displayable {

    @NotNull
    private String arrivalTime;

    @NotNull
    private String departureTime;

    /**
     * TODO May want to link stops together in some way so that the stopSequence can be auto-generated (might also detect common stops)
     */
    @NotNull
    private int stopSequence;

    // TODO stopHeadsign - used if headsign changes during the trip
    // TODO pickupType, dropOffType - useful for long distance routes to mark the type of stop (eg request stop)
    // TODO shapeDistTravelled - used in trip planning to show sections of shape and also needs to link into shapes.txt

    @ManyToOne
    private Trip trip;

    @ManyToOne
    private Stop stop;

    public StopTime() {
    }

    public StopTime(String arrivalTime, String departureTime, int stopSequence) {
        this.arrivalTime = arrivalTime;
        this.departureTime = departureTime;
        this.stopSequence = stopSequence;
    }

    public String getTripId() {
        if (trip == null) return null;
        return trip.getRouteId();
    }

    public String getStopId() {
        if (stop == null) return null;
        return stop.getStopId();
    }

    public int getDirectionId() {
        if (trip == null) return 0;
        return trip.getDirectionId();
    }

    @Override
    public String getGtfsFileName() {
        return "stop_times.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "trip_id,arrival_time,departure_time,stop_id,stop_sequence,stop_headsign,pickup_type,drop_off_type,shape_dist_traveled";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getTripId()).append(',').append(arrivalTime).append(',').append(departureTime).append(',')
                                  .append(getStopId()).append(',').append(getDirectionId()).append(",,").append(stopSequence)
                                  .append(",,,,").toString();
    }

    @Override
    public boolean isInclude() {
        return stop.isInclude();
    }

    @Override
    public String getDisplayName() {
        return toString();
    }
}
