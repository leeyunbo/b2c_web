package com.jpabook.jpashop.domain.form;

import com.jpabook.jpashop.domain.board.BoardCategory;
import com.jpabook.jpashop.domain.board.Comment;
import com.jpabook.jpashop.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BoardForm {
    private Long id;

    private String subject;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    private List<Comment> comments;

    private Member member;
}
