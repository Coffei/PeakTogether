package pv243.peaktogether.model.validations;

import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.validations.validator.HasLocationsValidator;

import javax.validation.Constraint;
import java.lang.annotation.*;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 28.4.13
 * Time: 12:22
 * Constraint to verify there are at least requested count of locations with requested type.
 */
@Constraint(validatedBy = HasLocationsValidator.class)
@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface HasLocations {
    String message() default "there is not enough locations";
    LocationType typeRequested() default LocationType.START;
    int countRequested() default 1;
}
