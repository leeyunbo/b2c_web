package com.jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class MemberLoginForm {
    @NotEmpty(message = "계정을 입력해주세요.")
    private String name;

    private String password;
}
