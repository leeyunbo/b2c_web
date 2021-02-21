package com.jpabook.jpashop.domain.Item;

import com.jpabook.jpashop.domain.Category;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.exception.NotEnoughStockException;
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

    // 연관관계 주인
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    public void change(String name, int price, int stockQuantity) {
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    public void create(String name, int price, int stockQuantity) {
        setName(name);
        setPrice(price);
        setStockQuantity(stockQuantity);
    }

    // 다대다를 1대다, 다대1로 풀어내야하기 떄문에 중간 테이블과 조인이 필요함
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();

    // 엔티티 자체가 해결할 수 있는 것들은 엔티티 안에 비즈니스 로직을 넣는 것이 좋음 (사용되는 데이터가 엔티티 내부에 있는 경우) => 응집력
    //==비즈니스 로직==//

    /**
     * 재고 수량 증가
     * @param quantity : 추가할 재고 갯수
     */
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    /**
     * 재고 수량 감소
     * @param quantity : 제거할 재고 갯수
     */
    public void removeStock(int quantity) {
         int restStock = this.stockQuantity - quantity;
         if (restStock < 0) {
             throw new NotEnoughStockException("need more stock");
         }
         this.stockQuantity = restStock;
    }
}
