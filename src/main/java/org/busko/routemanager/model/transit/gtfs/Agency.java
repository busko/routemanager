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

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.busko.routemanager.model.Displayable;
import org.busko.routemanager.model.transit.Country;
import org.busko.routemanager.model.transit.Region;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;

@RooJavaBean
@RooJpaActiveRecord(finders = { "findAgencysByAgencyIdEquals" })
public class Agency implements GtfsFormatted, Displayable {

    @NotNull
    @Size(max = 30)
    private String agencyId;

    @NotNull
    @Size(max = 256)
    private String agencyName;

    @NotNull
    @Size(max = 256)
    private String agencyUrl;

    @Size(max = 100)
    private String agencyTimezone;

    @Size(max = 20)
    private String agencyPhone;

    @Size(max = 20)
    private String agencyLang;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "agency")
    private Set<Route> routes = new HashSet<Route>();

    @NotNull
    private String uniqueEncoding;

    @NotNull
    private Boolean live;

    @ManyToOne
    private Country country;

    @ManyToOne
    private Region region;

    public Agency() {
    }

    public Agency(String agencyId, String agencyName, String agencyUrl, String agencyTimezone, String agencyPhone, String agencyLang, String uniqueEncoding) {
        this.agencyId = agencyId;
        this.agencyName = agencyName;
        this.agencyUrl = agencyUrl;
        this.agencyTimezone = agencyTimezone;
        this.agencyPhone = agencyPhone;
        this.agencyLang = agencyLang;
        this.uniqueEncoding = uniqueEncoding;
        this.live = false;
    }

    @Override
    public String getGtfsFileName() {
        return "agency.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "agency_id,agency_name,agency_url,agency_timezone,agency_phone,agency_lang";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(agencyId).append(',').append(agencyName).append(',').append(agencyUrl).append(',')
                                  .append(agencyTimezone).append(',').append(agencyPhone).append(',').append(agencyLang).toString();
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
        if (agencyName == null) return "";
        return new StringBuilder().append(agencyName).append(" [").append(uniqueEncoding).append(']').toString();
    }
}
