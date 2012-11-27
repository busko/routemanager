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
package org.busko.routemanager.services;

import org.apache.commons.io.FileUtils;
import org.busko.routemanager.model.transit.gtfs.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.PrintWriter;
import java.util.List;

/**
 * May well need the idea of a 'version' of the GTFS feed so keep all the layout together.
 */
@Service
public class RouteOutlineServiceImpl implements RouteOutlineService {

    /**
     * Generate the bundle for all the currently configured agencies.
     */
    @Override
    @Transactional
    public void generateGtfsBundle() throws Exception {
        File directory = new File("\\tmp");
        if (!directory.exists()) {
            directory.mkdir();
        }

        List agencys = Agency.findAllAgencys();
        if (agencys.size() == 0) return;
        createGtfsTxt(directory, agencys);

        List calendars = Calendar.findAllCalendars();
        createGtfsTxt(directory, calendars);

        createFile(directory, "calendar_dates.txt", "service_id,date,exception_type");
        createFile(directory, "fare_attributes.txt", "fare_id,price,currency_type,payment_method,transfers,transfer_duration");
        createFile(directory, "fare_rules.txt", "fare_id,route_id,origin_id,destination_id,contains_id");
        createFile(directory, "frequencies.txt", "trip_id,start_time,end_time,headway_secs");

        List routes = Route.findAllRoutes();
        createGtfsTxt(directory, routes);

        List shapeCollections = ShapeCollection.findAllShapeCollections();
        createGtfsTxt(directory, shapeCollections);

        List stops = Stop.findAllStops();
        createGtfsTxt(directory, stops);

        List trips = Trip.findAllTrips();
        createGtfsTxt(directory, trips);

        List stopTimes = StopTime.findAllStopTimes();
        createGtfsTxt(directory, stopTimes);
    }

    private void createGtfsTxt(File directory, List gtfsEntities) throws Exception {
        if (gtfsEntities.isEmpty()) return;

        GtfsFormatted gtfsFormatted = (GtfsFormatted)gtfsEntities.get(0);
        File file = createFile(directory, gtfsFormatted.getGtfsFileName(), null);

        PrintWriter writer = null;
        try {
            writer = new PrintWriter(FileUtils.openOutputStream(file));
            writer.print(gtfsFormatted.getGtfsFileHeader());
            writer.print("\n");

            String testData = NewZealandTestData.getTestData(gtfsFormatted.getGtfsFileName());
            if (testData != null) {
                writer.print(testData);
                writer.print("\n");
            }

            for (Object object : gtfsEntities) {
                writer.print(((GtfsFormatted)object).getGtfsData());
                writer.print("\n");
            }
        } finally {
            if (writer != null) { writer.close(); }
        }
    }

    private File createFile(File directory, String fileName, String fileHeader) throws Exception {
        File file = new File(directory, fileName);
        if (file.exists()) {
            file.delete();
        }

        if (fileHeader != null) {
            FileUtils.writeStringToFile(file, fileHeader);
        }

        return file;
    }
}
