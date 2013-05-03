package pv243.peaktogether.test;

import static org.junit.Assert.assertNotNull;

import java.util.logging.Logger;

import javax.inject.Inject;

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

import pv243.peaktogether.controller.MemberRegistration;
import pv243.peaktogether.model.Member;
import pv243.peaktogether.util.Resources;

@RunWith(Arquillian.class)
public class MemberRegistrationTest {
    @Deployment
    public static Archive<?> createTestArchive() {
        MavenDependencyResolver resolver = DependencyResolvers.use(MavenDependencyResolver.class).loadMetadataFromPom("pom.xml");

        return ShrinkWrap.create(WebArchive.class, "test.war")
                .addClasses(Member.class)
                        //.addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsLibraries(resolver.artifacts("org.postgis:postgis-jdbc").resolveAsFiles())
                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
    }
    
    


    @Test
    public void testRegister() throws Exception {
        Member newMember = new Member();
        assertNotNull(newMember);


    }

}
