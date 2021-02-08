package com.jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class BookForm {

    //== 상품 공통 속성 ==//
    private Long id;

    @NotEmpty(message = "도서 이름은 필수 입니다")
    private String name;

    private int price;

    private int stockQuantity;

    //== 책 속성 ==//
    private String author;
    private String isbn;
}
