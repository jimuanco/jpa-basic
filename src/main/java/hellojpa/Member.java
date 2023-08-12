package hellojpa;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;

@Entity
//@SequenceGenerator(name = "member_seq_generator", sequenceName = "member_seq") //테이블마다 다른 시퀀스를 쓰고 싶을 때
@SequenceGenerator(
        name = "MEMBER_SEQ_GENERATOR",
        sequenceName = "MEMBER_SEQ", //매핑할 데이터베이스 시퀀스 이름
        initialValue = 1, allocationSize = 50)
public class Member {

    @Id
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "member_seq_generator")
//    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB에 insert 돼야 id 값을 알 수 있어 em.persist시 바로 insert 함
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
//    private String id;

    @Column(name = "name", nullable = false)
    // JPA로 컬럼 변경 불가 (DB에선 됨)
//    @Column(name = "name", updatable = false)
    //not null 제약조건
//    @Column(name = "name", nullable = false)
    //제약조건 이름이 랜덤이라 잘 안씀, @Table의 uniqueConstraints는 이름 지정 가능해 이걸로 씀
//    @Column(name = "name", nullable = false, unique = true)
    // varchar(10)
//    @Column(name = "name", length = 10)
    //컬럼 정보를 직접 줄 수 있다. (문구가 그대로 DDL문에 들어감)
//    @Column(name = "name", columnDefinition = "varchar(100) default ‘EMPTY'")
    private String username;

/*
    private Integer age;

    //기본이 ORDINAL -> integer : enum의 순서를 DB에 저장(0, 1, 2, ...)
    //EnumType.STRING -> varchar(255) : 무조건 이걸로 써
    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; //자바 Date에는 날짜와 시간 다 있음, DB는 날짜(DATE), 시간(TIME), 날짜+시간(TIMESTAMP) 둘 다 포함 된거 구분해서 씀

    //최신 하이버네이트 -> 셋 다 @Temporal 생략 가능
    private LocalDate testLocalDate; //date
    private LocalDateTime testLocalDateTime; //timestamp
    private LocalTime testLocalTime; //time

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastModifiedDate;

    //@Lob + String -> clob
    @Lob
    private String description;

    @Transient
    private int temp;
*/

    public Member() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
