package com.jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NegativeOrZero;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class BookForm {

    //== 상품 공통 속성 ==//
    private Long id;

    @NotEmpty(message = "도서 이름격은 필수 입니다")
    private String name;

    @NegativeOrZero(message = "도서 가격은 0 이상이 되어야 합니다")
    private int price;

    @NegativeOrZero(message = "도서 수량은 0 이상이 되어합야 합니다")
    private int stockQuantity;

    //== 책 속성 ==//
    private String author;
    private String isbn;
}
