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
package org.busko.routemanager.web;

import org.busko.routemanager.model.transit.gtfs.Agency;
import org.busko.routemanager.model.transit.gtfs.Calendar;
import org.busko.routemanager.model.transit.gtfs.Route;
import org.busko.routemanager.model.transit.gtfs.Stop;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashSet;

@RequestMapping("/testdata/**")
@Controller
public class TestDataController {

    @RequestMapping
    public
    @ResponseBody
    String init() throws Exception {
        initGtfsData();
        return "Done";
    }

    private void initGtfsData() {
        initGtfsAgencyData();
        initGtfsCalendarData();
//        initGtfsRouteData();
//        initGtfsStopData();
    }

    private void initGtfsAgencyData() {
        HashSet<String> agencyIds = new HashSet<String>();
        for (Agency agency : Agency.findAllAgencys()) {
            agencyIds.add(agency.getAgencyId());
        }

        if (!agencyIds.contains("GoBus")) {
            Agency agency = new Agency("GoBus", "Dunedin City GoBus", "http://www.orc.govt.nz/Information-and-Services/Buses/Bus-Information/",
                                       "Pacific/Auckland", "+643 474 0287", "en", "10");
            agency.setLive(true);
            agency.persist();
        }
        if (!agencyIds.contains("Connectabus")) {
            Agency agency = new Agency("Connectabus", "Connectabus", "http://connectabus.com/",
                                       "Pacific/Auckland", "+643 441 4471", "en", "11");
            agency.setLive(true);
            agency.persist();
        }
        if (!agencyIds.contains("InvercargillCity")) {
            Agency agency = new Agency("InvercargillCity", "Invercargill City Council", "http://www.icc.govt.nz/ServicesA-Z/Buses.aspx",
                                       "Pacific/Auckland", "+643 218 7108", "en", "12");
            agency.setLive(true);
            agency.persist();
        }
        if (!agencyIds.contains("TheBlenheimBus")) {
            Agency agency = new Agency("TheBlenheimBus", "The Blenheim Bus", "http://www.marlborough.govt.nz/Services/Parking-Roads-and-Transport/Blenheim-Bus-Service.aspx",
                                       "Pacific/Auckland", "+643 520 7400", "en", "20");
            agency.setLive(true);
            agency.persist();
        }
        if (!agencyIds.contains("theBayBus")) {
            Agency agency = new Agency("theBayBus", "BayBus", "http://www.baybus.co.nz/",
                                       "Pacific/Auckland", "+643 864 4669", "en", "21");
            agency.persist();
        }
        if (!agencyIds.contains("NelsonBus")) {
            Agency agency = new Agency("NelsonBus", "Nelson Bus", "http://www.nelsoncitycouncil.co.nz/nbus/",
                                       "Pacific/Auckland", "+643 546 0200", "en", "22");
            agency.persist();
        }
        if (!agencyIds.contains("Tranzit")) {
            Agency agency = new Agency("Tranzit", "Tranzit", "http://www.newplymouthnz.com/OurDistrict/Transport/NewPlymouthBusServices.htm",
                                       "Pacific/Auckland", "+646 759 6060", "en", "23");
            agency.persist();
        }
        if (!agencyIds.contains("Citylink")) {
            Agency agency = new Agency("CityLink", "CityLink Whangarei", "http://www.nrc.govt.nz/transport/getting-around/whangarei-bus-service/",
                                       "Pacific/Auckland", "+649 470 1200", "en", "24");
            agency.persist();
        }
        
//        if (!agencyIds.contains("Intercity")) {
//            Agency agency = new Agency();
//            agency.setAgencyId("Intercity");
//            agency.setAgencyName("Intercity Coachlines");
//            agency.setAgencyUrl("http://www.intercity.co.nz/cheap-north-island-buses/bus-auckland-to-tauranga/");
//            agency.setAgencyTimezone("Pacific/Auckland");
//            agency.setAgencyPhone("+649 583 5780");
//            agency.setAgencyLang("en");
//            agency.setUniqueEncoding("24");
//            agency.persist();
//        }
        if (!agencyIds.contains("NakedBus")) {
            Agency agency = new Agency("NakedBus", "Naked Bus", "http://nakedbus.com/nz/bus/",
                                       "Pacific/Auckland", "0900 62533", "en", "99");
            agency.persist();
        }
    }

