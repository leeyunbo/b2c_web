package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    /**
     * 주문 하기
     * CASCADE 옵션 덕분에 orderRepository.save()만 해주면 됨
     * CASCADE는 특정 엔티티를 어떤 엔티티가 유일하게 관리할 경우에만 사용해라.
     * 복수의 엔티티에서 관리하는 경우 CASCADE를 사용하지 마라. 별도의 레포지토리를 생성해라.
     */
    @Transactional
    public Long order(Long memberId, Long itemId, int count) {
        // 멤버, 품목 엔티티 조회
        Member member = memberRepository.findOne(memberId);
        Item item = itemRepository.findOne(itemId);

        // 배송 정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        // 주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        // 주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        // 주문 저장
        orderRepository.save(order);

        return order.getId();
    }

    /**
     * 주문 취소
     */
    @Transactional
    public void cancelOrder(Long orderId) {
        // 주문 조회
        Order order = orderRepository.findOne(orderId);

        // 주문 취소
        order.cancel();
    }

    /**
     * 주문 업데이트
     */
    @Transactional
    public void updateOrder(Long orderId, List<OrderItem> orderItems) {
        // 주문 조회
        Order order = orderRepository.findOne(orderId);
        order.change(orderItems);
    }

    /**
     * 주문 가져오기
     * @param orderId
     * @return
     */
    @Transactional
    public Order getOrder(Long orderId) {
        return orderRepository.findOne(orderId);
    }

    /**
     * 검색
     * @param orderSearch
     * @return
     */
    public List<Order> findOrders(OrderSearch orderSearch) {
        return orderRepository.findAll(orderSearch);
    }

}
