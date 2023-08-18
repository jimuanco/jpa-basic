package hellojpa;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaTest {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");

        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {

            ManyTest manyTest = new ManyTest();
            manyTest.setUsername("test");
//            em.persist(manyTest);

            OneTest oneTest = new OneTest();
            oneTest.setName("one");
            oneTest.getMembers().add(manyTest);

            em.persist(oneTest);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}
