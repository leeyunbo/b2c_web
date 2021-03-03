package com.jpabook.jpashop.domain.board;

import com.jpabook.jpashop.domain.member.Member;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
public class CommentForm {
    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    private LocalDateTime createDate;

    private Member member;

    private String content;
}
