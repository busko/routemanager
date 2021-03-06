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
package org.busko.routemanager.web.admin.gtfs;

import org.busko.routemanager.model.transit.gtfs.Agency;
import org.busko.routemanager.model.transit.gtfs.Route;
import org.busko.routemanager.model.transit.gtfs.Shape;
import org.busko.routemanager.model.transit.gtfs.Trip;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/admin/gtfs/routes")
@Controller
@RooWebScaffold(path = "admin/gtfs/routes", formBackingObject = Route.class)
public class RouteController {

    void populateEditForm(Model uiModel, Route route) {
        uiModel.addAttribute("route", route);
        uiModel.addAttribute("agencys", Agency.findAllAgencys());
//        uiModel.addAttribute("shapes", Shape.findAllShapes());
//        uiModel.addAttribute("trips", Trip.findAllTrips());
    }
}
