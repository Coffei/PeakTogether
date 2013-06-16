package pv243.peaktogether.test.dao;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;

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
import pv243.peaktogether.dao.GalleryDAOImpl;
import pv243.peaktogether.dao.GalleryDAOInt;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.model.Gallery;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Photo;

import static org.junit.Assert.*;

@RunWith(Arquillian.class)
public class GalleryDAOTest {

    @PersistenceContext
    private EntityManager em;
    @Inject
    private UserTransaction utx;
    @Inject
    private GalleryDAOInt dao;

    @Deployment
    public static Archive<?> createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(
                MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        return ShrinkWrap
                .create(WebArchive.class, "test.war")
                .addClasses(TestUtils.class)
                .addPackage(Photo.class.getPackage())
                .addClasses(GalleryDAOImpl.class, GalleryDAOInt.class)
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

    }

    @Test
    public void testUpdate() throws Exception {


    }

    @Test
    public void testRemove() throws Exception {
        TestUtils.initDB(utx, em);
        Member owner = em.createQuery("select m from Member as m", Member.class).getResultList().get(0);

        Gallery gallery = new Gallery();
        gallery.setName("Test");
        gallery.setOwner(owner);
        gallery.setDescription("Testing gallery");
        gallery.setIsPublic(true);

        dao.create(gallery);
        Gallery found = em.find(Gallery.class, gallery.getId());

        assertNotNull("test precondition, gallery not found", found);

        dao.remove(gallery);

        Gallery notfound = em.find(Gallery.class, gallery.getId());
        assertNull("gallery not removed", notfound);

    }

    @Test
    public void testFindAll() throws Exception {

    }

    @Test
    public void testFindById() throws Exception {

    }

}
