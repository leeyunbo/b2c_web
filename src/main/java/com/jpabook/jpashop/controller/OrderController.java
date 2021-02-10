package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.service.ItemService;
import com.jpabook.jpashop.service.MemberService;
import com.jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping("/order")
    public String createForm(Model model) {
        List<Member> members = memberService.findMembers();
        List<Item> items = itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);

        return "order/orderForm";
    }

    // RequestParam -> 선택된 요소의 Value 값이 넘어오게 되면 그걸 저장시킬 수 있음 따옴표 안에는 해당 요소의 name이 들어감
    // Controller에서 객체를 조회하여 전해주면 영속성 컨텍스트가 아니기 때문에 핵심 비즈니스 로직에서 Dirty Checking이 불가능함
    @PostMapping("/order")
    public String order(@RequestParam("memberId") Long memberId,
                        @RequestParam("itemId") Long itemId,
                        @RequestParam("count") int count) {

        orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    // 단순히 조회하는 기능은 그냥 컨트롤러에서 바로 리포지토리에 접근해도 됨 (본인 선택.. 얽매일 필요는 X)
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);
        return "order/orderList";
    }

}
