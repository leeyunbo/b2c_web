package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.board.BoardSearch;
import com.jpabook.jpashop.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/boards/requests")
    public String requestList() {
        return "";
    }

    @GetMapping("/boards/advertisements")
    public String advertisementList() {
        return "";
    }
}
