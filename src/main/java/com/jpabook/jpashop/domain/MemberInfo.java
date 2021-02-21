package com.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {

    private Long id;
    private String name;
    private MemberAuthority memberAuthority;
}
