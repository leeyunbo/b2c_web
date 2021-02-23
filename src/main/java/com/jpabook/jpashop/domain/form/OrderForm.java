package com.jpabook.jpashop.domain.form;

import com.jpabook.jpashop.domain.order.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
public class OrderForm {
    private Long id;

    private String name;

    private int count;

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 (ORDER, CANCEL)
}
