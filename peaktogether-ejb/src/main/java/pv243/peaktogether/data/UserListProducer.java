package pv243.peaktogether.data;

import java.util.List;

import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import pv243.peaktogether.model.Member;

@RequestScoped
public class UserListProducer {
   @Inject
   private EntityManager em;

   private List<Member> members;

   // @Named provides access the return value via the EL variable name "members" in the UI (e.g.,
   // Facelets or JSP view)
   @Produces
   @Named
   public List<Member> getMembers() {
      return members;
   }

   public void onUserListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final Member member) {
      retrieveAllUsersOrderedByName();
   }

   public void retrieveAllUsersOrderedByName() {
      CriteriaBuilder cb = em.getCriteriaBuilder();
      CriteriaQuery<Member> criteria = cb.createQuery(Member.class);
      Root<Member> user = criteria.from(Member.class);
      // Swap criteria statements if you would like to try out type-safe criteria queries, a new
      // feature in JPA 2.0
      // criteria.select(user).orderBy(cb.asc(user.get(User_.name)));
      criteria.select(user).orderBy(cb.asc(user.get("name")));
      members = em.createQuery(criteria).getResultList();
   }
}
