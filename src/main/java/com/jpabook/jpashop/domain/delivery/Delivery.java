package com.jpabook.jpashop.domain.delivery;

import com.jpabook.jpashop.domain.member.Address;
import com.jpabook.jpashop.domain.order.Order;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class Delivery {

    @Id @GeneratedValue
    @Column(name = "delivery_id")
    private Long id;

    // OneToOne 관계에서는 어디든 FK를 넣어도 상관이 없음
    // 보통 order에서 delivery를 찾으므로 order에다가 FK를 넣는다.
    @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
    private Order order;

    @Embedded
    private Address address;

    // ORDINAL : 숫자 (default), STRING : 문자열
    // 숫자로 했을 때 만약 중간에 뭔가 다른 값이 끼어들면 모든 로직이 무너짐
    @Enumerated(EnumType.STRING)
    private DeliveryStatus status; // READY, COMP

}
