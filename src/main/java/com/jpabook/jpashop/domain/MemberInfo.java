package com.jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

@Getter
@Setter
public class MemberInfo {

    private Long id;
    private String name;
    private MemberAuthority memberAuthority;
}
