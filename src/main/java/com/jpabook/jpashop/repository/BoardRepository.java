package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.board.Board;
import com.jpabook.jpashop.domain.board.BoardSearch;
import com.jpabook.jpashop.domain.board.Comment;
import com.jpabook.jpashop.domain.order.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class BoardRepository {

    private final EntityManager em;

    public List<Board> findAll(BoardSearch boardSearch) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Board> cq = cb.createQuery(Board.class);
        Root<Board> b = cq.from(Board.class);
        Join<Object, Object> m = b.join("member", JoinType.INNER);

        List<Predicate> criteria = new ArrayList<>();

        // 작성자 이름으로 검색
        if (boardSearch.getMemberName() != null) {
            Predicate name = cb.like(m.<String>get("name"), boardSearch.getMemberName());
            criteria.add(name);
        }
        // 제목으로 검색
        else if(StringUtils.hasText(boardSearch.getSubject())) {
            Predicate subject = cb.like(b.get("subject"), "%" + boardSearch.getSubject() + "%");
            criteria.add(subject);
        }
        // 카테고리로 검색
        else if(boardSearch.getBoardCategory() != null){
            Predicate category = cb.equal(b.get("boardCategory"), boardSearch.getBoardCategory());
            criteria.add(category);
        }

        // 페이지 조건 추가
        cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
        TypedQuery<Board> query = em.createQuery(cq).setFirstResult((boardSearch.getPage()-1) * 10).setMaxResults(10);
        return query.getResultList();
    }

    public List<Board> findAll() {
        return em.createQuery("select b from Board b", Board.class).getResultList();
    }

    public Board findOne(Long boardId) {
        return em.find(Board.class, boardId);
    }

    public void save(Board board) {
        em.persist(board);
    }

}
