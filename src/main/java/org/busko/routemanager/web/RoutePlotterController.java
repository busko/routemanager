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

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;

@RequestMapping("/routeplotter")
@Controller
public class RoutePlotterController {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<byte[]> handheldApk(Model uiModel, HttpServletRequest httpServletRequest) throws Exception {
        InputStream input = getClass().getClassLoader().getResourceAsStream("BuskoPlotter.apk");
        int fileSize = input.available();
        byte[] bytes = new byte[fileSize];
        input.read(bytes);
        input.close();

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Disposition", "attachment;filename=BuskoPlotter.apk");
        responseHeaders.set("Content-Length", Integer.toString(fileSize));
        responseHeaders.set("Content-Type", "application/vnd.android.package-archive");
        return new ResponseEntity<byte[]>(bytes, responseHeaders, HttpStatus.OK);
    }
}
