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
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Shape implements Displayable, Comparable {

    @NotNull
    @Size(max = 20)
    private String shapePtLat;

    @NotNull
    @Size(max = 20)
    private String shapePtLon;

    @NotNull
    private int shapePtSequence;

    @ManyToOne
    private ShapeCollection shapeCollection;

    public String getLat() {
        return shapePtLat;
    }

    public void setLat(String lat) {
        this.shapePtLat = lat;
    }

    public String getLon() {
        return shapePtLon;
    }

    public void setLon(String lon) {
        this.shapePtLon = lon;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }

    @Override
    public int compareTo(Object o) {
        if (shapePtSequence < ((Shape)o).shapePtSequence) return -1;
        if (shapePtSequence > ((Shape)o).shapePtSequence) return 1;
        return 0;
    }
}
