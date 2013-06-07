package pv243.peaktogether.test.dao;

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
import pv243.peaktogether.model.Photo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;


@RunWith(Arquillian.class)
public class PhotoDAOTest {

    @PersistenceContext
    private EntityManager em;

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

	@Test
	public void testCreate() throws Exception {
        TestUtils.initDB(tx, em);
    }

	@Test
	public void testRemove() {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testFindById() {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testFindAll() {
		// TODO Auto-generated method stub
		
	}

	@Test
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}
    
    
    

}
