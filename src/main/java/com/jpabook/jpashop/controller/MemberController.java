package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // Model : Controller -> view에서 데이터 담을 수 있음
    @GetMapping(value = "/members/new")
    public String createForm(Model model) {
        // validation(?)과 같은걸 해줘서 빈껍데기를 가져가야함
        log.info("member controller");
        model.addAttribute("memberForm", new MemberForm());
        return "members/createMemberForm";
    }

    // MemberForm을 쓰는 이유? => 단순히 멤버 엔티티를 넘겨주게 되면 맞쳐줘야 하는 쓸데 없는 코드가 많아져 더러워짐
    @PostMapping("/members/new")
    public String create(@Valid MemberForm form, BindingResult result) {

        // 자동으로 result(에러 관련 클래스)를 끌고감
        // 끌고가면 해당 페이지에서 오류 관련 태그를 거치게 됨
        if (result.hasErrors()) {
            return "members/createMemberForm";
        }

        Address address = new Address(form.getCity(), form.getStreet(), form.getZipcode());

        Member member = new Member();
        member.setName(form.getName());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model) {
        // command + option + n
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }
}
