package org.busko.routemanager.model.transit.gtfs;

import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.busko.routemanager.model.Displayable;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Shape implements GtfsFormatted, Displayable {

    @Size(max = 20)
    private String shapeId;

    @NotNull
    @Size(max = 20)
    private String shapePtLat;

    @NotNull
    @Size(max = 20)
    private String shapePtLon;

    @NotNull
    private int shapePtSequence;

    @ManyToOne
    private Route route;

    @NotNull
    private Boolean explicitShapeId;

    public Shape() {
        this.shapeId = "S";
        this.explicitShapeId = false;
    }

    public String getLat() {
        return shapePtLat;
    }

    public void setLat(String lat) {
        this.shapePtLat = lat;
    }

    public String getLon() {
        return shapePtLon;
    }

    public void setLon(String lon) {
        this.shapePtLon = lon;
    }

    public String getFullShapeId() {
        if (explicitShapeId) return shapeId;
        if (route != null) return new StringBuilder().append("S").append(route.getUniqueEncoding()).toString();
        return shapeId;
    }

    @Override
    public String getGtfsFileName() {
        return "shapes.txt";
    }

    @Override
    public String getGtfsFileHeader() {
        return "shape_id,shape_pt_lat,shape_pt_lon,shape_pt_sequence";
    }

    @Override
    public String getGtfsData() {
        return new StringBuilder().append(getFullShapeId()).append(',').append(shapePtLat).append(',').append(shapePtLon).append(',').append(shapePtSequence).toString();
    }

    @Override
    public String getUniqueId() {
        return getId().toString();
    }

    @Override
    public boolean isInclude() {
        if (route != null) return route.isInclude();
        return false;
    }

    @Override
    public String getDisplayName() {
        return toString();
    }
}
