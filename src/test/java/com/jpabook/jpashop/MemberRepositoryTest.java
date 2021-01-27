package com.jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;


    @Test
    @Transactional //testcase에 Transactional이 있으면 테스트를 끝나고 롤백해버림(데이터가 사라짐)
    @Rollback(value = false)
    public void testMember() throws Exception {
        //given
        Member member = new Member();
        member.setUsername("memberA");

        //when
        Long saveId = memberRepository.save(member);
        Member findMember = memberRepository.find(saveId);

        //then
        Assertions.assertThat(findMember.getId()).isEqualTo(saveId);
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());

        // 같은 트랜잭션 내에서는 식별자가 같으면 같은 엔티티로 인식한다. 속성 컨텍스트?
        Assertions.assertThat(findMember).isEqualTo(member);
    }

}