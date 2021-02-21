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

    public List<Item> findAll(String memberName) {
        if(memberName == null) {
            return em.createQuery("select i from Item i", Item.class)
                    .getResultList();
        }
        else {
            return em.createQuery("select i from Item i join i.member m" +
                    " where m.name like :name", Item.class)
                    .setParameter("name", memberName)
                    .getResultList();
        }
    }


    //== 검색 로직 ==//
//    public List<Order> findAll(OrderSearch orderSearch) {
//        return em.createQuery("select o from Order o join o.member m" +
//                " where o.status = :status" +
//                " and m.name like :name", Order.class)
//                .setParameter("status", orderSearch.getOrderStatus())
//                .setParameter("name", orderSearch.getMemberName())
//                //.setFirstResult(100) 100부터 가져온다는 뜻
//                .setMaxResults(1000) // 최대 100건
//                .getResultList();
//
//    }
}
