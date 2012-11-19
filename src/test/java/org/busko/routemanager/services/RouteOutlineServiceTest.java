package org.busko.routemanager.services;

import org.busko.routemanager.HSQLTestBase;
import org.busko.routemanager.model.transit.Country;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class RouteOutlineServiceTest extends HSQLTestBase {

    private RouteOutlineService routeOutlineService;

    @Autowired
    public void setElectionTimetableService(RouteOutlineService routeOutlineService) {
        this.routeOutlineService = routeOutlineService;
    }

    @Test
    public void testGenerateGtfsBundle() throws Exception {
//        routeOutlineService.generateGtfsBundle();
    }
}
