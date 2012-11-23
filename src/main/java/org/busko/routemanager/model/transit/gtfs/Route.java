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

import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.busko.routemanager.model.Displayable;
import org.busko.routemanager.model.transit.community.RouteOutline;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

/**
 * Maps to the GTFS routes.txt file. All fields are named to match the CSV headers in the file.
 */
@RooJavaBean
@RooJpaActiveRecord
public class Route implements GtfsFormatted, Displayable {

    public static final int ROUTETYPE_BUS = 3;

    @NotNull
    @Size(max = 20)
    private String routeId;

    @NotNull
    @Size(max = 30)
    private String routeShortName;

    @Size(max = 100)
    private String routeLongName;

    @Size(max = 256)
    private String routeDesc;

    @Size(max = 20)
    private String routeBlockId;

    @Size(max = 6)
    private String routeColor;

    @ManyToOne
    private Agency agency;

    @NotNull
    private int routeType;

    // TODO routeColor and routeTextColor may be supported in OneBusAway and might help differentiate agencies or routes

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "route")
    private Set<Trip> trips = new HashSet<Trip>();

    /**
     * The uniqiue encoding should be numeric, unique across an agency and ideally 2 digits.
     */
    @NotNull
    @Size(max = 20)
    private String uniqueEncoding;

    @NotNull
    private Boolean live;

    public Route() {
    }

    public Route(Agency agency, String routeId, String uniqueEncoding, String routeShortName, String routeLongName, String routeBlockId, String routeColor) {
        this.agency = agency;
        this.routeId = routeId;
        this.uniqueEncoding = uniqueEncoding;
        this.routeShortName = routeShortName;
        this.routeLongName = routeLongName;
        this.routeBlockId = routeBlockId;
        this.routeColor = routeColor;
    }

    public String getAgencyId() {
        if (agency == null) return null;
        return agency.getAgencyId();
    }

    public void addTrip(Trip trip) {
        trip.setRoute(this);
        trips.add(trip);
    }

    @Transactional
    public void remove() {
        if (this.entityManager == null) this.entityManager = entityManager();
        Route route;
        if (this.entityManager.contains(this)) {
            route = this;
        } else {
            route = Route.findRoute(this.getId());
        }

        // Need to cancel any associated route outline links
        for (RouteOutline routeOutline : RouteOutline.findRouteOutlinesByRoute(route).getResultList()) {
            routeOutline.setRoute(null);
            routeOutline.merge();
        }

        // Need to cancel any associated stop links
        for (Stop stop : Stop.findStopsByRoute(route).getResultList()) {
            stop.setRoute(null);
            stop.merge();
        }

        this.entityManager.remove(route);
    }

    @Override
    public String getGtfsFileName() {
        return "routes.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "route_id,agency_id,route_short_name,route_long_name,route_desc,route_type,route_url,route_color,route_text_color\n" +
               "QT6,Connectabus,QT6,Kelvin Heights 6,,3,,,\n" +
               "QT8,Connectabus,QT8,Arthurs Point 8,,3,,,\n" +
               "QT9,Connectabus,QT9,Fernhill / Sunshine Bay 9,,3,,,\n" +
               "QT10,Connectabus,QT10,Arrowtown 10,,3,,,\n" +
               "QT11,Connectabus,QT11,Queenstown to Frankton then Airport & Remarkables Park 11,,3,,,\n" +
               "QT12,Connectabus,QT12,Events Centre / Lakes Hayes Estate 12,,3,,,\n" +
               "GoBus29,GoBus,29,Normanby - St Clair,,3,,,\n" +
               "GoBus66,GoBus,66,Roslyn/Maori Hill/Prospect Park - University/Octagon,,3,,,\n" +
               "GoBus67,GoBus,67,University/Octagon - Roslyn/Maori Hill/Prospect Park,,3,,,\n" +
               "INVFreebieBus,InvercargillCity,INF,Freebie Bus,,3,,,\n" +
               "INVPurpleCircleSouth,InvercargillCity,INS,Purple Circle South,,3,,,\n" +
               "INVPurpleCircleNorth,InvercargillCity,INN,Purple Circle North,,3,,,\n" +
               "NB-INV-QT,NakedBus,NB1,Naked Bus Invercargill-Queenstown,,3,,,\n" +
               "NB-INV-DUN,NakedBus,NB2,Naked Bus Invercargill-Dunedin,,3,,,\n" +
               "NB-INV-TEA,NakedBus,NB3,Naked Bus Invercargill-Te Anau,,3,,,\n" +
               "NB-TEA-QT,NakedBus,NB4,Naked Bus Te Anau-Queenstown,,3,,,";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(routeId).append(',').append(getAgencyId()).append(',').append(routeShortName).append(',')
                                  .append(routeLongName).append(',').append(routeDesc).append(',').append(routeType).append(',')
                                  .append("").append(',').append(routeColor).append(',').append("").toString();
    }

    @Override
    public String getUniqueId() {
        return getId().toString();
    }

    @Override
    public boolean isInclude() {
        return live;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }

    public String toString() {
        return routeShortName;
    }
}
