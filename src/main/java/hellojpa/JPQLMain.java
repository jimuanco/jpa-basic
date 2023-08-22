package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class JPQLMain {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

/*
        try {

            List<Member> result = em.createQuery(
                    "select m from Member m where m.username like '%kim%'",
                    Member.class
            ).getResultList();

            for (Member member : result) {
                System.out.println("member = " + member);
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        try {

            //Criteria 사용 준비
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Member> query = cb.createQuery(Member.class);

            Root<Member> m = query.from(Member.class);

            CriteriaQuery<Member> cq = query.select(m).where(cb.equal(m.get("username"), "kim"));

            List<Member> resultList = em.createQuery(cq)
                    .getResultList();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

        try {

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            //commit, query -> flush

            //JPA 관련 기술이 아니라면
            //em.flush();
            //dbconn.executeQuery("select * from member");

            List<Member> resultList = em.createNativeQuery("select MEMBER_ID, city, street, zipcode, USERNAME from MEMBER", Member.class)
                    .getResultList();

            for (Member member1 : resultList) {
                System.out.println("member1.username = " + member1.getUsername());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
