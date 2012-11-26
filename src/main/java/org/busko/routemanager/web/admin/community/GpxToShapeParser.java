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
package org.busko.routemanager.web.admin.community;

import org.apache.commons.digester.Digester;
import org.busko.routemanager.model.transit.community.RouteOutline;
import org.busko.routemanager.model.transit.gtfs.Shape;
import org.busko.routemanager.model.transit.gtfs.ShapeCollection;

import java.io.ByteArrayInputStream;

public class GpxToShapeParser {

    private ShapeCollection shapeCollection;

    public void parse(RouteOutline routeOutline) {
        if ((routeOutline != null) && routeOutline.getFileContent() != null) {
            shapeCollection = new ShapeCollection();
            shapeCollection.setRoute(routeOutline.getRoute());

            Digester digester = new Digester();
            digester.setValidating( false );
            digester.push(this);
            digester.addObjectCreate("gpx/trk/trkseg/trkpt", Shape.class.getName());
            digester.addSetProperties("gpx/trk/trkseg/trkpt");
            digester.addSetNext("gpx/trk/trkseg/trkpt", "addShape", Shape.class.getName());

            ByteArrayInputStream input = null;
            try {
                input = new ByteArrayInputStream(routeOutline.getFileContent());
                digester.parse(input);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (input != null) try { input.close(); } catch (Exception e) {}
            }
        }
    }
    
    public void addShape(Shape shape) {
        if (shapeCollection == null) return;
        shapeCollection.addShape(shape);
    }

    public ShapeCollection getShapeCollection() {
        return shapeCollection;
    }

    public void setShapeCollection(ShapeCollection shapeCollection) {
        this.shapeCollection = shapeCollection;
    }
}
