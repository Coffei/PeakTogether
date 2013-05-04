package pv243.peaktogether.test;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.User;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

import static org.junit.Assert.assertEquals;


/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 28.4.13
 * Time: 14:26
 * Test for Event validation.
 */
@RunWith(Arquillian.class)
public class EventValidationTest {
    private static Validator validator;

    @Deployment
    public static Archive<?> createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");


        return ShrinkWrap.create(WebArchive.class, "testing.war")
                .addClasses(Event.class, User.class, Location.class, LocationType.class)
                .addAsLibraries(resolver.artifacts("org.postgis:postgis-jdbc", "com.vividsolutions:jts", "org.slf4j:slf4j-api").resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");

    }



    @BeforeClass
    public static void init() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
        System.out.println("inited");
    }

    private Event buildCorrectEvent() {
        System.out.println("building");

        User owner = new User();
        List<Location> locations = new ArrayList<Location>(1);
        locations.add(new Location(LocationType.START, null));

        Event event = new Event();
        event.setName("cool name");
        event.setDescription("cool loooooooong description");
        event.setPublicEvent(true);
        event.setOwner(owner);
        event.setLimited(false);
        event.setStart(new Date(new Date().getTime() + 10000));
        event.setLocations(locations);

        return event;
    }

    @Test
    public void correctBuilderEvent() {
        System.out.println("first test");
        Event event = buildCorrectEvent();

       // Set<ConstraintViolation<Event>> violations = validator.validate(event);
        //assertEquals(0, violations.size());
    }

    //@Test
    public void capacityValidation() {
        Event event = buildCorrectEvent();

        //incorrect capacity
        event.setLimited(true);
        event.setCapacity(null);



    }

    private void assertViolations(Event event, int expectedCount) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);

        assertEquals(expectedCount, violations.size());
    }

    private void assertViolations(Event event, int expectedCount, String[] messageFragments) {
        Set<ConstraintViolation<Event>> violations = validator.validate(event);
        assertEquals(expectedCount, violations.size());
        for(String msg : messageFragments) {
            assertViolationsMessage(violations, msg);
        }
    }


    private void assertViolationsMessage(Collection<ConstraintViolation<Event>> violations, String message) {
        //TODO: implement
    }

}
