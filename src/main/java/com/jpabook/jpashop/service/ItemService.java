package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.ItemRepository;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    //Write 권한 추가
    @Transactional
    public Long saveItem(Item item, Long memberId) {
        Member member = memberRepository.findOne(memberId);
        item.setMember(member);
        itemRepository.save(item);
        return item.getId();
    }

    // 혹은 DTO 생성해서 진행
    @Transactional
    public void updateItem(Long itemId, String name, int price, int stockQuantity, String author, String isbn) {
        Book findItem = (Book) itemRepository.findOne(itemId);
        findItem.change(name, price, stockQuantity, author, isbn);
    }

    public List<Item> findItems() {
        return itemRepository.findAll();
    }

    public Item findOne(Long itemId) {
        return itemRepository.findOne(itemId);
    }
}
