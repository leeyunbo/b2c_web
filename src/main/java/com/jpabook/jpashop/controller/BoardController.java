package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.board.Board;
import com.jpabook.jpashop.domain.board.BoardCategory;
import com.jpabook.jpashop.domain.board.BoardSearch;
import com.jpabook.jpashop.domain.form.BoardForm;
import com.jpabook.jpashop.domain.form.BookForm;
import com.jpabook.jpashop.domain.member.Member;
import com.jpabook.jpashop.domain.session.MemberInfo;
import com.jpabook.jpashop.service.BoardService;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    /**
     * 게시글 등록
     * @param model
     * @return
     */
    @GetMapping("/boards/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BoardForm());
        return "boards/createBoardsForm";
    }

    /**
     * 게시글 등록
     */
    @PostMapping("/boards/new")
    public String create(BoardForm form, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();

        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        Member member = memberService.findOne(memberInfo.getId());

        Board board = Board.createBoard(member, BoardCategory.REQUESTS, form.getSubject(), form.getContent());
        boardService.saveBoard(board);

        return "redirect:/requests";
    }

    /**
     * 게시글 수정
     */
    @GetMapping("/boards/{boardId}/update")
    public String updateForm(@PathVariable("boardId") Long boardId, Model model) {
        Board board = boardService.findBoard(boardId);

        BoardForm boardForm = new BoardForm();
        boardForm.setId(board.getId());
        boardForm.setSubject(board.getSubject());
        boardForm.setContent(board.getContent());
        boardForm.setMember(board.getMember());
        boardForm.setBoardCategory(board.getBoardCategory());

        model.addAttribute("board", boardForm);

        return "boards/updateBoardForm";
    }

    /**
     * 게시글 수정
     */
    @PostMapping("/boards/{boardId}/update")
    public String updateForm(@PathVariable("boardId") Long boardId, BoardForm form) {
        boardService.updateBoard(form.getId(), form.getSubject(), form.getContent(), form.getMember(), form.getBoardCategory());

        return "boards/updateBoardForm";
    }


    /**
     * 게시글 삭제
     */


    /**
     * 요청 게시판 리스트 가져오기
     * @param model
     * @return
     */
    @GetMapping("/boards/requests")
    public String requestList(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "/boards/requestBoardList";
    }

    /**
     * 광고 게시판 리스트 가져오기
     * @return
     */
    @GetMapping("/boards/advertisements")
    public String advertisementList() {
        return "";
    }
}
