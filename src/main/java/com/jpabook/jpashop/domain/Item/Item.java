package com.jpabook.jpashop.domain.Item;

import com.jpabook.jpashop.domain.Category;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


// 부모 테이블에 상속 테이블 전략을 작성해야함 => Single Table
// dtype => 구분 컬럼
@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    private Long id;

    private String name;
    private int price;
    private int stockQuantity;

    // 다대다를 1대다, 다대1로 풀어내야하기 떄문에 중간 테이블과 조인이 필요함
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

}
