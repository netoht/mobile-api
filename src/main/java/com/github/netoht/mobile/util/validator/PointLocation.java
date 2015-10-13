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
@Pattern(regexp = "^[-]?\\d+(\\.\\d+)?,[-]?\\d+(\\.\\d+)?$")
@Target({ ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
@ReportAsSingleViolation
@Documented
public @interface PointLocation {

    String message() default "latitude e longitude devem ser informadas corretamente por exemplo -12.000,10.000";
    Class<?>[]groups() default {};
    Class<? extends Payload>[]payload() default {};
}