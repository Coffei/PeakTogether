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
import org.junit.Test;
import org.junit.runner.RunWith;
import pv243.peaktogether.TestUtils;
import pv243.peaktogether.dao.PhotoDAOImpl;
import pv243.peaktogether.dao.PhotoDAOInt;
import pv243.peaktogether.model.Location;
import pv243.peaktogether.model.LocationType;
import pv243.peaktogether.model.Photo;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;

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
        Query query = em.createQuery("select ST_Distance_Sphere(loc1.point, loc2.point) from Location loc1, Location loc2");
        System.out.println(query.getResultList());




    }
}
