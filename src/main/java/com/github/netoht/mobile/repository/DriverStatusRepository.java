package com.github.netoht.mobile.repository;

import java.util.List;

import org.springframework.data.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Component;

import com.github.netoht.mobile.model.DriverStatus;

@Component
public interface DriverStatusRepository extends MongoRepository<DriverStatus, String> {

    public DriverStatus findOneByDriverId(Long driverId);

    public List<DriverStatus> findByPositionWithinAndDriverAvailable(Box box, boolean driverAvailable);
}