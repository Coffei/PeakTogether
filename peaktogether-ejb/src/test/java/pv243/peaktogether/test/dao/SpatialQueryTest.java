package pv243.peaktogether.test.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.junit.Test;
import org.junit.runner.RunWith;
import pv243.peaktogether.TestUtils;
import pv243.peaktogether.dao.EventDAOInt;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.dao.PhotoDAOImpl;
import pv243.peaktogether.dao.PhotoDAOInt;
import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Photo;
import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;

import junit.framework.Assert;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 7.6.13
 * Time: 15:16
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Arquillian.class)
public class SpatialQueryTest {

    @PersistenceContext
    private EntityManager em;
    
    @Inject
	private EventDAOInt eventDAO;
	@Inject
	private MemberDAOInt memberDAO;
	

    @Inject
    private UserTransaction tx;

    @Deployment
    public static Archive<?> createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Photo.class.getPackage())
                .addClass(TestUtils.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(resolver.artifacts("org.postgis:postgis-jdbc", "org.hibernate:hibernate-spatial").resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    //@Test
    public void createLocation() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        GeometryFactory gf = new GeometryFactory();
        Location loc1 = new Location(LocationType.START, gf.createPoint(new Coordinate(50.0649367, 14.4124936)));
        Location loc2 = new Location(LocationType.START, gf.createPoint(new Coordinate(49.2262681, 16.6094661)));

        tx.begin();
        em.joinTransaction();

        em.persist(loc1);
        em.persist(loc2);

        tx.commit();


    }

    @Test
    public void distanceQuery() throws Exception {
           
        
        //brno 49.1976183N, 16.7003433E
        //praha 50.0880667N, 14.4336828E
        //bratislava 48.1448719N, 17.1122656E
        
        
        //BRATISLAVA
        Set<Member> friends1 = new HashSet<Member>();
		Member member1 = new Member();
		member1.setEmail("jonas@gmail.com");
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
		location1.setPoint(gf.createPoint(new Coordinate(17.1122656,48.1448719)));
		Event eventBA = new Event();
		eventBA.setCapacity(37);
		eventBA.setDescription("Bratislava");
		eventBA.setName("Bratislava");
		eventBA.setPublicEvent(true);
		eventBA.setOwner(member);
		eventBA.setLimited(true);
		eventBA.setStart(new Date(new Date().getTime() + 10000));
		eventBA.setLocations(locations1);
		
		eventDAO.create(eventBA);
		
		//BRNO
		Set<Location> locations2 = new HashSet<Location>();
		Location location2 = new Location();
		location2.setType(LocationType.START);
		location2.setPoint(gf.createPoint(new Coordinate(16.7003433,49.1976183)));
		Event eventBR = new Event();
		eventBR.setCapacity(37);
		eventBR.setDescription("Brno");
		eventBR.setName("Brno");
		eventBR.setPublicEvent(true);
		eventBR.setOwner(member);
		eventBR.setLimited(true);
		eventBR.setStart(new Date(new Date().getTime() + 10000));
		eventBR.setLocations(locations2);
		
		eventDAO.create(eventBR);
		
		//PRAHA
		
		Set<Location> locations3 = new HashSet<Location>();
		Location location3 = new Location();
		location3.setType(LocationType.START);
		location3.setPoint(gf.createPoint(new Coordinate(16.7003433,49.1976183)));
		Event eventPR = new Event();
		eventPR.setCapacity(37);
		eventPR.setDescription("Prague");
		eventPR.setName("Prague");
		eventPR.setPublicEvent(true);
		eventPR.setOwner(member);
		eventPR.setLimited(true);
		eventPR.setStart(new Date(new Date().getTime() + 10000));
		eventPR.setLocations(locations3);
		
		eventDAO.create(eventPR);
		
		
	
		//WIEN 48.2028242N, 16.3152822E
 
	    Point wien = gf.createPoint(new Coordinate(16.3152822,48.2028242));
	    
	    // wien - brno = 58 km
	    // wien - bratislava = 113 km
	   	// wien - praha 256 km
 
	    List<Event> result;
	    
	    
	    //find nothing
	    result = eventDAO.findEventsByDistanceFromStart(wien, 40);
	    Assert.assertEquals("Spatial query failed 0",0,result.size());
	      
	    
	    //find only bratislava   
	    result = eventDAO.findEventsByDistanceFromStart(wien, 70);
	    Assert.assertEquals("Spatial query failed 1 size",1,result.size());
	    Assert.assertEquals("Spatial query failed 1 name","Bratislava",result.get(0).getName());
	    
	    //find bratislava and brno
	    
	    result = eventDAO.findEventsByDistanceFromStart(wien, 130);
	    Assert.assertEquals("Spatial query failed 2 size",2,result.size());
	    Assert.assertEquals("Spatial query failed 2 ba name","Bratislava",result.get(0).getName());
	    Assert.assertEquals("Spatial query failed 2 br name","Brno",result.get(1).getName());
	 
	    //find bratislava, brno, prague
	    
	    result = eventDAO.findEventsByDistanceFromStart(wien, 270);
	    Assert.assertEquals("Spatial query failed 3 size",3,result.size());
	    Assert.assertEquals("Spatial query failed 3 ba name","Bratislava",result.get(0).getName());
	    Assert.assertEquals("Spatial query failed 3 br name","Brno",result.get(1).getName());
	    Assert.assertEquals("Spatial query failed 3 pr name","Prague",result.get(1).getName());
	    
	    
    }
}
