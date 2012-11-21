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

import org.busko.routemanager.model.transit.community.RouteSubmission;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;

@RequestMapping("/admin/community/routesubmissions")
@Controller
@RooWebScaffold(path = "admin/community/routesubmissions", formBackingObject = RouteSubmission.class)
public class RouteSubmissionController {

    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid RouteSubmission routeSubmission, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, routeSubmission);
            return "admin/community/routesubmissions/create";
        }
        uiModel.asMap().clear();

        routeSubmission.setSubmittedDateTime(new Date());
        routeSubmission.uploadFileData();
        routeSubmission.persist();
        return "redirect:/admin/community/routesubmissions/" + encodeUrlPathSegment(routeSubmission.getId().toString(), httpServletRequest);
    }

    @RequestMapping(params = "view", value = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<byte[]> view(@PathVariable("id") Long id, Model uiModel) throws Exception {
        RouteSubmission routeSubmission = RouteSubmission.findRouteSubmission(id);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment;filename=" + routeSubmission.getUsername() + ".xml");
        responseHeaders.set("Content-Length", Integer.toString(routeSubmission.getFileContent().length));
//        responseHeaders.set("Content-Type", "text/plain; charset=UTF-8");
        responseHeaders.set("Content-Type", "text/xml");
        return new ResponseEntity<byte[]>(routeSubmission.getFileContent(), responseHeaders, HttpStatus.OK);
    }
}
