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
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Stop implements GtfsFormatted, Displayable {

    @NotNull
    @Size(max = 20)
    private String stopId;

    @NotNull
    @Size(max = 100)
    private String stopName;

    @Size(max = 256)
    private String stopDesc;

    @NotNull
    @Size(max = 20)
    private String stopLat;

    @NotNull
    @Size(max = 20)
    private String stopLon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stop")
    private Set<StopTime> stopTimes = new HashSet<StopTime>();

    @ManyToMany
    private Set<Route> routes = new HashSet<Route>();

    @NotNull
    private Boolean explicitStopId;

    public Stop() {
        this.explicitStopId = false;
    }

    public Stop(String stopId, String stopName, String stopDesc, String stopLat, String stopLon) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopDesc = stopDesc;
        this.stopLat = stopLat;
        this.stopLon = stopLon;
        this.explicitStopId = false;
    }

    public void addStopTime(StopTime stopTime) {
        stopTime.setStop(this);
        stopTimes.add(stopTime);
    }

    public String getFullStopId() {
        if (explicitStopId) return stopId;
        String fullStopId = stopId.length() == 1 ? ("0" + stopId) : stopId;
        if (routes.size() > 1) {
            // todo Should check agency not different
            return new StringBuilder().append(routes.iterator().next().getAgency().getUniqueEncoding()).append("00").append(fullStopId).toString();
        }
        if (routes.size() == 1) {
            Route route = routes.iterator().next();
            return new StringBuilder().append(route.getAgency().getUniqueEncoding()).append(route.getUniqueEncoding()).append(fullStopId).toString();
        }
        return stopId;
    }

    @Override
    public String getGtfsFileName() {
        return "stops.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "stop_id,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getFullStopId()).append(',').append(formatGtfsString(stopName)).append(',').append(formatGtfsString(stopDesc))
                                  .append(',').append(stopLat).append(',').append(stopLon).append(',').append("").append(',').toString();
    }

    @Override
    public boolean isInclude() {
        if (!routes.isEmpty()) {
            for (Route route : routes) {
                if (route.isInclude()) {
                    return true;
                }
            }
        }
        return true;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }

    private String formatGtfsString(String string) {
        if (string == null) return "";
        return string.replace(',', ';');
    }
}
