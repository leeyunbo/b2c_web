package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // Model : Controller -> view에서 데이터 담을 수 있음
    @GetMapping(value = "/mebers/new")
    public String createForm(Model model) {
        // validation(?)과 같은걸 해줘서 빈껍데기를 가져가야함
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }
}
