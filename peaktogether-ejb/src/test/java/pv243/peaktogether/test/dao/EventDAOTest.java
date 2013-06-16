package pv243.peaktogether.test.dao;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import pv243.peaktogether.TestUtils;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.model.*;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


@RunWith(Arquillian.class)
public class EventDAOTest {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private UserTransaction utx;
	@Inject
	private EventDAOInt eventDAO;
	@Inject
	private MemberDAOInt memberDAO;

    @Inject
    private EventDAOInt dao;

	@Deployment
	public static Archive<?> createTestArchive() {
		MavenDependencyResolver resolver = DependencyResolvers.use(
				MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

		return ShrinkWrap
				.create(WebArchive.class, "test.war")
				.addClasses(TestUtils.class)
				.addPackage(Photo.class.getPackage())
				.addPackage(MemberDAOInt.class.getPackage())
				.addAsResource("test-persistence.xml",
						"META-INF/persistence.xml")
				.addAsLibraries(
						resolver.artifacts("org.postgis:postgis-jdbc",
								"org.hibernate:hibernate-spatial")
								.resolveAsFiles())
				.addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Test
	public void testCreate() throws Exception {
        TestUtils.initDB(utx, em);

        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);


        Event event = new Event();
        event.setDescription("blaa");
        event.setName("blaaaaa");
        event.setLimited(false);
        event.setOwner(owner);
        event.setCapacity(5);
        event.setPublicEvent(true);
        event.setStart(new Date(new Date().getTime() + 10000));

        Set<Location> locations = new HashSet<Location>(2);
        GeometryFactory gf = new GeometryFactory();
        Location loc1 = new Location(LocationType.START, gf.createPoint(new Coordinate(0,0)));
        Location loc2 = new Location(LocationType.START, gf.createPoint(new Coordinate(1,0)));

        locations.add(loc1);
        locations.add(loc2);

        event.setLocations(locations);

        dao.create(event);

        Event found = em.find(Event.class, event.getId());
        assertNotNull("persisted event not found", found);
        assertEquals("locations not persisted", 2, found.getLocations().size());
	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testRemove() throws Exception {
		
		
		
		Set<Member> friends1 = new HashSet<Member>();
		Member member1 = new Member();
		member1.setEmail("agiertli@gmail.com");
		member1.setUsername("jonas");
		member1.setRegistered(new Date());
		memberDAO.create(member1);
		
		friends1.add(member1);
		
		Skill skill1 = new Skill();
		Set<Skill> skills1 = new HashSet<Skill>();
		skill1.setSport(Sport.SKIING);
		skill1.setLevel(10);
		skills1.add(skill1);
		Member member = new Member();
		member.setEmail("respectx@gmail.com");
		member.setUsername("anton");
		member.setRegistered(new Date());
		member.setFriends(friends1);
		member.setSkills(skills1);
		
		memberDAO.create(member);
		
		
		Set<Location> locations1 = new HashSet<Location>();
		Location location1 = new Location();
		location1.setType(LocationType.START);
		GeometryFactory gf = new GeometryFactory();
		location1.setPoint(gf.createPoint(new Coordinate(5,4)));
		Event event1 = new Event();
		event1.setCapacity(37);
		event1.setDescription("Event description.");
		event1.setName("A nice skiing trip.");
		event1.setPublicEvent(true);
		event1.setOwner(member);
		event1.setLimited(true);
		event1.setStart(new Date(new Date().getTime() + 10000));
		event1.setLocations(locations1);
		
		eventDAO.create(event1);
		
		Event found = eventDAO.findById(event1.getId());
		Assert.assertNotNull("Event not found",found);
		
		eventDAO.remove(found);
		
		Assert.assertNull("Nasli sme event, aj ked tam uz nema byt",eventDAO.findById(found.getId()));
		

	}

	@Test
	public void testFindAll() throws Exception {

	}

	@Test
	public void testFindById() throws Exception {

	}

}
