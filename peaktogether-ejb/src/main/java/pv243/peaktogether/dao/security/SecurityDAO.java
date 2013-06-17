package pv243.peaktogether.dao.security;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.credential.Password;
import org.picketlink.idm.model.User;

import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 * Created with IntelliJ IDEA.
 * User: Coffei
 * Date: 17.6.13
 * Time: 14:33
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class SecurityDAO {

    @Inject
    private IdentityManager identityManager;

    public void persistUser(User user, Password password) {
        identityManager.add(user);
        identityManager.updateCredential(user, password);
    }


}
