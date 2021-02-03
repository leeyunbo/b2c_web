package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ItemServiceTest {

    @Autowired
    ItemService itemService;
    @Autowired
    ItemRepository itemRepository;

    @Test
    public void 재고추가() {
        //given면 이게 주어지면
        Book item = new Book();
        item.setName("springBook");
        item.setAuthor("lee yun bok");
        item.setIsbn("#20200078");

        //when 이걸 실행하면
        Long saveId = itemService.saveItem(item);

        // 같은 트랜잭션에서 ID 값이 같으면 같은 영속성 컨텍스트가 되어서 딱 하나로 관리된다.
        // 따라서 True가 나올 수 있다.
        // then 이렇게 된다
        assertEquals(item, itemRepository.findOne(saveId));
    }

    @Test
    public void 재고수정() {
        //given
        Book item = new Book();
        item.setName("springBook");
        item.setAuthor("lee yun bok");
        item.setIsbn("#20200078");

        //when
        int beforeQuantity = item.getStockQuantity();
        item.addStock(32);

        //then
        assertEquals(item.getStockQuantity(), beforeQuantity + 32);
    }


}