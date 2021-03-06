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
package org.busko.routemanager.model.transit;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.busko.routemanager.model.Displayable;
import org.busko.routemanager.model.transit.gtfs.Agency;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Country implements Displayable {

    @NotNull
    @Size(min = 3, max = 3)
    private String code;

    @NotNull
    @Size(max = 256)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Set<Region> regions = new HashSet<Region>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "country")
    private Set<Agency> agencies = new HashSet<Agency>();

    @Override
    public String getDisplayName() {
        return toString();
    }
}
