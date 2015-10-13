package com.github.netoht.mobile.model;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.geo.Box;
import org.springframework.data.geo.Point;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.netoht.mobile.util.validator.PointLocation;

public class BoundingBoxLocation {

    @PointLocation
    private String minLocation;

    @PointLocation
    private String maxLocation;

    public BoundingBoxLocation() {
    }

    public BoundingBoxLocation(String sw, String ne) {
        this.minLocation = sw;
        this.maxLocation = ne;
    }

    @JsonProperty("sw")
    public void setMinLocation(String minLocation) {
        this.minLocation = minLocation;
    }

    @JsonProperty("ne")
    public void setMaxLocation(String maxLocation) {
        this.maxLocation = maxLocation;
    }

    public Point getMinPoint() {
        return getPoint(minLocation);
    }

    public Point getMaxPoint() {
        return getPoint(maxLocation);
    }

    private Point getPoint(String location) {
        if (StringUtils.isBlank(location)) {
            return null;
        }
        double latitude = Double.parseDouble(StringUtils.substringBefore(location, ","));
        double longitude = Double.parseDouble(StringUtils.substringAfter(location, ","));
        return new Point(latitude, longitude);
    }

    public Box getBox() {
        return new Box(getMinPoint(), getMaxPoint());
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
