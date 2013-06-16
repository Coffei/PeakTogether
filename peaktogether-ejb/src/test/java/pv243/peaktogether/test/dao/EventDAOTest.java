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
import org.junit.Test;
import org.junit.runner.RunWith;

import pv243.peaktogether.TestUtils;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.model.Photo;

@RunWith(Arquillian.class)
public class EvenDAOTest {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private UserTransaction utx;

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

	}

	@Test
	public void testUpdate() throws Exception {

	}

	@Test
	public void testRemove() throws Exception {

	}

	@Test
	public void testFindAll() throws Exception {

	}

	@Test
	public void testFindById() throws Exception {

	}

}
