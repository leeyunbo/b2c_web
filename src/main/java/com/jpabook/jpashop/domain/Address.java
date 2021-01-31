package com.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.Entity;


//값 타입 같은 경우는 변경이 일어나서는 안됨, Setter 금지
//기본 생성자는 public이나 protected로 설정해야한다.
//JPA가 이런 제약을 두는 이유는 JPA 구현 라이브러리가 객체를 생성할 때 리플렉션 같은 기술을 사용ㅎ할 수 있도록 지원해야하기 떄
@Embeddable
@Getter
public class Address {

    private String city;
    private String street;
    private String zipcode;

    public Address() {

    }

    public Address(String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
