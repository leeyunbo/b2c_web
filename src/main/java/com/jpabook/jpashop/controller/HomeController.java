package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberService memberService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("loginMemberForm", new MemberLoginForm());
        return "home";
    }

    @PostMapping("/login")
    public String login(@Valid MemberLoginForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "loginMember";
        }

        Member findMember = memberService.findOne(form.getName());
        if(findMember == null) {
            return "loginMember";
        }
        else {
            return "home";
        }
    }
}
