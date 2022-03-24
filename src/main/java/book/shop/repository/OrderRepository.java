package book.shop.repository;

import book.shop.domain.Order;
import book.shop.enumerate.OrderStatus;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import static book.shop.domain.QMember.member;
import static book.shop.domain.QOrder.order;
import static javax.persistence.criteria.JoinType.INNER;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager entityManager;

    public void save(Order order) {
        this.entityManager.persist(order);
    }

    public Order findOne(final Long id) {
        return this.entityManager.find(Order.class, id);
    }

    public List<Order> findAll() {
        return this.entityManager.createQuery("select o from Order o", Order.class)
                .getResultList();
    }

    public List<Order> findAll(final OrderSearch orderSearch) {
        final JPAQueryFactory query = new JPAQueryFactory(this.entityManager);
        return query
                .select(order)
                .from(order)
                .join(order.member, member)
                .where(this.statusEqual(orderSearch.getOrderStatus()), this.nameLike(orderSearch.getMemberName()))
                .limit(1000)
                .fetch();
    }

    /**
     * StringBuild
     * @return 검색 리스트
     */
    public List<Order> findAllByString(final OrderSearch orderSearch) {
        String jpql = "select o from Order o join o.member m";
        boolean isFirstCondition = true;
        if (orderSearch.getOrderStatus() != null) {
            jpql += " where";
            isFirstCondition = false;
            jpql += " o.status = :status";
        }

        if (StringUtils.hasText(orderSearch.getMemberName())) {
            if (isFirstCondition) jpql += " where";
            else jpql += " and";
            jpql += " m.name like :name";
        }

        TypedQuery<Order> query = entityManager.createQuery(jpql, Order.class).setMaxResults(1000);

        if (orderSearch.getOrderStatus() != null)
            query = query.setParameter("status", orderSearch.getOrderStatus());
        if (StringUtils.hasText(orderSearch.getMemberName()))
            query = query.setParameter("name", orderSearch.getMemberName());

        return query.getResultList();
    }

    /**
     * JPA Criteria
     * @param orderSearch 주문 검색 조건
     * @return 검색 리스트
     */
    public List<Order> findAllByCriteria(final OrderSearch orderSearch) {
        final CriteriaBuilder criteriaBuilder = this.entityManager.getCriteriaBuilder();
        final CriteriaQuery<Order> criteriaQuery = criteriaBuilder.createQuery(Order.class);
        final Root<Order> root = criteriaQuery.from(Order.class);
        final Join<Object, Object> member = root.join("member", INNER);

        List<Predicate> criteria = new ArrayList<>();

        if (orderSearch.getOrderStatus() != null)
            criteria.add(criteriaBuilder.equal(root.get("status"), orderSearch.getOrderStatus()));

        if (StringUtils.hasText(orderSearch.getMemberName()))
            criteria.add(criteriaBuilder.like(member.get("name"), "%" + orderSearch.getMemberName() + "%"));

        final int criteriaSize = criteria.size();
        criteriaQuery.where(criteriaBuilder.and(criteria.toArray(new Predicate[criteriaSize])));
        final TypedQuery<Order> query = this.entityManager.createQuery(criteriaQuery).setMaxResults(1000);
        return query.getResultList();
    }

    public List<Order> findWithMemberDelivery() {
        return this.entityManager.createQuery(
                "select o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d",
                Order.class).getResultList();
    }

    public List<Order> findAllWithItem() {
        return this.entityManager.createQuery(
                "select distinct o from Order o" +
                        " join fetch o.member m" +
                        " join fetch o.delivery d" +
                        " join fetch o.orderItems oi" +
                        " join fetch oi.item i", Order.class
        ).getResultList();
    }

    public List<Order> findAllWithMemberDelivery(final int offset, final int limit) {
        return this.entityManager.createQuery(
                        "select o from Order o"
                        , Order.class
                ).setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();
    }

    private BooleanExpression statusEqual(final OrderStatus orderStatusCondition) {
        if (orderStatusCondition == null) return null;
        return order.status.eq(orderStatusCondition);
    }

    private BooleanExpression nameLike(final String memberName) {
        if (!StringUtils.hasText(memberName)) return null;
        return member.name.like(memberName);
    }
}
