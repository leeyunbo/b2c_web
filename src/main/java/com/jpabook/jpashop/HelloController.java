package com.jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    // Model : 데이터를 실어서 뷰에게 넘길 수 있음
    // return "hello" : hello라는 화면으로 이동 자동으로 html이 붙음
    // templates -> 서버 렌더링
    // static -> 정적 페이지
    @GetMapping("hello")
    public String hello(Model model) {
        model.addAttribute("data", "hello");
        return "hello";
    }
}
