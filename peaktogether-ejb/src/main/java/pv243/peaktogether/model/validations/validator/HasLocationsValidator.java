package pv243.peaktogether.model.validations.validator;

import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.validations.HasLocations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Collection;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 28.4.13
 * Time: 12:25
 * To change this template use File | Settings | File Templates.
 */
public class HasLocationsValidator implements ConstraintValidator<HasLocations, Collection<Location>> {

    private LocationType type;
    private int count;

    @Override
    public void initialize(HasLocations hasStart) {
       type = hasStart.typeRequested();
        count = hasStart.countRequested();
    }

    @Override
    public boolean isValid(Collection<Location> locations, ConstraintValidatorContext constraintValidatorContext) {
      if(locations == null)
          return false;

      for (Location location : locations) {
          if(location.getType().equals(type))
              count--;

          if(count <= 0)
              return true;
      }

        return false;
    }
}
