package org.busko.routemanager.model.transit.gtfs;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord(finders = { "findStopsByRoute" })
public class Stop implements GtfsFormatted, Displayable {

    @NotNull
    @Size(max = 20)
    private String stopId;

    @NotNull
    @Size(max = 100)
    private String stopName;

    @Size(max = 256)
    private String stopDesc;

    @NotNull
    @Size(max = 20)
    private String stopLat;

    @NotNull
    @Size(max = 20)
    private String stopLon;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stop")
    private Set<StopTime> stopTimes = new HashSet<StopTime>();

    @ManyToOne
    private Agency agency;

    @ManyToOne
    private Route route;

    @NotNull
    private Boolean explicitStopId;

    public Stop() {
    }

    public Stop(String stopId, String stopName, String stopDesc, String stopLat, String stopLon) {
        this.stopId = stopId;
        this.stopName = stopName;
        this.stopDesc = stopDesc;
        this.stopLat = stopLat;
        this.stopLon = stopLon;
        this.explicitStopId = false;
    }

    public void addStopTime(StopTime stopTime) {
        stopTime.setStop(this);
        stopTimes.add(stopTime);
    }

    public String getFullStopId() {
        if (explicitStopId) return stopId;
        String fullStopId = stopId.length() == 1 ? ("0" + stopId) : stopId;
        if (agency != null) return new StringBuilder().append(agency.getUniqueEncoding()).append("00").append(fullStopId).toString();
        if (route != null) return new StringBuilder().append(route.getAgency().getUniqueEncoding()).append(route.getUniqueEncoding()).append(fullStopId).toString();
        return stopId;
    }

    @Override
    public String getGtfsFileName() {
        return "stops.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "stop_id,stop_name,stop_desc,stop_lat,stop_lon,zone_id,stop_url";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getFullStopId()).append(',').append(stopName).append(',').append(stopDesc).append(',').append(stopLat).append(',').append(stopLon).append(',').append("").append(',').toString();
    }

    @Override
    public String getDisplayName() {
        return toString();
    }
}
