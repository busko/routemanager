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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findCalendarsByServiceIdEquals" })
public class Calendar implements GtfsFormatted, Displayable {

    public static final int MON = 1;

    public static final int TUE = 2;

    public static final int WED = 4;

    public static final int THR = 8;

    public static final int FRI = 16;

    public static final int SAT = 32;

    public static final int SUN = 64;

    public static final int MON_FRI = MON + TUE + WED + THR + FRI;

    public static final int MON_SAT = MON + TUE + WED + THR + FRI + SAT;

    public static final int MON_SUN = MON + TUE + WED + THR + FRI + SAT + SUN;

    public static final int SAT_SUN = SAT + SUN;

    @NotNull
    @Size(max = 30)
    private String serviceId;

    @NotNull
    private Boolean monday;

    @NotNull
    private Boolean tuesday;

    @NotNull
    private Boolean wednesday;

    @NotNull
    private Boolean thursday;

    @NotNull
    private Boolean friday;

    @NotNull
    private Boolean saturday;

    @NotNull
    private Boolean sunday;

    @NotNull
    @Size(max = 20)
    private String startDate;

    @NotNull
    @Size(max = 20)
    private String endDate;

    public Calendar() {
    }

    public Calendar(int frequency, Boolean monday, Boolean tuesday, Boolean wednesday, Boolean thursday, Boolean friday, Boolean saturday, Boolean sunday) {
        this.serviceId = Integer.toString(frequency);
        this.monday = monday;
        this.tuesday = tuesday;
        this.wednesday = wednesday;
        this.thursday = thursday;
        this.friday = friday;
        this.saturday = saturday;
        this.sunday = sunday;
        startDate = "20120924";
        endDate = "20121231";
    }

    @Override
    public String getGtfsFileName() {
        return "calendar.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "service_id,monday,tuesday,wednesday,thursday,friday,saturday,sunday,start_date,end_date";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(serviceId).append(',').append(monday).append(',').append(tuesday).append(',')
                                  .append(wednesday).append(',').append(thursday).append(',').append(friday).append(',')
                                  .append(saturday).append(',').append(sunday).append(',').append(startDate).append(',')
                                  .append(endDate).toString();
    }

    @Override
    public boolean isInclude() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }
}
