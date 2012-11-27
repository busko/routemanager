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
import java.util.Set;
import java.util.TreeSet;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class ShapeCollection implements GtfsFormatted, Displayable {

    @Size(max = 20)
    private String shapeId;

    @ManyToOne
    private Route route;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "shapeCollection")
    @OrderBy("shapePtSequence")
    private Set<Shape> shapes = new TreeSet<Shape>();

    @NotNull
    private Boolean explicitShapeId;

    public ShapeCollection() {
        this.shapeId = "S";
        this.explicitShapeId = false;
    }

    public void addShape(Shape shape) {
        shape.setShapeCollection(this);
        shape.setShapePtSequence(10000 + shapes.size());
        shapes.add(shape);
    }

    public String getFullShapeId() {
        if (explicitShapeId) return shapeId;
        if (route != null) return new StringBuilder().append("S").append(route.getRouteId()).toString();
        return shapeId;
    }

    @Override
    public String getGtfsFileName() {
        return "shapes.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence";
    }

    @Override
    public String getGtfsData() {
        StringBuilder builder = new StringBuilder();
        for (Shape shape : shapes) {
            builder.append(getFullShapeId()).append(',').append(shape.getShapePtLat())
                   .append(',').append(shape.getShapePtLon()).append(',').append(shape.getShapePtSequence()).append("\n");
        }
        return builder.toString();
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
