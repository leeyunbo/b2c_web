package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.board.Board;
import com.jpabook.jpashop.domain.board.BoardSearch;
import com.jpabook.jpashop.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

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
