package book.shop;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class MemberRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @Transactional
//    @Rollback(value = false)
    @DisplayName(value = "멤버 찾기")
    void testMember() {
        //given
        final Member member = new Member();
        member.setUsername("memberA");

        //when
        final Long savedId = this.memberRepository.save(member);
        final Member findMember = this.memberRepository.find(savedId);

        //then
        assertThat(findMember.getId()).isEqualTo(savedId);
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

}
