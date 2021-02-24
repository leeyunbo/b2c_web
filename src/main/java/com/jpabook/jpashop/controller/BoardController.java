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
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;
    private final MemberService memberService;

    @GetMapping("/boards/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "boards/createBoardsForm";
    }

    @PostMapping("/boards/new")
    public String create(BoardForm form, HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession();
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        Member member = memberService.findOne(memberInfo.getId());

        Board board = Board.createBoard(member, BoardCategory.REQUESTS, form.getSubject(), form.getContent());
        boardService.saveBoard(board);

        return "redirect:/requests";
    }



    @GetMapping("/boards/requests")
    public String requestList(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "/boards/requestBoardList";
    }

    @GetMapping("/boards/advertisements")
    public String advertisementList() {
        return "";
    }
}
