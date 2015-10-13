package com.github.netoht.mobile.controller.api;

import static com.codahale.metrics.MetricRegistry.name;
import static org.springframework.http.HttpStatus.NO_CONTENT;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.codahale.metrics.MetricRegistry;
import com.github.netoht.mobile.model.BoundingBoxLocation;
import com.github.netoht.mobile.model.Driver;
import com.github.netoht.mobile.model.DriverStatus;
import com.github.netoht.mobile.service.DriverService;
import com.github.netoht.mobile.service.DriverStatusService;

@RestController
@RequestMapping("/api/v1/drivers")
public class DriverControllerV1 {

    private static final String JSON = "application/json; charset=utf-8";

    @Autowired
    private MetricRegistry metrics;

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverStatusService statusService;

    @PreAuthorize("#oauth2.hasScope('write')")
    @ResponseStatus(NO_CONTENT)
    @RequestMapping(method = POST, consumes = JSON)
    public void createDriver(@RequestBody(required = true) Driver driver) {
        metrics.histogram(name(DriverControllerV1.class, "createDriver")).update(1);
        driverService.createDriver(driver);
    }

    @PreAuthorize("#oauth2.hasScope('write')")
    @ResponseStatus(NO_CONTENT)
    @RequestMapping(value = "/{driverId}/status", method = POST, produces = JSON, consumes = JSON)
    public void updateDriverStatus(@PathVariable("driverId") Long driverId, @RequestBody(required = true) DriverStatus status) {
        metrics.histogram(name(DriverControllerV1.class, "updateDriverStatus")).update(1);
        statusService.updateStatus(driverId, status);
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @ResponseStatus(OK)
    @RequestMapping(value = "/{driverId}/status", method = GET, produces = JSON)
    public DriverStatus getDriverStatus(@PathVariable("driverId") Long driverId) {
        metrics.histogram(name(DriverControllerV1.class, "getDriverStatus")).update(1);
        return statusService.getStatus(driverId);
    }

    @PreAuthorize("#oauth2.hasScope('read')")
    @ResponseStatus(OK)
    @RequestMapping(value = "/inArea", method = GET, produces = JSON)
    public List<DriverStatus> getDriversInArea(@RequestParam(value = "sw", required = true) String sw, @RequestParam(value = "ne", required = true) String ne) {
        metrics.histogram(name(DriverControllerV1.class, "getDriverInArea")).update(1);
        return statusService.getDriversInArea(new BoundingBoxLocation(sw, ne));
    }
}
