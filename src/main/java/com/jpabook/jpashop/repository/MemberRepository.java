package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * @author yunbok
 * 회원 조회 및 등록 레포지토리
 */

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // Spring Boot가 만들어 놓은 JPA의 엔티티 매니저를 주입하라는 에노테이션 @PersistenceContext
    // 롬북에 의하여 private final로 깔끔하게 대체 가능
    private final EntityManager em;

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id);
    }

    public List<Member> findAll() {
       // JPQL은 엔티티에 대하여 쿼리문을 날리는 것, Member m으로 치환해서 m을 select하라는 의미
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }

}
