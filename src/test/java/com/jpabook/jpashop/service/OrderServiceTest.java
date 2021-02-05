package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import com.jpabook.jpashop.repository.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    EntityManager em;
    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void 상품주문() throws Exception {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("시골_JPA", 10000, 10);

        // when
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // then
        Order getOrder = orderRepository.findOne(orderId);

        // 처음 주문 했을 때, ORDER 상태인지
        assertEquals( OrderStatus.ORDER, getOrder.getStatus());
        // 주문한 아이템의 종류의 수가 1개인지
        assertEquals(1, getOrder.getOrderItems().size());
        // 총 가격이 가격 * 주문 수량인지
        assertEquals(10000 * orderCount, getOrder.getTotalPrice());
        // 주문한 만큼 Item의 수량이 줄어들었는지
        assertEquals(10 - orderCount, book.getStockQuantity());
    }

    @Test
    public void 주문취소() throws Exception {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("시골_JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        // when
        orderService.cancelOrder(orderId);

        // then
        Order getOrder = orderRepository.findOne(orderId);
        // 취소 상태인지 확인
        assertEquals(OrderStatus.CANCEL, getOrder.getStatus());
        // 취소한 만큼 Item의 수량이 증가했는지
        assertEquals(10, book.getStockQuantity());

    }

    @Test
    public void 상품주문_재고수량초과() throws Exception {
        // given
        Member member = createMember("회원1", new Address("서울", "강가", "123-123"));
        Item book = createBook("시골_JPA", 10000, 10);

        int orderCount = 11;
        // when
        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        }
        catch(NotEnoughStockException e) {
            return;
        }

        // then
        fail("재고 수량 부족 예외가 발생해야 한다.");
    }

    private Item createBook(String name, int orderPrice, int orderCount) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(orderPrice);
        book.setStockQuantity(orderCount);
        em.persist(book);
        return book;
    }

    private Member createMember(String name, Address address) {
        Member member = new Member();
        member.setName(name);
        member.setAddress(address);
        em.persist(member);
        return member;
    }

}