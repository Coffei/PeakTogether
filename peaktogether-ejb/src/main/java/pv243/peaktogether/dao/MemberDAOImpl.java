package pv243.peaktogether.dao;

import org.picketlink.idm.IdentityManager;
import org.picketlink.idm.model.User;
import pv243.peaktogether.model.Member;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Stateless
public class MemberDAOImpl implements MemberDAOInt {

	@PersistenceContext
	private EntityManager em;

    @Inject
    private IdentityManager identityManager;

	@Override
	public void create(Member member) {
		em.persist(member);
	}

	@Override
	public void remove(Member member) {
		member = em.merge(member);
        User user = identityManager.getUser(member.getEmail()); //find corresponding identity object
        identityManager.remove(user); //remove the identity obj.
		em.remove(member);
	}

	@Override
	public Member update(Member member) {
		return em.merge(member);
	}

	@Override
	public Member findById(Long id) {
		return em.find(Member.class, id);
	}

    @Override
    public Member findByEmail(String email) {
        if(email==null)
            return null;

        TypedQuery<Member> query = em.createQuery("from Member m where m.email = :email", Member.class).setMaxResults(1);
        query.setParameter("email", email);
        List<Member> list = query.getResultList();
        if(list.isEmpty())
            return null;

        return list.get(0);
    }

    @Override
	public List<Member> findAll() {
		List<Member> results =
				em.createQuery("from "+Member.class.getName(), Member.class).getResultList();
		return results;
	}

}
