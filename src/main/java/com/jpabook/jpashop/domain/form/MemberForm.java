package com.jpabook.jpashop.domain.form;

import com.jpabook.jpashop.domain.member.MemberAuthority;
import com.jpabook.jpashop.domain.member.MemberGrade;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
public class MemberForm {


    private Long id;

    @NotEmpty(message = "회원 이름은 필수 입니다")
    private String name;

    private String password;

    private MemberAuthority memberAuthority;

    private String city;
    private String street;
    private String zipcode;

    private MemberGrade grade;

}
