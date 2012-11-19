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

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

/**
 * Idea is to be able to display Agency information by region on a map.
 * Want to be able to capture down to one bus agencies such as those that would run a local school bus.
 * Not sure if this is needed?
 */
@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Region implements Displayable {

    @NotNull
    @Size(max = 256)
    private String name;

    /**
     * The uniqiue encoding should be numeric, unique across a country and ideally 2 digits.
     */
    @NotNull
    private int uniqueEncoding;

    @ManyToOne
    private Country country;

    @Override
    public String getDisplayName() {
        return toString();
    }
}
