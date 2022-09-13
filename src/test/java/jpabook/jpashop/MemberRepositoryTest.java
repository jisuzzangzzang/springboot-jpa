package jpabook.jpashop;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // 스프링 테스트하기위해 JUnit에게 알려줌
@SpringBootTest
public class MemberRepositoryTest {

    @Autowired MemberRepository memberRepository;

    @Test
    @Transactional
    @Rollback(false) // DB가 Rollback을 하지 않고 실행됨
    public void testMember() throws Exception {
        // given
           Member member = new Member();
           member.setUsername("memberA");

        // when
        Long saveId = memberRepository.save(member); // memberRepository.save(member)
                                                     // ctrl+alt+v -> 변수 뽑아오기
        Member findMember = memberRepository.find(saveId);

        // then (검증)
        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        Assertions.assertThat(findMember).isEqualTo(member); // true
        System.out.println("findMember == member: " + (findMember == member));
    }
}