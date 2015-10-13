package com.github.netoht.mobile.service;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

import com.github.netoht.mobile.exception.IllegalFieldsException;
import com.github.netoht.mobile.exception.NotFoundException;
import com.github.netoht.mobile.model.BoundingBoxLocation;
import com.github.netoht.mobile.model.DriverStatus;
import com.github.netoht.mobile.repository.DriverStatusRepository;
import com.github.netoht.mobile.util.log.LogEvent;

@Service
public class DriverStatusService {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private Validator validator;

    @Autowired
    private DriverStatusRepository statusRepository;

    public void updateStatus(Long driverId, DriverStatus status) {
        try {
            if (status == null || driverId == null || status.getDriverId() != driverId) {
                throw new IllegalFieldsException("id informado na url é diferente do id informado no corpo da mensagem");
            }
            if (status.getLatitude() == null || status.getLongitude() == null) {
                throw new IllegalFieldsException("latitude e longitude devem ser informadas corretamente por exemplo -12.000,10.000");
            }
            status.setPosition();
            DriverStatus statusLoaded = statusRepository.findOneByDriverId(driverId);
            if (statusLoaded == null) {
                throw new NotFoundException("Taxista não cadastrado no sistema com o id informado " + driverId);
            }
            status.setId(statusLoaded.getId());
            statusRepository.save(status);

        } catch (Exception e) {
            log.error(LogEvent.create("erro ao atualizar status do taxista").put("driverId", driverId).put("status", status).put(e).toString());
            throw e;
        }
    }

    public DriverStatus getStatus(Long driverId) {
        try {
            if (driverId == null || driverId == 0) {
                throw new IllegalFieldsException("id informado na url é inválido");
            }
            DriverStatus driver = statusRepository.findOneByDriverId(driverId);
            if (driver == null) {
                throw new NotFoundException("Taxista não cadastrado no sistema com o id " + driverId);
            }
            return driver;

        } catch (Exception e) {
            log.error(LogEvent.create("erro ao recuperar status do taxista").put("driverId", driverId).put(e).toString());
            throw e;
        }
    }

    public List<DriverStatus> getDriversInArea(BoundingBoxLocation location) {
        try {
            Set<ConstraintViolation<BoundingBoxLocation>> violations = validator.validate(location);
            if (!violations.isEmpty()) {
                throw new IllegalFieldsException(violations.iterator().next().getMessage());
            }
            return statusRepository.findByPositionWithinAndDriverAvailable(location.getBox(), true);

        } catch (Exception e) {
            log.error(LogEvent.create("erro ao recuperar taxistas na area solicitada").put("location", location).put(e).toString());
            throw e;
        }
    }

    public Point getStatusPosition(Long driverId) {
        try {
            DriverStatus status = getStatus(driverId);
            if (status == null || status.getPosition() == null) {
                return null;
            }
            return status.getPosition();

        } catch (Exception e) {
            log.error(LogEvent.create("erro ao recuperar localização do taxista").put("driverId", driverId).put(e).toString());
            return null;
        }
    }
}