    private void initGtfsCalendarData() {
        if (Calendar.countCalendars() > 0) return;

        Calendar mon_fri = new Calendar(Calendar.MON_FRI, true, true, true, true, true, false, false);
        mon_fri.persist();
        Calendar mon_sat = new Calendar(Calendar.MON_SAT, true, true, true, true, true, true, false);
        mon_sat.persist();
        Calendar mon_sun = new Calendar(Calendar.MON_SUN, true, true, true, true, true, true, true);
        mon_sun.persist();
        Calendar sat_sun = new Calendar(Calendar.SAT_SUN, false, false, false, false, false, true, true);
        sat_sun.persist();
        Calendar monday = new Calendar(Calendar.MON, true, false, false, false, false, false, false);
        monday.persist();
        Calendar tuesday = new Calendar(Calendar.TUE, false, true, false, false, false, false, false);
        tuesday.persist();
        Calendar wednesday = new Calendar(Calendar.WED, false, false, true, false, false, false, false);
        wednesday.persist();
        Calendar thursday = new Calendar(Calendar.THR, false, false, false, true, false, false, false);
        thursday.persist();
        Calendar friday = new Calendar(Calendar.FRI, false, false, false, false, true, false, false);
        friday.persist();
        Calendar saturday = new Calendar(Calendar.SAT, false, false, false, false, false, true, false);
        saturday.persist();
        Calendar sunday = new Calendar(Calendar.SUN, false, false, false, false, false, false, true);
        sunday.persist();
    }

    private void initGtfsRouteData() {
        if (Route.countRoutes() > 0) return;

        Agency goBus = Agency.findAgencysByAgencyIdEquals("GoBus").getSingleResult();
        if (goBus == null) return;

        Route goBus28 = new Route(goBus, "DN28", "28", "DN28", "St Clair - Octagon", "DN28_9", "FF0000");
        goBus28.persist();
        Route goBus9 = new Route(goBus, "DN9", "09", "DN9", "Octagon - Normanby", "DN28_9", "FF0000");
        goBus9.persist();
//        Route goBus28A = new Route(goBus, "DN28A", "281", "DN28A", "St Clair - Octagon", "DN28A_9", "FF0000");
//        goBus28A.persist();
//        Route goBus9A = new Route(goBus, "DN9A", "091", "DN9A", "Octagon - Normanby", "DN28A_9", "FF0000");
//        goBus9A.persist();
        Route goBus8 = new Route(goBus, "DN8", "08", "DN8", "Normanby - Octagon", "DN8_29", "FF0000");
        goBus8.persist();
        Route goBus29 = new Route(goBus, "DN29", "29", "DN29", "Octagon - St Clair", "DN8_29", "FF0000");
        goBus29.persist();
    }

    private void initGtfsStopData() {
        if (Stop.countStops() > 0) return;

        Agency goBus = Agency.findAgencysByAgencyIdEquals("GoBus").getSingleResult();
        if (goBus == null) return;

        Stop goBusStand1 = new Stop(goBus.getUniqueEncoding() + "0001", "Octagon Stand 1", null, "-45.87480053758057", "170.5032499075278");
        goBusStand1.persist();
        Stop goBusStand2 = new Stop(goBus.getUniqueEncoding() + "0002", "Octagon Stand 2", null, "-45.8749402228972", "170.50316855255505");
        goBusStand2.persist();
        Stop goBusStand3 = new Stop(goBus.getUniqueEncoding() + "0003", "Octagon Stand 3", null, "-45.87426645681816", "170.50302215227168");
        goBusStand3.persist();
        Stop goBusStand4 = new Stop(goBus.getUniqueEncoding() + "0004", "Cumberland St. Stand 4", null, "-45.87392598380922", "170.50703807272362");
        goBusStand4.persist();
        Stop goBusStand5 = new Stop(goBus.getUniqueEncoding() + "0005", "Cumberland St. Stand 5", null, "-45.87117470208344", "170.50859003242851");
        goBusStand5.persist();
        Stop goBusStand7 = new Stop(goBus.getUniqueEncoding() + "0007", "Octagon Stand 7", null, "-45.8735052714479", "170.50398197776394");
        goBusStand7.persist();
        Stop goBusStand8 = new Stop(goBus.getUniqueEncoding() + "0007", "Octagon Stand 8", null, "-45.87359783715763", "170.5039306691184");
        goBusStand8.persist();
    }
}
