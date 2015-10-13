package com.github.netoht.mobile.util.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;

@NotBlank
@Pattern(regexp = "[A-Z]{3}-[0-9]{4}", message = "")
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface CarPlate {

    String message() default "A placa do veículo deve ser informada no padrão esperado de 'AAA-9999'";
    Class<?>[]groups() default {};
    Class<? extends Payload>[]payload() default {};
}