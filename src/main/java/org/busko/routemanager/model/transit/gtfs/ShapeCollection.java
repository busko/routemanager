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
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ShapeCollection {

    @Size(max = 20)
    private String shapeId;

    @ManyToOne
    private Route route;

    @NotNull
    private Boolean explicitShapeId;

    public ShapeCollection() {
        this.shapeId = "S";
        this.explicitShapeId = false;
    }

    public String getFullShapeId() {
        if (explicitShapeId) return shapeId;
        if (route != null) return new StringBuilder().append("S").append(route.getRouteId()).toString();
        return shapeId;
    }
}
