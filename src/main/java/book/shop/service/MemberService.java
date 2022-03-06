package book.shop.service;

import book.shop.domain.Address;
import book.shop.domain.Member;
import book.shop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    /**
     * 회원 가입
     * @param member 가입 회원 정보
     * @return member.Id
     */
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        this.memberRepository.save(member);
        return member.getId();
    }

    /**
     * 중복 회원 검증
     * @param member 조회 회원 정보
     */
    private void validateDuplicateMember(final Member member) {
        final List<Member> memberList = memberRepository.findByName(member.getName());
        if (!memberList.isEmpty()) throw new IllegalStateException("이미 존재하는 회원입니다.");
    }

    /**
     * 회원 전체 조회
     * @return 전체 회원 리스트
     */
    public List<Member> findMembers() {
        return this.memberRepository.findAll();
    }

    public Member findOne(Long id) {
        return this.memberRepository.fineOne(id);
    }

    @Transactional
    public void update(final Long id, final String name, final Address address) {
        final Member member = this.memberRepository.fineOne(id);
        member.setName(name);
        if (address != null) member.setAddress(address);
    }
}
