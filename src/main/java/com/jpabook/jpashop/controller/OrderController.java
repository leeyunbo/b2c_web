package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.service.ItemService;
import com.jpabook.jpashop.service.MemberService;
import com.jpabook.jpashop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.aspectj.weaver.ast.Or;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
        List<Item> items = itemService.findItems(null);

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
    // ModelAttribute -> Model에 자동으로 담김
    @GetMapping("/orders")
    public String orderList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model) {
        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);
        return "order/orderList";
    }

    @GetMapping("/orders/me")
    public String orderMyList(@ModelAttribute("orderSearch")OrderSearch orderSearch, Model model, HttpServletRequest httpServletRequest) {
        // 세션 정보 불러오기
        HttpSession session = httpServletRequest.getSession();
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");
        orderSearch.setMemberName(memberInfo.getName());

        List<Order> orders = orderService.findOrders(orderSearch);

        model.addAttribute("orders", orders);
        return "order/orderMyList";
    }

    @PostMapping("/orders/{orderId}/cancel")
    public String cancelOrder(@PathVariable("orderId") Long orderId) {
        orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }

    @GetMapping("/orders/{orderId}/edit")
    public String orderUpdateForm(@PathVariable("orderId") Long orderId, Model model) {
        OrderForm form = new OrderForm();
        Order order = orderService.getOrder(orderId);
        List<OrderItem> orders = order.getOrderItems();

        form.setId(orderId);
        form.setStatus(order.getStatus());
        form.setCount(orders.get(0).getCount());
        form.setName(orders.get(0).getItem().getName());

        model.addAttribute("form", form);
        return "order/updateOrderForm";
    }

    @PostMapping("/orders/{orderId}/edit")
    public String orderUpdate(@PathVariable("orderId") Long orderId, @ModelAttribute("form") OrderForm form) {
        orderService.updateOrder(orderId, form.getCount());
        return "redirect:/orders";
    }


}
