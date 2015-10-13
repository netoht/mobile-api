package com.github.netoht.mobile.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.netoht.mobile.util.validator.CarPlate;

@Table(name = "driver")
@Entity
public class Driver implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "serial")
    private Long id;

    @NotNull(message = "O nome do taxista deve ser informado pelo campo 'name'")
    @Size(min = 8, max = 100, message = "O nome do taxista deve conter entre 8 e 100 caracteres")
    @Column(nullable = false, length = 100)
    private String name;

    @CarPlate
    @Column(name = "car_plate", nullable = false, unique = true, length = 8)
    private String carPlate;

    public Driver() {
    }

    @JsonProperty("driverId")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return StringUtils.upperCase(name);
    }

    public void setName(String name) {
        this.name = StringUtils.upperCase(name);
    }

    public String getCarPlate() {
        return StringUtils.upperCase(carPlate);
    }

    public void setCarPlate(String carPlate) {
        this.carPlate = StringUtils.upperCase(carPlate);
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.DEFAULT_STYLE);
    }
}
