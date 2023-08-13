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

        EntityTransaction tx = em.getTransaction();
        tx.begin();

/*
        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team); //pk값 셋팅 되고 영속 상태 됨

            Member member = new Member();
            member.setUsername("member1");
            member.setTeamId(team.getId());
            em.persist(member);
            System.out.println("==============");
            // select * from member m join team t on m.team_id = t.team_id;

            Member findMember = em.find(Member.class, member.getId());

            Long findTeamId = findMember.getTeamId();
            Team findTeam = em.find(Team.class, findTeamId);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
            member.setTeam(team);
            em.persist(member);
            System.out.println("===============");

            em.flush(); //영속성 컨텍스트 -> DB 쿼리
            em.clear(); //영속성 컨텍스트 초기화

            Member findMember = em.find(Member.class, member.getId());

//            Team findTeam = findMember.getTeam();
//            System.out.println("findTeam = " + findTeam.getName());

            //DB에 100번 Team이 있다면
//            Team newTeam = em.find(Team.class, 100L);
//            findMember.setTeam(newTeam); //DB의 외래키 값 업데이트 됨

            List<Member> members = findMember.getTeam().getMembers();
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
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

            Member member = new Member();
            member.setUsername("member1");
            em.persist(member);

            Team team = new Team();
            team.setName("TeamA");
            team.getMembers().add(member); //연관관계 주인이 아님 -> 읽기 전용, add 안됨
            em.persist(team);

            em.flush();
            em.clear();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

        try {

            Team team = new Team();
            team.setName("TeamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("member1");
//            member.setTeam(team);
            member.changeTeam(team); //연관관계 편의 메소드
            em.persist(member);

//            team.addMember(member); //연관관계 편의 메소드는 둘 중에 한 군데서만 하자

            //양쪽에 다 값을 셋팅 해줘야 한다.
            //1. 객체 지향적
            //2. Test case 작성시 -> JPA 없이 동작
//            team.getMembers().add(member); //연관관계 편의 메소드를 만들고 이건 지우자

//            em.flush();
//            em.clear();

            Team findTeam = em.find(Team.class, team.getId()); //1차 캐시
            //em.flush(), em.clear()가 없다면 위의 em.find()는 1차캐시에서 가져옴.
            // -> team.getMembers().add(member); 안해주면 members가 없음
            List<Member> members = findTeam.getMembers();

//            System.out.println("findTeam = " + findTeam); //lombok으로 toString()을 양쪽에 생성 한다면 무한루프
            //컨트롤러에서 Entity를 반환하지 말자! 무한루프 문제, Entity 변경 시 API 스펙 변경. 따라서 DTO로 변환해 반환 하기

            System.out.println("=================");
            //em.flush(), em.clear()가 있다면 team.getMembers().add(member); 안해줘도 이시점에 select 쿼리 날림 (Lazy Loading)
            // -> members 가져옴
            for (Member m : members) {
                System.out.println("m = " + m.getUsername());
            }
            System.out.println("=================");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }

        emf.close();
    }
}

// JPA 모델링 할 때 단방향 매핑으로 설계를 일단 끝낸다.
// 객체 + 테이블 설계를 같이 가져가며 FK 파악 -> 다쪽 (ManyToOne, OneToOne)에서 단방향 매핑을 한다.
// JPA에서의 설계는 단방향 매핑만으로 객체랑 테이블 매핑은 완료가 된다.
// 애플리케이션 개발 단계에서 JPQL로 역방향으로 탐색할 일이 많다.
// -> 양방향 매핑은 필요할 때 추가하면 된다. (테이블 변경 없이 엔티티에 코드만 추가하면 된다.)