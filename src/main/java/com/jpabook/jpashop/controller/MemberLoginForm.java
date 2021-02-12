package com.jpabook.jpashop.controller;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberLoginForm {

    @NotEmpty(message = "계정을 입력해주세요.")
    private String name;

    @NotEmpty(message = "비밀번호를 입력해주세요.")
    private String passwd;
}
