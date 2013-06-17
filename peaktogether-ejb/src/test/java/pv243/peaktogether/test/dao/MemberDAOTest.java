package pv243.peaktogether.test.dao;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
import pv243.peaktogether.dao.GalleryDAOInt;
import pv243.peaktogether.dao.MemberDAOInt;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.model.Photo;
import pv243.peaktogether.model.Skill;
import pv243.peaktogether.model.Sport;

@RunWith(Arquillian.class)
public class MemberDAOTest {

	@PersistenceContext
	private EntityManager em;
	@Inject
	private UserTransaction utx;
	@Inject
	private MemberDAOInt memberDAO;
	@Inject
	private GalleryDAOInt galleryDAO;

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

		// TestUtils.initDB(utx, em);

		Member member = new Member();
		member.setEmail("respectx@gmail.com");
		member.setUsername("anton");
		member.setRegistered(new Date());

		Set<Skill> skills1 = new HashSet<Skill>();
		Skill skill1 = new Skill();
		skill1.setSport(Sport.BIKING);
		skill1.setLevel(10);
		skills1.add(skill1);
		member.setSkills(skills1);

		memberDAO.create(member);
		Member mnew = memberDAO.findById(member.getId());
		Assert.assertNotNull(mnew);
		Assert.assertTrue(mnew.getEmail().equals("respectx@gmail.com"));

		Set<Skill> snew = mnew.getSkills();

		Assert.assertEquals(skills1.size(), snew.size());

		memberDAO.remove(member);

	}

	@Test
	public void testRemove() {
		// TODO Auto-generated method stub

		Member member = new Member();
		member.setEmail("respecasdtx@gmail.com");
		member.setUsername("anton");
		member.setRegistered(new Date());

		Set<Skill> skills1 = new HashSet<Skill>();
		Skill skill1 = new Skill();
		skill1.setSport(Sport.BIKING);
		skill1.setLevel(10);
		skills1.add(skill1);
		member.setSkills(skills1);

		memberDAO.create(member);
		Member mnew = memberDAO.findById(member.getId());
		Assert.assertNotNull(
				"Member does not exist, please check test for creation", mnew);
		memberDAO.remove(mnew);
		Assert.assertTrue("Removal was not sucessful!",
				memberDAO.findById(member.getId()) == null);

	}

	@Test
	public void testFindById() {

		Member member = new Member();
		member.setEmail("respecddaassdtx@gmail.com");
		member.setUsername("anton");
		member.setRegistered(new Date());

		memberDAO.create(member);

		Assert.assertNotNull("Member does not exist !",
				memberDAO.findById(member.getId()));

		memberDAO.remove(member);

	}

	@Test
	public void testFindAll() {

		Member member = new Member();
		member.setEmail("respecadddsatx@gmail.com");
		member.setUsername("anton");
		member.setRegistered(new Date());
		memberDAO.create(member);

		Member member2 = new Member();
		member2.setEmail("respectxuniqqqx@gmail.com");
		member2.setUsername("antonn");
		member2.setRegistered(new Date());

		memberDAO.create(member2);

		Assert.assertTrue(memberDAO.findAll().size() == 2);
	}

	@Test
	public void testUpdate() {

		Member member = new Member();
		member.setEmail("respecqqweasdtx@gmail.com");
		member.setUsername("anton");
		member.setRegistered(new Date());

		memberDAO.create(member);
		member.setEmail("agiertli@redhat.com");
		memberDAO.update(member);

		Assert.assertTrue(memberDAO.findById(member.getId()).getEmail()
				.equals("agiertli@redhat.com"));

	}

    @Test
    public void testFindByEmail() {
        Member member = new Member();
        member.setEmail("unique512@gmail.com");
        member.setUsername("anton");
        member.setRegistered(new Date());

        Set<Skill> skills1 = new HashSet<Skill>();
        Skill skill1 = new Skill();
        skill1.setSport(Sport.BIKING);
        skill1.setLevel(10);
        skills1.add(skill1);
        member.setSkills(skills1);

        memberDAO.create(member);

        Member m = memberDAO.findByEmail("unique512@gmail.com");

        Assert.assertNotNull("Member not found by email.",m);
        Assert.assertEquals("Found incorrect member", member.getUsername(), m.getUsername());

        Member nonexistent = memberDAO.findByEmail("somesupergreatemailaddressthatcan'texistindb@veryuniquehst.uniqdomain");
        Assert.assertNull("Found a member when it shouldn't", nonexistent);

        Member fault = memberDAO.findByEmail(null);
        Assert.assertNull(fault);
    }

}
