package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HomeController {

    private final MemberService memberService;


    @GetMapping("/home")
    public String home(Model model) {
        return "home";
    }

    @GetMapping("/")
    public String loginForm(Model model) {
        model.addAttribute("form", new MemberLoginForm());
        return "loginMember";
    }

    @PostMapping("/")
    public String login(@ModelAttribute("form") MemberLoginForm form) {
        Member findMember = memberService.findOne(form.getName());
        if(findMember == null) {
            return "loginMember";
        }
        else {
            if(findMember.loginCheck(form.getPassword())) {
                return "home";
            }
            else {
                return "loginMember";
            }
        }
    }
}
