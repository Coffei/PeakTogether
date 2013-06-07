package pv243.peaktogether.test.dao;

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
import pv243.peaktogether.dao.PhotoDAOImpl;
import pv243.peaktogether.dao.PhotoDAOInt;
import pv243.peaktogether.model.Gallery;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Photo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class PhotoDAOTest {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction tx;

    @Inject
    private PhotoDAOInt dao;
	
    @Deployment
    public static Archive<?> createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Photo.class.getPackage())
                .addClasses(PhotoDAOInt.class, PhotoDAOImpl.class)
                .addClass(TestUtils.class)
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(resolver.artifacts("org.postgis:postgis-jdbc", "org.hibernate:hibernate-spatial").resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

	@Test
	public void testCreate() throws Exception {
        TestUtils.initDB(tx, em);

        //I need:
        Gallery gallery = em.createQuery("select g from Gallery as g", Gallery.class).getResultList().get(0);
        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);

        Photo photo = new Photo();
        photo.setDescription("really cool test photo");
        photo.setGallery(gallery);
        photo.setLocation("/fake/path/to/image.jpg");
        photo.setTitle("Yeah!");

        dao.create(photo);

        Photo found = em.find(Photo.class, photo.getId());
        assertNotNull(found);

        assertEquals("gallery ID should equal", gallery.getId(), found.getGallery().getId());
        assertEquals("owner ID should equal", owner.getId(), found.getOwner().getId());
        assertEquals("path should equal","/fake/path/to/image.jpg", found.getLocation());

    }

	@Test
	public void testRemove() throws Exception{
        TestUtils.initDB(tx, em);

        Gallery gallery = em.createQuery("select g from Gallery as g", Gallery.class).getResultList().get(0);
        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);

        Photo photo = new Photo();
        photo.setDescription("really cool test photo");
        photo.setGallery(gallery);
        photo.setLocation("/fake/path/to/image.jpg");
        photo.setTitle("Yeah!");

        dao.create(photo);

        Photo found = em.find(Photo.class, photo.getId());
        assertNotNull("photo doesn't exist, check testCreate!", found);

        System.out.println(found);

        dao.remove(found);
        System.out.println(photo);
        Photo notfound = em.find(Photo.class, photo.getId());
        System.out.println(notfound);
        assertNull("photo was not removed", notfound);

	}

	@Test
	public void testFindById() throws Exception {
        TestUtils.initDB(tx, em);

        Gallery gallery = em.createQuery("select g from Gallery as g", Gallery.class).getResultList().get(0);
        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);

        Photo photo = new Photo();
        photo.setDescription("really cool test photo");
        photo.setGallery(gallery);
        photo.setLocation("/fake/path/to/image.jpg");
        photo.setTitle("Yeah!");

        dao.create(photo);
        Long id = photo.getId();

        Photo found = dao.findById(id);

        assertNotNull("photo was not found after creation", found);
        assertEquals("photo is not the same", photo, found);
		
	}

	@Test
	public void testFindAll() throws Exception {
        TestUtils.initDB(tx, em);

        int initialSize = dao.findAll().size();

        Gallery gallery = em.createQuery("select g from Gallery as g", Gallery.class).getResultList().get(0);
        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);

        Photo photo = new Photo();
        photo.setDescription("really cool test photo");
        photo.setGallery(gallery);
        photo.setLocation("/fake/path/to/image.jpg");
        photo.setTitle("Yeah!");

        dao.create(photo);

        int afterAddSize = dao.findAll().size();

        assertEquals("size of found photos doesn't match", initialSize+1, afterAddSize);
		
	}

	@Test
	public void testUpdate() throws Exception {
		//test gallery change, one basic attr
        TestUtils.initDB(tx, em);

        Gallery gallery = em.createQuery("select g from Gallery as g", Gallery.class).getResultList().get(0);
        Gallery gallery1 = em.createQuery("select g from Gallery as g", Gallery.class).getResultList().get(1);
        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);

        Photo photo = new Photo();
        photo.setDescription("really cool test photo");
        photo.setGallery(gallery);
        photo.setLocation("/fake/path/to/image.jpg");
        photo.setTitle("Yeah!");

        dao.create(photo);
        assertEquals("test precondition", gallery.getId(), photo.getGallery().getId());

        //change gallery and title
        photo.setGallery(gallery1);
        photo.setTitle("changed but ok");
        dao.update(photo);

        Photo found = dao.findById(photo.getId());
        assertEquals("gallery not changed",gallery1.getId(),found.getGallery().getId());
        assertEquals("title not changed", "changed but ok", found.getTitle());

	}
    
    
    

}
