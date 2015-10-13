package com.github.netoht.mobile.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.github.netoht.mobile.model.Driver;

@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {

    Long countByCarPlate(String carPlate);
}