package com.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String name;

    @Embedded
    private Address address;

    // 연관관계 주인 X ORDER 테이블에 있는 member에 의해 매핑된다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList();
}
