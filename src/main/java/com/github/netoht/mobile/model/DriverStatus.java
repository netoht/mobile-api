package com.github.netoht.mobile.model;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_EMPTY;

import javax.persistence.Id;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.data.annotation.Transient;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@Document(collection = "driver_status")
public class DriverStatus {

    @Id
    private String id;

    @Indexed(unique = true)
    private Long driverId;
    private boolean driverAvailable;

    @Transient
    private Double latitude;

    @Transient
    private Double longitude;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point position;

    public DriverStatus() {
    }

    public DriverStatus(Long driverId) {
        super();
        this.driverId = driverId;
    }

    @JsonIgnore
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public boolean isDriverAvailable() {
        return driverAvailable;
    }

    public void setDriverAvailable(boolean driverAvailable) {
        this.driverAvailable = driverAvailable;
    }

    @JsonProperty("latitude")
    @JsonInclude(NON_EMPTY)
    public Double getLatitude() {
        if (position == null) {
            return latitude;
        }
        return position.getX();
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    @JsonProperty("longitude")
    @JsonInclude(NON_EMPTY)
    public Double getLongitude() {
        if (position == null) {
            return longitude;
        }
        return position.getY();
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    @JsonIgnore
    public Point getPosition() {
        return position;
    }

    public void setPosition() {
        this.position = new Point(latitude, longitude);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
