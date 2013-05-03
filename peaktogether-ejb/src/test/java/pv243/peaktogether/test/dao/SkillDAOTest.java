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

import pv243.peaktogether.model.Photo;

@RunWith(Arquillian.class)
public class SkillDAOTest implements crudDAOTestInt {
	
	   @Deployment
	    public static Archive<?> createTestArchive() {
	        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

	        return ShrinkWrap.create(WebArchive.class, "test.war")
	                .addClasses(Photo.class)
	                        .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
	                .addAsLibraries(resolver.artifacts("org.postgis:postgis-jdbc").resolveAsFiles())
	                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
	    }

	@Override
	@Test
	public void testCreate() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Test
	public void testRemove() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Test
	public void testFindById() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Test
	public void testFindAll() {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Test
	public void testUpdate() {
		// TODO Auto-generated method stub
		
	}

}