package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        //JPA는 데이터 변경할 때 트랜잭션안에서 작업
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        //회원 등록
/*
        try {
            Member member = new Member();
            member.setId(2L);
            member.setName("HelloA");

            em.persist(member);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //회원 삭제
/*
        try {

            Member findMember = em.find(Member.class, 1L);

            em.remove(findMember);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //회원 수정
/*
        try {

            Member findMember = em.find(Member.class, 1L);
            findMember.setName("HelloJPA");

            tx.commit(); //커밋직전에 update 쿼리 날림
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //JPQL
        try {
            //pagination 가능
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit(); //커밋직전에 update 쿼리 날림
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }

        emf.close();
    }
}
