package jpabook.jpashop.domain;

import jpabook.jpashop.domain.item.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;

@Entity
@Getter
@Setter
public class Category {

    @Id
    @GeneratedValue
    @Column(name = "category_id")
    private Long id;

    private String name;

    @ManyToMany // 실무에서는 ManyToMany 사용 금지 (중간 테이블에 컬럼 추가 불가능, 세밀한 쿼리 진행 불가능)
                // 대다대 맵핑을 일대다, 다대일 맵핑으로 풀어내야함 (@ManyToOne 혹은 @OntToMany)
    @JoinTable(name = "category_item",
            joinColumns = @JoinColumn(name = "category_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")) // 중간 테이블 매핑
    private List<Item> items = new ArrayList<>();

    @ManyToOne(fetch = LAZY) // 모든 연관관계는 지연로딩으로 설정
                             // @XToOne 관계는 기본이 즉시로딩이므로, 직접 지연로딩으로 설정
    @JoinColumn(name = "parent_id")
    private Category parent;

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>();

    //== 연관관계 메소드==//
    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
