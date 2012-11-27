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

import org.busko.routemanager.model.transit.community.RouteOutline;
import org.busko.routemanager.model.transit.gtfs.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

@RequestMapping("/admin/community/routeoutlines")
@Controller
@RooWebScaffold(path = "admin/community/routeoutlines", formBackingObject = RouteOutline.class)
public class RouteOutlineController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid RouteOutline routeOutline, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, routeOutline);
            return "admin/community/routeoutlines/create";
        }
        uiModel.asMap().clear();
        routeOutline.uploadFileData();
        routeOutline.persist();
        return "redirect:/admin/community/routeoutlines/" + encodeUrlPathSegment(routeOutline.getId().toString(), httpServletRequest);
    }

    /**
     * Needs to process all the data submitted to create, at present, the associated Route object.
     */
    @Transactional
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String update(@Valid RouteOutline routeOutline, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, routeOutline);
            return "admin/community/routeoutlines/update";
        }

        // Sort the request data
        HashMap<String, String> latMap = new HashMap<String, String>();
        HashMap<String, String> lonMap = new HashMap<String, String>();
        HashMap<String, String> descMap = new HashMap<String, String>();
        HashMap<String, HashMap<String, String>> tripStoptimeMap = new HashMap<String, HashMap<String, String>>();
        HashMap<String, String> frequencyMap = new HashMap<String, String>();
        for (Object o : httpServletRequest.getParameterMap().keySet()) {
            String key = o.toString();
            if (key.startsWith("LAT")) {
                latMap.put(key.substring(3), httpServletRequest.getParameter(key));
            }
            else if (key.startsWith("LON")) {
                lonMap.put(key.substring(3), httpServletRequest.getParameter(key));
            }
            else if (key.startsWith("DESC")) {
                descMap.put(key.substring(4), httpServletRequest.getParameter(key));
            }
            else if (key.endsWith("FREQUENCY")) {
                frequencyMap.put(key.substring(4, key.indexOf("-")), httpServletRequest.getParameter(key));
            }
            else if (key.startsWith("TRIP")) {
                String index = key.substring(4, key.indexOf("-"));
                HashMap<String, String> stoptimeMap = tripStoptimeMap.get(index);
                if (stoptimeMap == null) {
                    stoptimeMap = new HashMap<String, String>();
                    tripStoptimeMap.put(index, stoptimeMap);
                }
                stoptimeMap.put(key.substring(key.indexOf("-") + 1), httpServletRequest.getParameter(key));
            }
        }

        RouteOutline theRouteOutline = RouteOutline.findRouteOutline(routeOutline.getId());
        theRouteOutline.setUsername(routeOutline.getUsername());
        theRouteOutline.setRouteName(routeOutline.getRouteName());
        theRouteOutline.setRouteDescription(routeOutline.getRouteDescription());
        theRouteOutline.setSubmittedDateTime(routeOutline.getSubmittedDateTime());

        Route route = theRouteOutline.createAndAssociateNewRoute();
        route.persist();
        theRouteOutline.merge();

        // Stops should be prefixed with 'STOPx_' where the x is the stop number. If this format is used then set the stopId accordingly.
        HashMap<String, Stop> stopMap = new HashMap<String, Stop>();
        for (int i = 0; i < latMap.size(); i++) {
            String index = Integer.toString(i);
            String stopName = descMap.get(index);
            String stopId = null;
            if (stopName.toLowerCase().startsWith("stop")) {
                int pos = stopName.indexOf("_");
                if (pos > 4) {
                    stopId = stopName.substring(4, pos);
                    stopName = stopName.substring(pos + 1);
                }
            }
            Stop stop = new Stop(stopId != null ? stopId : "XX", stopName, null, latMap.get(index), lonMap.get(index));

            // todo Here we may need to set Agency if stop is being reused
            route.addStop(stop);

            stopMap.put(index, stop);
            stop.persist();
        }

        // Create the shape from the GPS data
        // TODO Do we want to do this from the GUI to allow points to be added/moved on the interface?
        GpxToShapeParser gpxToShapeParser = new GpxToShapeParser();
        gpxToShapeParser.parse(theRouteOutline);
        gpxToShapeParser.getShapeCollection().persist();

        for (String tripNumber : tripStoptimeMap.keySet()) {
            Trip trip = new Trip(frequencyMap.get(tripNumber), null, routeOutline.getRouteName(), 0);
            trip.setCalendar(Calendar.findCalendarsByServiceIdEquals(trip.getServiceId()).getSingleResult());
            trip.setShapeCollection(gpxToShapeParser.getShapeCollection());
            route.addTrip(trip);
            trip.persist();

            HashMap<String, String> stoptimeMap = tripStoptimeMap.get(tripNumber);
            for (int i = 0; i < latMap.size(); i++) {
                String index = Integer.toString(i);
                StopTime stopTime = new StopTime(stoptimeMap.get(index), stoptimeMap.get(index), i);
                trip.addStopTime(stopTime);

                Stop stop = stopMap.get(index);
                stop.addStopTime(stopTime);
                stopTime.persist();
            }
        }

        uiModel.asMap().clear();
        return "redirect:/admin/community/routeoutlines/" + encodeUrlPathSegment(routeOutline.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "gpx", value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> gpx(@PathVariable("id") Long id, Model uiModel) {
        RouteOutline routeOutline = RouteOutline.findRouteOutline(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment;filename=" + routeOutline.getRouteName() + ".xml");
        responseHeaders.set("Content-Length", Integer.toString(routeOutline.getFileContent().length));
        responseHeaders.set("Content-Type", "text/xml");
        return new ResponseEntity<byte[]>(routeOutline.getFileContent(), responseHeaders, HttpStatus.OK);
    }

    void populateEditForm(Model uiModel, RouteOutline routeOutline) {
        uiModel.addAttribute("routeOutline", routeOutline);
        addDateTimeFormatPatterns(uiModel);
        List<Agency> agencies = Agency.findAllAgencys();
        agencies.add(0, new Agency());
        uiModel.addAttribute("agencys", agencies);
//        uiModel.addAttribute("routes", Route.findAllRoutes());
    }
}
