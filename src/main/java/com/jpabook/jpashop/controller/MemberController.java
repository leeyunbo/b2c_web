package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.MemberInfo;
import com.jpabook.jpashop.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.annotation.Resource;
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
        member.setPassword(form.getPassword());
        member.setAddress(address);

        memberService.join(member);
        return "redirect:/";
    }

    // 이것도 DTO or MemberForm을 활용하는 것이 좋지만, 단순하기 때문에 그냥 엔티티 리스트를 뿌려줌
    // API를 만들때는 절대 엔티티를 외부로 반환하면 안됨, 엔티티를 변경되면 API 스펙도 바뀌게 됨 ( 연결된 시스템들이 고통 받음 )
    @GetMapping("/members")
    public String list(Model model) {
        // command + option + n
        model.addAttribute("members", memberService.findMembers());
        return "members/memberList";
    }

    @GetMapping("members/{memberId}/edit")
    public String updateMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        Member member = memberService.findOne(memberId);

        MemberForm form = new MemberForm();
        Address address = member.getAddress();

        form.setId(member.getId());
        form.setName(member.getName());
        form.setCity(address.getCity());
        form.setStreet(address.getStreet());
        form.setZipcode(address.getZipcode());
        form.setGrade(member.getGrade());
        form.setMemberAuthority(member.getMemberAuthority());

        model.addAttribute("form", form);
        return "members/updateMemberForm";
    }

    @PostMapping("members/{memberId}/edit")
    public String updateMember(@PathVariable Long memberId, @ModelAttribute("form") MemberForm form) {
        // 해당 상품에 대한 권한이 있는지 체크하는 로직이 있으면 보안상 좋음
        memberService.updateMember(memberId, form.getName(), form.getCity(), form.getStreet(), form.getZipcode(), form.getGrade(), form.getMemberAuthority());
        return "redirect:/members";
    }

}
