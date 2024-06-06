package com.study.hotelland.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;

@Documented
@Constraint(validatedBy = ValidatorValidVisitorName.class)
@Target({METHOD, FIELD, ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidVisitorName {

    String message() default "Visitor with the given name already exists";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
