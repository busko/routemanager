// WARNING: DO NOT EDIT THIS FILE. THIS FILE IS MANAGED BY SPRING ROO.
// You may push code into the target .java compilation unit if you wish to edit any member(s).

package org.busko.routemanager.web.admin.gtfs;

import java.io.UnsupportedEncodingException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.busko.routemanager.model.transit.gtfs.Stop;
import org.busko.routemanager.model.transit.gtfs.StopTime;
import org.busko.routemanager.model.transit.gtfs.Trip;
import org.busko.routemanager.web.admin.gtfs.StopTimeController;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.util.UriUtils;
import org.springframework.web.util.WebUtils;

privileged aspect StopTimeController_Roo_Controller {
    
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String StopTimeController.create(@Valid StopTime stopTime, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, stopTime);
            return "admin/gtfs/stoptimes/create";
        }
        uiModel.asMap().clear();
        stopTime.persist();
        return "redirect:/admin/gtfs/stoptimes/" + encodeUrlPathSegment(stopTime.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(params = "form", produces = "text/html")
    public String StopTimeController.createForm(Model uiModel) {
        populateEditForm(uiModel, new StopTime());
        return "admin/gtfs/stoptimes/create";
    }
    
    @RequestMapping(value = "/{id}", produces = "text/html")
    public String StopTimeController.show(@PathVariable("id") Long id, Model uiModel) {
        uiModel.addAttribute("stoptime", StopTime.findStopTime(id));
        uiModel.addAttribute("itemId", id);
        return "admin/gtfs/stoptimes/show";
    }
    
    @RequestMapping(produces = "text/html")
    public String StopTimeController.list(@RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        if (page != null || size != null) {
            int sizeNo = size == null ? 10 : size.intValue();
            final int firstResult = page == null ? 0 : (page.intValue() - 1) * sizeNo;
            uiModel.addAttribute("stoptimes", StopTime.findStopTimeEntries(firstResult, sizeNo));
            float nrOfPages = (float) StopTime.countStopTimes() / sizeNo;
            uiModel.addAttribute("maxPages", (int) ((nrOfPages > (int) nrOfPages || nrOfPages == 0.0) ? nrOfPages + 1 : nrOfPages));
        } else {
            uiModel.addAttribute("stoptimes", StopTime.findAllStopTimes());
        }
        return "admin/gtfs/stoptimes/list";
    }
    
    @RequestMapping(method = RequestMethod.PUT, produces = "text/html")
    public String StopTimeController.update(@Valid StopTime stopTime, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, stopTime);
            return "admin/gtfs/stoptimes/update";
        }
        uiModel.asMap().clear();
        stopTime.merge();
        return "redirect:/admin/gtfs/stoptimes/" + encodeUrlPathSegment(stopTime.getId().toString(), httpServletRequest);
    }
    
    @RequestMapping(value = "/{id}", params = "form", produces = "text/html")
    public String StopTimeController.updateForm(@PathVariable("id") Long id, Model uiModel) {
        populateEditForm(uiModel, StopTime.findStopTime(id));
        return "admin/gtfs/stoptimes/update";
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE, produces = "text/html")
    public String StopTimeController.delete(@PathVariable("id") Long id, @RequestParam(value = "page", required = false) Integer page, @RequestParam(value = "size", required = false) Integer size, Model uiModel) {
        StopTime stopTime = StopTime.findStopTime(id);
        stopTime.remove();
        uiModel.asMap().clear();
        uiModel.addAttribute("page", (page == null) ? "1" : page.toString());
        uiModel.addAttribute("size", (size == null) ? "10" : size.toString());
        return "redirect:/admin/gtfs/stoptimes";
    }
    
    void StopTimeController.populateEditForm(Model uiModel, StopTime stopTime) {
        uiModel.addAttribute("stopTime", stopTime);
        uiModel.addAttribute("stops", Stop.findAllStops());
        uiModel.addAttribute("trips", Trip.findAllTrips());
    }
    
    String StopTimeController.encodeUrlPathSegment(String pathSegment, HttpServletRequest httpServletRequest) {
        String enc = httpServletRequest.getCharacterEncoding();
        if (enc == null) {
            enc = WebUtils.DEFAULT_CHARACTER_ENCODING;
        }
        try {
            pathSegment = UriUtils.encodePathSegment(pathSegment, enc);
        } catch (UnsupportedEncodingException uee) {}
        return pathSegment;
    }
    
}
