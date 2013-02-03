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
package org.busko.routemanager;

import org.junit.Assert;
import org.junit.Test;

public class GpsDistanceTest {

    private static double R = 6378.137;
    private static double D2R = Math.PI / 180;

    @Test
    public void testHaversine1() {
        double lat1 = -41.51362178609261;
        double lon1 = 173.95443;
        double lat2 = -41.51171019567971;
        double lon2 = 173.95452417964043;

        double d = calculateHaversineDistance(lat1, lon1, lat2, lon2);
        Assert.assertTrue(d < 100);
    }

    @Test
    public void testHaversineRoslynToAmity() {
        // Rolsyn Fresh Choice
        double lat1 = -45.8678073867509;
        double lon1 = 170.48896360516844;
        // Amity Health Centre
        double lat2 = -45.86617227781143;
        double lon2 = 170.49169446069322;

        double d = calculateHaversineDistance(lat1, lon1, lat2, lon2);
        Assert.assertTrue(d < 100);
    }

    @Test
    public void testHaversineRoslynToMaoriHill() {
        // Rolsyn Fresh Choice
        double lat1 = -45.8678073867509;
        double lon1 = 170.48896360516844;
        // Maori Hill
        double lat2 = -45.85808568653667;
        double lon2 = 170.50006455018578;

        double d = calculateHaversineDistance(lat1, lon1, lat2, lon2);
        Assert.assertTrue(d < 100);
    }

    @Test
    public void testHaversineBlenheimStop1() {
        // North Route Stop 1
        double lat1 = -41.51362178609261;
        double lon1 = 173.95443;
        // South Route Stop 1
        double lat2 = -41.51338354214751;
        double lon2 = 173.95442214459024;

        double d = calculateHaversineDistance(lat1, lon1, lat2, lon2);
        Assert.assertTrue(d < 100);
    }

    private double calculateHaversineDistance(double lat1, double lon1, double lat2, double lon2) {
        double dLat = (lat2 - lat1) * D2R;
        double dLon = (lon2 - lon1) * D2R;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1 * D2R) * Math.cos(lat2 * D2R);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }

    private double calculateHaversineDistanceWithout(double lat1, double lon1, double lat2, double lon2) {
        double dLat = (lat2 - lat1);// * D2R;
        double dLon = (lon2 - lon1);// * D2R;

        double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1) * Math.cos(lat2);
//                    Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(lat1 * D2R) * Math.cos(lat2 * D2R);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
        return R * c;
    }
}
