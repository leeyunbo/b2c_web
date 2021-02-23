package com.jpabook.jpashop.domain.session;

import com.jpabook.jpashop.domain.member.MemberAuthority;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberInfo {

    private Long id;
    private String name;
    private MemberAuthority memberAuthority;
}
