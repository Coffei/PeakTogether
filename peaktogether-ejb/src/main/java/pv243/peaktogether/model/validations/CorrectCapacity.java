package pv243.peaktogether.model.validations;

import pv243.peaktogether.model.validations.validator.EventCapacityValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 28.4.13
 * Time: 10:21
 * Custom constraint to validate capacity and isMaxCapacity properties.
 */
@Constraint(validatedBy = EventCapacityValidator.class)
@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CorrectCapacity {
    String message() default "capacity is wrong";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
