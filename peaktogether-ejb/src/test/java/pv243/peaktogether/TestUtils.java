package pv243.peaktogether;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;

import org.junit.Assert;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;

import pv243.peaktogether.model.Event;
import pv243.peaktogether.model.Gallery;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.Photo;
import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;
import pv243.peaktogether.model.User;

public class TestUtils {
	
	public static void initDB(UserTransaction utx, EntityManager em) throws NotSupportedException, SystemException, SecurityException, IllegalStateException, RollbackException, HeuristicMixedException, HeuristicRollbackException, InterruptedException {
		
		boolean emptyDB =
				em.createQuery("from " + Skill.class.getName(), Skill.class).getResultList().isEmpty();
		
		if (emptyDB) {
			
			User user1;
			User user2;
			Event event1 = new Event();
			Event event2 = new Event();
			Gallery gallery1 = new Gallery();
			Gallery gallery2 = new Gallery();
			Location location1 = new Location();
			Location location2 = new Location();
			Photo photo1 = new Photo();
			Photo photo2 = new Photo();
			Skill skill1 = new Skill();
			Skill skill2 = new Skill();
			
			GeometryFactory gf = new GeometryFactory();
			Set<Photo> photos1 = new HashSet<Photo>();
			Set<Photo> photos2 = new HashSet<Photo>();
			Set<Event> owned1 = new HashSet<Event>();
			Set<Event> owned2 = new HashSet<Event>();
			Set<Event> joined1 = new HashSet<Event>();
			Set<Event> joined2 = new HashSet<Event>();
			Set<User> friends1 = new HashSet<User>();
			Set<User> friends2 = new HashSet<User>();
			Set<Gallery> galleries1 = new HashSet<Gallery>();
			Set<Gallery> galleries2 = new HashSet<Gallery>();
			Set<Skill> skills1 = new HashSet<Skill>();
			Set<Skill> skills2 = new HashSet<Skill>();
			Set<Location> locations1 = new HashSet<Location>();
			Set<Location> locations2 = new HashSet<Location>();
			
			skill1.setSport(Sport.SKIING);
			skill1.setLevel(10);
			skill2.setSport(Sport.CLIMBING);
			skill2.setLevel(10);
			
			location1.setType(LocationType.START);
			location1.setPoint(gf.createPoint(new Coordinate(5,4)));
			location2.setType(LocationType.START);
			location2.setPoint(gf.createPoint(new Coordinate(27,17)));
			
			locations1.add(location1);
			locations2.add(location2);
			
			user1 = createNewUser("jamesbond", "007@sis.gov.uk", null, null, null,
					null ,null, null, null, "Bond. James Bond.");
			user2 = createNewUser("superman", "ckent@dailyplanet.com", null, null, null,
					null ,null, null, null, "I hate cryptonite.");
			
			utx.begin(); {
				em.joinTransaction();
				em.persist(skill1);
				em.persist(skill2);
				
				//Not working..
				//em.persist(location1);
				//em.persist(location2);
				
				em.persist(user1);
				em.persist(user2);
				
				skills1.add(skill1);
				skills2.add(skill2);
				
				friends1.add(user2);
				friends2.add(user1);
				
				user1.setFriends(friends1);
				user1.setSkills(skills1);
				user2.setFriends(friends2);
				user2.setSkills(skills2);
			} utx.commit();
			
			gallery1.setName("Bond's gallery.");
			gallery1.setDescription("This is the gallery of James Bond.");
			gallery1.setOwner(user1);
			gallery1.setIsPublic(true);
			gallery2.setName("Superman's gallery.");
			gallery2.setDescription("This is the gallery of Clark Kent");
			gallery2.setOwner(user2);
			gallery2.setIsPublic(true);
			
			utx.begin(); {
				em.joinTransaction();
				em.persist(gallery1);
				em.persist(gallery2);
				
				galleries1.add(gallery1);
				galleries2.add(gallery2);
				
				// Use this code while mapping is not finished
				User tempUser = em.find(User.class, user1.getId());
				tempUser.setGalleries(galleries1);
				em.merge(tempUser);
				tempUser = em.find(User.class, user2.getId());
				tempUser.setGalleries(galleries2);
				em.merge(tempUser);
				
				// Mapping needs to be finished
				// until then use the code above
				/*user1.setGalleries(galleries1);
				em.merge(user1);
				user2.setGalleries(galleries2);
				em.merge(user2);	*/	
			} utx.commit();

			photo1.setTitle("photo1");
			photo1.setDescription("This is a photo description.");
			photo1.setLocation("/home/peaktogether/pics/photo1.jpg");
			photo1.setOwner(user1);
			photo1.setGallery(gallery1);
			photo2.setTitle("photo2");
			photo2.setDescription("This is another photo description.");
			photo2.setLocation("/home/peaktogether/pics/photo2.jpg");
			photo2.setOwner(user2);
			photo2.setGallery(gallery2);
			
			utx.begin(); {
				em.joinTransaction();
				em.persist(photo1);
				em.persist(photo2);	
				
				user1.setPicture(photo1);
				em.merge(user1);
				user2.setPicture(photo2);
				em.merge(user2);
								
				// Finish gallery:photo mapping.
				photos1.add(photo1);
				gallery1.setPhotos(photos1);
				em.merge(gallery1);
				photos2.add(photo2);
				gallery2.setPhotos(photos2);
				em.merge(gallery2);
				
			} utx.commit();
			
			// Not working because of Locations..
			// Otherwise should be correct.
			/*
			event1.setCapacity(37);
			event1.setDescription("Event description.");
			event1.setName("A nice skiing trip.");
			event1.setPublicEvent(true);
			event1.setOwner(user1);
			event1.setLimited(true);
			event1.setStart(new Date(new Date().getTime() + 10000));
			event1.setLocations(locations1);
			event2.setCapacity(2);
			event2.setDescription("Event description.");
			event2.setName("A nice climbing trip.");
			event2.setPublicEvent(true);
			event2.setOwner(user2);
			event2.setLimited(true);
			event2.setStart(new Date(new Date().getTime() + 11000));
			event2.setLocations(locations2);
			
			owned1.add(event1);
			owned2.add(event2);
			joined1.add(event2);
			joined2.add(event1);
			
			utx.begin(); {
				em.persist(event1);
				em.persist(event2);
				user1.setOwnedEvents(owned1);
				user1.setJoinedEvents(joined1);
				user2.setOwnedEvents(owned1);
				user2.setJoinedEvents(joined1);
				em.merge(user1);
				em.merge(user2);
			} utx.commit();
			*/			
		} //else System.out.println("DB NOT EMPTY.");
		
		
		
		
	}
	
	private static User createNewUser(String username, String email, Set<User> friends,
			Set<Gallery> galleries,Point home, Set<Event> joined, Set<Event> owned, 
			Photo pic, Set<Skill> skills, String status) {
		
		User user = new User();
		
		user.setUsername(username);
		user.setEmail(email);
		user.setRegistered(new Date());
		user.setFriends(friends);
		user.setGalleries(galleries);
		user.setHomeLocation(home);
		user.setJoinedEvents(joined);
		user.setOwnedEvents(owned);
		user.setPicture(pic);
		user.setSkills(skills);
		user.setStatus(status);
		
		return user;
	}
}
