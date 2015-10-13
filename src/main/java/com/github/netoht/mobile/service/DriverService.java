package com.github.netoht.mobile.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.netoht.mobile.exception.ConflictException;
import com.github.netoht.mobile.exception.IllegalFieldsException;
import com.github.netoht.mobile.model.Driver;
import com.github.netoht.mobile.model.DriverStatus;
import com.github.netoht.mobile.repository.DriverRepository;
import com.github.netoht.mobile.repository.DriverStatusRepository;
import com.github.netoht.mobile.util.log.LogEvent;

@Service
public class DriverService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Validator validator;

    @Autowired
    private DriverRepository driverRepository;

    @Autowired
    private DriverStatusRepository statusRepository;

    @Transactional
    public void createDriver(Driver driver) {
        try {
            Set<ConstraintViolation<Driver>> violations = validator.validate(driver);
            if (!violations.isEmpty()) {
                Object[] messages = violations.stream().map(v -> v.getMessage()).toArray();
                throw new IllegalFieldsException(Arrays.toString(messages));
            }
            if (carPlateExists(driver)) {
                throw new ConflictException("Placa do veículo já cadastrada no sistema por outro taxista");
            }
            final Driver newDriver = driverRepository.save(driver);
            saveInitialStatus(newDriver);

        } catch (Exception e) {
            log.error(LogEvent.create("erro ao criar novo cadastro de taxista").put("driver", driver).put(e).toString());
            throw e;
        }
    }

    private boolean carPlateExists(Driver driver) {
        return driverRepository.countByCarPlate(driver.getCarPlate()) > 0;
    }

    private void saveInitialStatus(Driver newDriver) {
        statusRepository.insert(new DriverStatus(newDriver.getId()));
    }

    public Iterable<Driver> findAll() {
        try {
            return driverRepository.findAll();

        } catch (Exception e) {
            log.error(LogEvent.create("erro ao recuperar todos os taxistas").put(e).toString());
            return Collections.emptyList();
        }
    }
}
