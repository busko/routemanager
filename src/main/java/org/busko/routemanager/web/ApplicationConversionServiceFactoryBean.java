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
import org.busko.routemanager.model.transit.gtfs.Route;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.support.FormattingConversionServiceFactoryBean;
import org.springframework.roo.addon.web.mvc.controller.converter.RooConversionService;

/**
 * A central place to register application Converters and Formatters.
 */
@RooConversionService
public class ApplicationConversionServiceFactoryBean extends FormattingConversionServiceFactoryBean {

    public Converter<Agency, String> getAgencyToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.busko.routemanager.model.transit.gtfs.Agency, java.lang.String>() {
            public String convert(Agency agency) {
                return agency.getAgencyName();
            }
        };
    }

    public Converter<Route, String> getRouteToStringConverter() {
        return new org.springframework.core.convert.converter.Converter<org.busko.routemanager.model.transit.gtfs.Route, java.lang.String>() {
            public String convert(Route route) {
                return route.getRouteShortName();
            }
        };
    }
}
