package Fabio.Reis.Scholarship.annotations.noNumbers;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ FIELD })
@Retention(RUNTIME)
@Constraint(validatedBy = NoNumbersValidator.class)
@Documented
public @interface NoNumbers {
    String message() default "Field cannot contain numbers";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
