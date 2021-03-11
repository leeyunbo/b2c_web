package com.jpabook.jpashop.domain.board;

import com.jpabook.jpashop.domain.member.Member;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "boards")
public class Board {

    @Id
    @GeneratedValue
    @Column(name = "board_id")
    private Long id;

    private String subject;

    private String content;

    @Enumerated(EnumType.STRING)
    private BoardCategory boardCategory;

    private LocalDateTime createDate;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private boolean isDelete = false;

    private String itemName;

    public static Board createBoard(Member member, BoardCategory boardCategory, String subject, String content) {
        Board board = new Board();
        board.setMember(member);
        board.setBoardCategory(boardCategory);
        board.setSubject(subject);
        board.setContent(content);
        board.setCreateDate(LocalDateTime.now());
        return board;
    }


    public void change(String subject, String content, BoardCategory boardCategory) {
        setSubject(subject);
        setContent(content);
        setMember(member);
        setBoardCategory(boardCategory);
    }

    public void delete() {
        setDelete(true);
    }

    public void addComment(Comment comment) {
        getComments().add(comment);
    }
}
