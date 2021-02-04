package com.jpabook.jpashop.domain;

import com.jpabook.jpashop.domain.Item.Item;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OrderItem {

    @Id @GeneratedValue
    @Column(name = "order_item_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    private int orderPrice; // 주문 가격
    private int count; // 주문 수량

    //==생성 메서드==//
    // 물론 아이템 객체에 가격 정보가 있지만, 할인되는 경우에는 달라질수도 있으므로 따로 가져오는게 낫다
    public static OrderItem createOrderItem(Item item, int orderPrice, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        // 사온만큼 아이템의 재고를 삭제해야함
        item.removeStock(count);
        return orderItem;
    }

    //==비즈니스 로직==//
    // 취소한 경우 해당 아이템의 갯수를 다시 더해줌

    /**
     * 상품 구매 취소
     */
    public void cancel() {
        getItem().addStock(count);
    }

    //==조회 로직==//
    // 아이템의 가격 * 주문한 아이템의 갯수

    /**
     * 주문상품 전체 가격 조회
     * @return : 가격
     */
    public int getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
