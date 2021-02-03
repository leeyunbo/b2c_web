package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;


/**
 * 재고 관련 레포지토리
 */
@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;


    // ID가 없으면 완전히 새로 저장하는 객체이며, ID가 있으면 이미 DB에 저장되어 있어서 수정하는 것
    /**
     * 재고 저장
     * @param item
     */
    public void save(Item item) {
        if (item.getId() == null) {
            em.persist(item);
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() {
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
