package hellojpa;

import org.hibernate.Hibernate;

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

/*
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
*/

/*
        try {

            Member member = new Member();
            member.setUsername("member1");

            em.persist(member);

            Team team = new Team();
            team.setName("teamA");
            team.getMembers().add(member);

            em.persist(team);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/


/*
        try {

            Movie movie = new Movie();
            movie.setDirector("aaaa");
            movie.setActor("bbbb");
            movie.setName("바람과함께사라지다");
            movie.setPrice(10000);

            em.persist(movie);

            em.flush();
            em.clear();

//            Movie findMovie = em.find(Movie.class, movie.getId());
//            System.out.println("findMovie = " + findMovie);

            //이 경우 TABLE_PER_CLASS 전략일때 union all로 다 뒤짐
            Item item = em.find(Item.class, movie.getId());
            System.out.println("item = " + item);

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
            member.setUsername("user1");
            member.setCreatedBy("Kim");
            member.setCreatedDate(LocalDateTime.now());

            em.persist(member);

            em.flush();
            em.clear();

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
            member.setUsername("hello");

            em.persist(member);

            em.flush();
            em.clear();

//            Member findMember = em.find(Member.class, member.getId());
            Member findMember = em.getReference(Member.class, member.getId());
            System.out.println("before findMember = " + findMember.getClass());
            System.out.println("findMember.id = " + findMember.getId());
            System.out.println("=====================");
            System.out.println("findMember.username = " + findMember.getUsername()); //처음 호출 시점에 select 쿼리 날림
            System.out.println("after findMember = " + findMember.getClass()); //초기화 이후에도 똑같은 프록시 객체이다.
            System.out.println("findMember.username = " + findMember.getUsername());

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            em.persist(member2);

            em.flush();
            em.clear();

//            Member m1 = em.find(Member.class, member1.getId());
//            Member m2 = em.find(Member.class, member2.getId());

            Member m1 = em.find(Member.class, member1.getId());
            System.out.println("=========================");
            Member m2 = em.getReference(Member.class, member2.getId());

            System.out.println("m1 == m2: " + (m1.getClass() == m2.getClass()));
            System.out.println("m1 == m2: " + (m1 instanceof Member));
            System.out.println("m1 == m2: " + (m2 instanceof Member));

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member m1 = em.find(Member.class, member1.getId());
            System.out.println("m1 = " + m1.getClass());

            //1. 이미 Member가 영속성 컨텍스트 1차 캐시에 있는데 프록시로 가져올 이유가 없음
            Member reference = em.getReference(Member.class, member1.getId());
            System.out.println("reference = " + reference.getClass());

            //2. 같은 영속성 컨텍스트안에서 가져오면 JPA는 항상 true
            //따라서 이미 영속성 컨텍스트에 있으면 실제 엔티티 반환
            System.out.println("a == a: " + (m1 == reference));

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

            //처음에 프록시로 조회해버리면 em.find()도 프록시 반환
            Member findMember = em.find(Member.class, member1.getId());
            System.out.println("findMember = " + findMember.getClass());

            System.out.println("refMember == findMember: " + (refMember == findMember)); //true

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }
*/

/*
        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

//            em.detach(refMember);
            em.clear();
//            em.close();

            refMember.getUsername(); //DB에 쿼리 나가며 프로시 객체 초기화 해야하는데 준영속 상태일땐 안됨

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
*/

/*
        try {

            Member member1 = new Member();
            member1.setUsername("member1");
            em.persist(member1);

            em.flush();
            em.clear();

            Member refMember = em.getReference(Member.class, member1.getId());
            System.out.println("refMember = " + refMember.getClass());

            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));
//            refMember.getUsername(); //강제 초기화
            Hibernate.initialize(refMember); //강제 초기화
            System.out.println("isLoaded = " + emf.getPersistenceUnitUtil().isLoaded(refMember));

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
*/

/*
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());

            System.out.println("m = " + m.getTeam().getClass());

            System.out.println("================");
            System.out.println("teamName = " + m.getTeam().getName()); //team의 속성을 사용하는 시점에 프록시 객체가 초기화되며 DB 쿼리
            System.out.println("================");

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
*/

/*
        try {

            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Team teamB = new Team();
            teamB.setName("teamB");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("member1");
            member1.setTeam(team);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("member2");
            member2.setTeam(teamB);
            em.persist(member2);

            em.flush();
            em.clear();

            //즉시 로딩은 JPQL에서 N+1 문제를 일으킨다. (페치 조인, 엔티티 그래프, 배치 사이즈(1+1)로 해결)
            //(1) SQL: select * from Member
            //(N) SQL: select * from Team where TEAM_ID = xxx
//            List<Member> members = em.createQuery("select m from Member m", Member.class)
//                    .getResultList();

            //페치 조인
            List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class)
                    .getResultList();

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
*/

        try {

            Child child1 = new Child();
            Child child2 = new Child();

            Parent parent = new Parent();
            parent.addChild(child1);
            parent.addChild(child2);

            //cascade 없이
//            System.out.println("before = " + child1.getParent().getId());
//            em.persist(parent); //parent 부터 persist, child 부터 persist 하면 Child 테이블 업데이트 쿼리 또 나감(parent_id)
//            System.out.println("after = " + child1.getParent().getId());
//            em.persist(child1);
//            em.persist(child2);

            em.persist(parent); //cascade = CascadeType.ALL -> childList에 있는 애들도 다 persist

            em.flush();
            em.clear();

            Parent findParent = em.find(Parent.class, parent.getId());
//            findParent.getChildList().remove(0); //orphanRemoval = true -> delete 쿼리
            em.remove(findParent);

            tx.commit();
        } catch(Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }

        emf.close();
    }

    private static void printMember(Member member) {
        System.out.println("member = " + member.getUsername());
    }

    private static void printMemberAndTeam(Member member) {
        String username = member.getUsername();
        System.out.println("username = " + username);

        Team team = member.getTeam();
        System.out.println("team = " + team.getName());
    }
}

// JPA 모델링 할 때 단방향 매핑으로 설계를 일단 끝낸다.
// 객체 + 테이블 설계를 같이 가져가며 FK 파악 -> 다쪽 (ManyToOne, OneToOne)에서 단방향 매핑을 한다.
// JPA에서의 설계는 단방향 매핑만으로 객체랑 테이블 매핑은 완료가 된다.
// 애플리케이션 개발 단계에서 JPQL로 역방향으로 탐색할 일이 많다.
// -> 양방향 매핑은 필요할 때 추가하면 된다. (테이블 변경 없이 엔티티에 코드만 추가하면 된다.)