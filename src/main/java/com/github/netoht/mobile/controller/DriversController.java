package com.github.netoht.mobile.controller;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

import java.awt.geom.IllegalPathStateException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.netoht.mobile.model.Driver;
import com.github.netoht.mobile.service.DriverService;
import com.github.netoht.mobile.service.DriverStatusService;

@Controller
@RequestMapping("/drivers")
public class DriversController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverStatusService statusService;

    @RequestMapping(method = GET)
    public String listDrivers(Model model, HttpServletResponse response) {
        Iterable<Driver> drivers = driverService.findAll();
        model.addAttribute("drivers", drivers);
        return "listDrivers";
    }

    @RequestMapping("/location/{driverId}")
    public String location(@PathVariable("driverId") Long driverId, Model model, HttpServletResponse response) {
        try {
            Point position = statusService.getStatusPosition(driverId);
            if (position == null) {
                model.addAttribute("driverId", driverId);
                throw new IllegalPathStateException();
            }
            String location = String.format("https://www.google.com/maps?z=18&q=%s,%s", position.getX(), position.getY());
            return "redirect:" + location;

        } catch (Exception e) {
            return "driverNotAvailable";
        }
    }
}
