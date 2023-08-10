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
/*
        try {
            //pagination 가능
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                    .setFirstResult(0)
                    .setMaxResults(2)
                    .getResultList();

            for (Member member : result) {
                System.out.println("member.name = " + member.getName());
            }

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/
        //비영속, 영속
/*
        try {

            //비영속
            Member member = new Member();
            member.setId(101L);
            member.setName("HelloJPA");

            //영속
            System.out.println("=== BEFORE ===");
            em.persist(member); //여기서 DB에 저장 되지 않는다.(쿼리X), 1차 캐시에 저장
//            em.detach(member); //준영속
            System.out.println("=== AFTER ===");

            Member findMember = em.find(Member.class, 101L); //1차 캐시에서 가져와 조회 쿼리X

            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("findMember.name = " + findMember.getName());

            tx.commit(); //영속성 컨텍스트에 있는애가 DB 쿼리로 날라감
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //조회 쿼리 한 번
/*
        try {

            Member findMember1 = em.find(Member.class, 101L); //쿼리 나감
            Member findMember2 = em.find(Member.class, 101L); //1차 캐시에서 조회

            //영속 엔티티의 동일성 보장
            System.out.println("result = " + (findMember1 == findMember2));

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //트랜잭션을 지원하는 쓰기 지연
/*
        try {

            Member member1 = new Member(150L, "A");
            Member member2 = new Member(160L, "B");

            em.persist(member1);
            em.persist(member2);

            System.out.println("================"); // 이후에 쿼리 나감 (<property name="hibernate.jdbc.batch_size" value="10"/> 설정 만큼 한번에 보냄)

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //변경 감지(Dirty Checking)
/*
        try {

            Member member = em.find(Member.class, 150L);
            member.setName("ZZZZ");

            System.out.println("================");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //플러시
/*
        try {

            Member member = new Member(200L, "member200");
            em.persist(member);

            em.flush(); //1차 캐시가 지워지는게 아님

            System.out.println("================"); //이전에 insert 쿼리 나감
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }
*/

        //준영속
        try {

            Member member = em.find(Member.class, 150L); //DB에서 조회해서 1차 캐시에 올림 -> 영속 상태
            member.setName("AAAA");

//            em.detach(member); //준영속 -> update 쿼리가 안나감
            em.clear(); //영속성 컨텍스트 초기화

            Member member2 = em.find(Member.class, 150L); //조회 쿼리 또 나감

            System.out.println("================");
            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close(); //DB connection을 물고 동작하기 때문에 꼭 닫아줘야 해
        }

        emf.close();
    }
}
