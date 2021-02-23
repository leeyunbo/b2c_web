package com.jpabook.jpashop.domain.member;

import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.domain.order.Order;
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

    private String password;

    @Enumerated(EnumType.STRING)
    private MemberAuthority memberAuthority = MemberAuthority.COMMON;

    @Embedded
    private Address address;

    // 연관관계 주인 X
    // ORDER 테이블에 있는 member에 의해 매핑된다.
    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList();

    @Enumerated(EnumType.STRING)
    private MemberGrade grade = MemberGrade.BASIC;

    @OneToMany(mappedBy = "member")
    private List<Item> items = new ArrayList();

    public void change(Long memberId, String name, String city, String street, String zipcode, MemberGrade grade, MemberAuthority memberAuthority) {
        Address address = new Address(city, street, zipcode);
        setId(memberId);
        setName(name);
        setAddress(address);
        setGrade(grade);
        setMemberAuthority(memberAuthority);
    }

    public boolean loginCheck(String inputPassword) {
        if(inputPassword.equals(getPassword())) {
            return true;
        }
        else return false;
    }
}
