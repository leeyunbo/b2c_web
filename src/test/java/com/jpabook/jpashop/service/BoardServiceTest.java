package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.domain.board.Board;
import com.jpabook.jpashop.domain.board.BoardCategory;
import com.jpabook.jpashop.domain.board.Comment;
import com.jpabook.jpashop.domain.board.CommentForm;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.member.MemberAuthority;
import com.jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class BoardServiceTest {

    @Autowired
    BoardService boardService;

    @Autowired
    MemberService memberService;

    @Test
    public void 게시판추가() {
        //given면 이게 주어지면
        Member member = new Member();
        member.setName("lee yun bok");
        member.setMemberAuthority(MemberAuthority.ADMIN);
        memberService.join(member);

        Board board = new Board();
        board.setSubject("Subject");
        board.setMember(member);
        board.setContent("Content");
        board.setBoardCategory(BoardCategory.ADVERTISEMENTS);

        //when 이걸 실행하면
        Long boardId = boardService.saveBoard(board);

        // 같은 트랜잭션에서 ID 값이 같으면 같은 영속성 컨텍스트가 되어서 딱 하나로 관리된다.
        // 따라서 True가 나올 수 있다.
        // then 이렇게 된다
        assertEquals(board, boardService.findBoard(boardId));
    }

    @Test
    public void 게시판수정() {
        //given
        Member member = new Member();
        member.setName("lee yun bok");
        member.setMemberAuthority(MemberAuthority.ADMIN);
        memberService.join(member);

        Board board = new Board();
        board.setSubject("before subject");
        board.setContent("before content");
        board.setBoardCategory(BoardCategory.ADVERTISEMENTS);
        board.setMember(member);

        //when
        Long boardId = boardService.saveBoard(board);
        Board beforeBoard = boardService.findBoard(boardId);

        boardService.updateBoard(boardId, "next subject", "next content", BoardCategory.REQUESTS);
        Board afterBoard = boardService.findBoard(boardId);


        //then
        assertEquals(afterBoard.getContent(), "next content");
        assertEquals(afterBoard.getSubject(), "next subject");
        assertEquals(afterBoard.getBoardCategory(), BoardCategory.REQUESTS);
    }

    @Test
    public void 댓글등록() {
        //given
        Member member = new Member();
        member.setName("lee yun bok");
        member.setMemberAuthority(MemberAuthority.ADMIN);
        memberService.join(member);

        Board board = new Board();
        board.setSubject("before subject");
        board.setContent("before content");
        board.setBoardCategory(BoardCategory.ADVERTISEMENTS);
        board.setMember(member);
        boardService.saveBoard(board);

        String content = "댓글 내용";

        boardService.createComment(board.getId(), content, member);
        boardService.createComment(board.getId(), content, member);
        boardService.createComment(board.getId(), content, member);
        boardService.createComment(board.getId(), content, member);
        boardService.createComment(board.getId(), content, member);
        boardService.createComment(board.getId(), content, member);

        Board board1 = boardService.findBoard(board.getId());
        assertEquals(board1.getComments().size(), 6);

        for(Comment comment : board1.getComments()) {
            System.out.println(comment.getContent());
        }

    }
}
