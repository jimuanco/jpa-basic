package hellojpa;

import javax.persistence.*;

@Entity
//@Inheritance(strategy = InheritanceType.JOINED)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@DiscriminatorColumn //DTYPE(기본값) 컬럼 -> value 기본값은 엔티티명, SINGLE_TABLE에선 없어도 생긴다., TABLE_PER_CLASS에선 의미 없다.
public abstract class Item { //추상 클래스로 해줘야 TABLE_PER_CLASS에서 ITEM 테이블이 생기지 X

    @Id @GeneratedValue
    private Long id;

    private String name;
    private int price;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
