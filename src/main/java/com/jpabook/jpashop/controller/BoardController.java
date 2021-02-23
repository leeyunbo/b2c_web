package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.board.BoardSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardController {

    @GetMapping("/boards/request")
    public String requestList(BoardSearch boardSearch) {

    }

    @GetMapping("/boards/advertisementList")
    public String advertisementList(BoardSearch boardSearch) {

    }
}
