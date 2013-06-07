package pv243.peaktogether.model.validations.validator;

import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.validations.CorrectCapacity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 28.4.13
 * Time: 10:20
 * To change this template use File | Settings | File Templates.
 */
public class EventCapacityValidator implements ConstraintValidator<CorrectCapacity, Event> {

    @Override
    public void initialize(CorrectCapacity correctCapacity) { }

    @Override
    public boolean isValid(Event event, ConstraintValidatorContext constraintValidatorContext) {
        if(event.isLimited()) {
            return event.getCapacity() != null && event.getCapacity() > 1;
        }

        return true;
    }
}
