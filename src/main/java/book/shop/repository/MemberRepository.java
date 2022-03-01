package book.shop.repository;

import book.shop.domain.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MemberRepository {
    private final EntityManager entityManager;

    public void save(final Member member) {
        this.entityManager
                .persist(member);
    }

    public Member fineOne(final Long id) {
        return this.entityManager
                .find(Member.class, id);
    }

    public List<Member> findAll() {
        return this.entityManager
                .createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(final String name) {
        return this.entityManager
                .createQuery("select m from Member m where m.name = :name", Member.class)
                .setParameter("name", name)
                .getResultList();
    }
}
