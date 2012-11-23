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
        return "route_id,service_id,trip_id,trip_headsign,direction_id,block_id,shape_id\n" +
                "QT6,WEEKDAY,QT6out_0656,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_0726,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_0756,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_0826,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_0856,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_0956,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1056,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1156,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1256,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1356,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1456,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1556,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1656,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1726,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1756,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1826,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1856,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1926,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_1956,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6out_2026,Kelvin Heights,0,,SQT6out\n" +
                "QT6,WEEKDAY,QT6in_0711,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_0741,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_0811,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_0841,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_0911,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1011,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1111,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1211,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1311,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1411,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1511,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1611,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1711,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1741,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1811,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1841,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1911,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_1941,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_2011,Frankton Hub,1,,SQT6in\n" +
                "QT6,WEEKDAY,QT6in_2041,Frankton Hub,1,,SQT6in\n" +
                "QT8,WEEKDAY,QT8out_0720,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_0750,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_0820,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_0920,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1020,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1120,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1220,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1320,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1420,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1520,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1620,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1650,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1720,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1750,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1820,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1905,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_1935,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_2005,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8out_2035,Arthurs Point,0,,SQT8out\n" +
                "QT8,WEEKDAY,QT8in_0634,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_0704,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_0734,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_0834,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_0934,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1034,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1134,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1234,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1334,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1434,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1534,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1604,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1634,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1704,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1734,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1804,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1834,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1919,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_1949,Queenstown,1,,SQT8in\n" +
                "QT8,WEEKDAY,QT8in_2019,Queenstown,1,,SQT8in\n" +
                "QT9,WEEKDAY,QT9out_0650,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_0710,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_0744,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_0814,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_0844,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_0914,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_0944,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1014,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1044,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1114,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1144,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1214,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1244,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1314,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1344,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1414,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1444,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1514,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1544,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1614,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1644,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1714,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1744,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1814,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1844,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1914,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_1944,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_2014,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_2044,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_2114,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_2214,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9out_2249,Fernhill / Sunshine Bay,0,,SQT9out\n" +
                "QT9,WEEKDAY,QT9in_0700,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_0724,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_0758,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_0824,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_0854,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_0924,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_0954,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1024,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1054,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1124,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1154,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1224,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1254,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1324,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1354,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1424,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1454,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1524,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1554,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1624,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1658,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1728,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1754,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1828,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1854,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1924,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_1954,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_2024,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_2054,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_2124,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_2224,Queenstown,1,,SQT9in\n" +
                "QT9,WEEKDAY,QT9in_2259,Queenstown,1,,SQT9in\n" +
                "QT10,WEEKDAY,QT10out_0746,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_0846,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1046,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1146,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1346,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1546,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1716,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1746,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1816,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_1846,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_2120,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_2250,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10out_2325,Arrowtown,0,,SQT10out\n" +
                "QT10,WEEKDAY,QT10in_0603,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_0620,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_0633,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_0706,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_0736,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_0806,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1006,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1106,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1206,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1406,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1606,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1736,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_1806,Frankton Hub,1,,SQT10in\n" +
                "QT10,WEEKDAY,QT10in_2225,Frankton Hub,1,,SQT10in\n" +
                "QT11,WEEKDAY,QT11out_0650,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0705,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0720,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0735,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0750,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0805,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0820,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0835,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0850,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0905,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0920,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0935,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_0950,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1005,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1020,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1035,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1050,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1105,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1120,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1135,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1150,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1205,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1220,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1235,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1250,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1305,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1320,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1335,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1350,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1405,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1420,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1435,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1450,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1505,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1520,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1535,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1550,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1605,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1620,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1635,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1650,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1705,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1720,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1735,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1750,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1805,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1820,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1835,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1850,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1905,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_1935,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_2005,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_2035,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_2105,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_2135,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_2235,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11out_2310,Frankton then Airport & Remarkables Park,0,,SQT11out\n" +
                "QT11,WEEKDAY,QT11in_0626,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0641,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0653,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0708,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0723,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0738,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0753,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0808,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0823,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0838,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0853,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0908,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0923,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0938,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_0953,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1008,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1023,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1038,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1053,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1108,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1123,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1138,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1153,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1208,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1223,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1238,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1253,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1308,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1323,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1338,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1353,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1408,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1423,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1438,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1453,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1508,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1523,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1538,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1553,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1608,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1623,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1638,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1653,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1708,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1723,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1738,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1753,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1808,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1823,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1838,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1853,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1908,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1923,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_1953,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_2023,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_2053,Frankton then Queenstown,1,,SQT11in\n" +
                "QT11,WEEKDAY,QT11in_2153,Frankton then Queenstown,1,,SQT11in\n" +
                "QT12,WEEKDAY,QT12out_0726,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_0756,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_0826,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_0926,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1026,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1126,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1226,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1326,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1426,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1526,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1556,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1626,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1656,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1726,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1756,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1826,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1856,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1926,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_1956,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12out_2026,Event Centre / Lake Hayes Estate,0,,SQT12out\n" +
                "QT12,WEEKDAY,QT12in_0711,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_0741,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_0811,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_0841,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_0941,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1041,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1141,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1241,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1341,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1441,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1541,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1611,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1641,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1711,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1741,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1811,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1841,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1911,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_1941,Frankton Hub,1,,SQT12in\n" +
                "QT12,WEEKDAY,QT12in_2011,Frankton Hub,1,,SQT12in\n" +
                "GoBus29,WEEKDAY,GoBus29_1830,St Clair,0,,S29\n" +
                "GoBus29,WEEKDAY,GoBus29_1930,St Clair,0,,S29\n" +
                "GoBus29,WEEKDAY,GoBus29_2030,St Clair,0,,S29\n" +
                "GoBus29,WEEKDAY,GoBus29_2100,St Clair,0,,S29\n" +
                "GoBus29,WEEKDAY,GoBus29_2130,St Clair,0,,S29\n" +
                "GoBus29,WEEKDAY,GoBus29_2230,St Clair,0,,S29\n" +
                "GoBus29,WEEKDAY,GoBus29_2330,St Clair,0,,S29\n" +
                "GoBus66,WEEKDAY,GoBus66_0630,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_0700,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_0730,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_0800,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_0830,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_0900,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_0930,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1000,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1030,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1100,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1130,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1200,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1230,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1300,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1330,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1400,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1430,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1500,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1530,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1600,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1630,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1700,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1730,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1800,University/Octagon,0,,S66\n" +
                "GoBus66,WEEKDAY,GoBus66_1830,University/Octagon,0,,S66\n" +
                "GoBus67,WEEKDAY,GoBus67_0630,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_0700,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_0730,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_0800,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_0830,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_0900,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_0930,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1000,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1030,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1100,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1130,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1200,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1230,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1300,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1330,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1400,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1430,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1500,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1530,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1600,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1630,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1700,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1730,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1800,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "GoBus67,WEEKDAY,GoBus67_1830,Roslyn/Maori Hill/Prospect Park,0,,S67\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1000,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1015,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1030,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1045,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1100,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1115,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1130,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1145,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1200,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1215,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1230,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1245,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1300,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1315,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1330,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1345,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1400,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1415,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVFreebieBus,WEEKDAY,INVFreebieBus_1430,Freebie Bus,0,,SINVFreebieBus\n" +
                "INVPurpleCircleSouth,WEEKDAY,INVPurpleCircleSouth_0900,Purple Circle South,0,,SINVPurpleCircleSouth\n" +
                "INVPurpleCircleSouth,WEEKDAY,INVPurpleCircleSouth_1030,Purple Circle South,0,,SINVPurpleCircleSouth\n" +
                "INVPurpleCircleSouth,WEEKDAY,INVPurpleCircleSouth_1200,Purple Circle South,0,,SINVPurpleCircleSouth\n" +
                "INVPurpleCircleSouth,WEEKDAY,INVPurpleCircleSouth_1330,Purple Circle South,0,,SINVPurpleCircleSouth\n" +
                "INVPurpleCircleNorth,WEEKDAY,INVPurpleCircleNorth_0945,Purple Circle North,0,,SINVPurpleCircleNorth\n" +
                "INVPurpleCircleNorth,WEEKDAY,INVPurpleCircleNorth_1115,Purple Circle North,0,,SINVPurpleCircleNorth\n" +
                "INVPurpleCircleNorth,WEEKDAY,INVPurpleCircleNorth_1245,Purple Circle North,0,,SINVPurpleCircleNorth\n" +
                "NB-INV-QT,WEEKDAY,NB-INV-QTout_1300,Invercargill - Queenstown,0,,SNB-INV-QTout\n" +
                "NB-INV-QT,WEEKDAY,NB-INV-QTin_0900,Queenstown - Invercargill,1,,SNB-INV-QTin\n" +
                "NB-INV-DUN,WEEKDAY,NB-INV-DUNout_1020,Invercargill - Dunedin,0,,SNB-INV-DUNout\n" +
                "NB-INV-DUN,WEEKDAY,NB-INV-DUNin_1515,Dunedin - Invercargill,1,,SNB-INV-DUNin\n" +
                "NB-INV-TEA,WEEKDAY,NB-INV-TEAout_1300,Invercargill - Te Anau,0,,SNB-INV-TEAout\n" +
                "NB-INV-TEA,WEEKDAY,NB-INV-TEAin_0900,Te Anau - Invercargill,1,,SNB-INV-TEAin\n" +
                "NB-TEA-QT,WEEKDAY,NB-TEA-QTout_0900,Te Anau - Queenstown,0,,SNB-TEA-QTout\n" +
                "NB-TEA-QT,WEEKDAY,NB-TEA-QTin_1300,Queenstown - Te Anau,1,,SNB-TEA-QTin";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getRouteId()).append(',').append(serviceId).append(',').append(getFullTripId()).append(',')
                                  .append(tripHeadsign).append(',').append(directionId).append(",,").append(shapeId).toString();
    }

    @Override
    public String getUniqueId() {
        return getId().toString();
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
