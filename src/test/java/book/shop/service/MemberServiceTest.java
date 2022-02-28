package book.shop.service;

import book.shop.domain.Member;
import book.shop.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Transactional
@SpringBootTest
class MemberServiceTest {
    @Autowired
    private MemberService memberService;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private EntityManager entityManager;

    @Test
    @DisplayName(value = "회원가입")
    void join() {
        //given
        Member member = new Member();
        member.setName("H");
        //when
        final Long savedId = this.memberService.join(member);
        //then
        entityManager.flush();
        assertEquals(member, this.memberService.findOne(savedId));
    }

    @Test
    @DisplayName(value = "이름 중복 검사")
    void validateDuplicateName() {
        //given
        Member firstMember = new Member();
        firstMember.setName("H");
        Member secondMember = new Member();
        secondMember.setName("H");
        //when
        this.memberService.join(firstMember);
        //then
        assertThrows(IllegalStateException.class, () -> this.memberService.join(secondMember));
    }
}
