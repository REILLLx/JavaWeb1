package com.example.spacecatsmarket.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CosmicWordValidator.class)
@Documented
public @interface CosmicWordCheck {

    String message() default "The name must contain a cosmic term like 'star', 'galaxy', or 'comet'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
