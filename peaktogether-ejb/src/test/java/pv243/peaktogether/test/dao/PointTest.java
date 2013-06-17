package pv243.peaktogether.test.dao;

import com.vividsolutions.jts.geom.Coordinate;
import com.vividsolutions.jts.geom.GeometryFactory;
import com.vividsolutions.jts.geom.Point;
import org.jboss.arquillian.container.spi.client.deployment.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependency;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.MavenResolutionFilter;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.postgis.PGgeometry;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.Photo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;

import java.util.Collection;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * Member: Coffei
 * Date: 30.5.13
 * Time: 13:15
 * To change this template use File | Settings | File Templates.
 */
@RunWith(Arquillian.class)
public class PointTest {

    @org.jboss.arquillian.container.test.api.Deployment
    public static Archive<?> createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");
        

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addPackage(Location.class.getPackage())
                .addAsResource("test-persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(resolver.resolveAsFiles(new MavenResolutionFilter() {
                    @Override
                    public boolean accept(MavenDependency mavenDependency) {
                        return true;
                    }

                    @Override
                    public MavenResolutionFilter configure(Collection<MavenDependency> mavenDependencies) {
                        return this;
                    }
                }))
                //.addAsLibraries(resolver.artifacts("org.postgis:postgis-jdbc", "org.hibernate:hibernate-spatial", "com.vividsolutions:jts" ).resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction transaction;

    @Test
    public void storeLocation() throws SystemException, NotSupportedException, HeuristicRollbackException, HeuristicMixedException, RollbackException {
        GeometryFactory factory = new GeometryFactory();

        Point point = factory.createPoint(new Coordinate(49.123456, 17.123567));
        Location location = new Location(LocationType.START,point);

        transaction.begin();
        em.joinTransaction();
        em.persist(location);
        assertTrue("location is persisted", em.contains(location));

        transaction.commit();

        Long id = location.getId();

        transaction.begin();
        em.joinTransaction();
        Location found = em.find(Location.class, id);
        assertNotNull("location should be foundable", found);

        transaction.commit();



    }
}
