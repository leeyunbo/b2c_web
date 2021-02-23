package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.member.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import java.util.List;

/**
 * @author yunbok
 * 회원 조회 및 등록 레포지토리
 */

@Repository
@RequiredArgsConstructor
public class MemberRepository {

    // Spring Boot가 만들어 놓은 JPA의 엔티티 매니저를 주입하라는 에노테이션 @PersistenceContext
    // 롬북에 의하여 private final로 깔끔하게 대체 가능 (SpringBoot가 @PersistenceContext -> @Autowired로 대체를 해주기 때문에 가능)
    private final EntityManager em;


    // 영속성 컨텍스트에 속한다고 바로 INSERT가 들어가지 않음
    // 후에 커밋이 이루어질 때 INSERT가 나감
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

    public Member findByName(String name) {
        try {
            return em.createQuery("select m from Member m where m.name = :name", Member.class)
                    .setParameter("name", name)
                    .getSingleResult();
        }
        catch (NoResultException e) {
            return null;
        }
    }

}
