package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.Member;
import com.jpabook.jpashop.domain.MemberAuthority;
import com.jpabook.jpashop.domain.MemberGrade;
import com.jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author yunbok
 * 회원 조회 및 등록 서비스
 */


//JPA 관련 로직은 무조건 트랜잭션 내에서 수행되어야함
@Service
@Transactional

// 롬복에 의하여 private final 필드에 대해 자동 생성자 주입
@RequiredArgsConstructor
public class MemberService {


    private final MemberRepository memberRepository;

    // 요즘은 생성자 인젝션이 권장된다.
    // memberRepository가 실행 중간에 바뀔 일이 없다. (final)
    // 테스트 케이스에서 memberRepository를 직접 생성할 때 생성자 파라미터에 의해 수동 주입을 놓칠 일이 없다.
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    /**
     * 회원가
     * @param member : 회원 엔티티
     * @return : 회원 번호
     */
    public Long join(Member member) {
        // 중복 회원 검증
        validateDuplicateMember(member);
        memberRepository.save(member);

        // DB에 저장 되기전에 ID가 저장되어있는 것은 무조건 보장됨 (generatedValue)
        return member.getId();
    }

    /**
     * 중복 회원 검증
     * @param member : 회원 엔티티
     */
    // 물론 복수의 WAS를 통해 접근하여 동시에 같은 계정을 생성하는 경우가 발생하면 이 로직이 틀어질 수도 있다.
    // 그런 경우를 대비하여 디비 필드에 UNIQUE 제약 조건을 걸어주는 것이 권장된다.
    private void validateDuplicateMember(Member member) {
        Member findMembers = memberRepository.findByName(member.getName());
        if (findMembers != null) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /**
     * 회원 전체 조회
     * @return : 회원 엔티티 리스트
     */
    // readOnly 추가하면 데이터를 단순히 읽어올 때 성능 최적화가 이루어짐
    @Transactional(readOnly = true)
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    /**
     * 단일 회원 조회
     * @param memberId : 회원 엔티티
     * @return : 회원 엔티티
     */
    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    /**
     * 로그인을 위한 회원 조회
     * @param name 회원 이름
     * @return
     */
    @Transactional(readOnly = true)
    public Member findOne(String name) {
        return memberRepository.findByName(name);
    }

    /**
     * 회원 정보 수정
     * @param memberId
     * @param name
     * @param city
     * @param street
     * @param zipcode
     * @param grade
     */
    @Transactional
    public void updateMember(Long memberId, String name, String city, String street, String zipcode, MemberGrade grade, MemberAuthority memberAuthority) {
        Member findItem = memberRepository.findOne(memberId);
        findItem.change(memberId, name, city, street, zipcode, grade, memberAuthority);
    }
}
