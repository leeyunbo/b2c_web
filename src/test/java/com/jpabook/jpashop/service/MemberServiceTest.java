package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.repository.MemberRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

// Transactional -> 테스트 케이스에 있으면 기본으로 전략이 롤백임!
// 롤백 전략이면 굳이 영속성 컨텍스트에 대하여 INSERT를 할 필요가 없음. ( 콘솔에 INSERT가 안찍히는 이유 )

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired MemberService memberService;
    @Autowired MemberRepository memberRepository;

    @Test
    @Rollback(false)
    public void 회원기입() throws Exception {
        //given면 이게 주어지면
        Member member = new Member();
        member.setName("lee yun bok");

        //when 이걸 실행하면
        Long saveId = memberService.join(member);

        // 같은 트랜잭션에서 ID 값이 같으면 같은 영속성 컨텍스트가 되어서 딱 하나로 관리된다.
        // 따라서 True가 나올 수 있다.
        // then 이렇게 된다
        assertEquals(member, memberRepository.findOne(saveId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given

        //when

        //then
    }

}