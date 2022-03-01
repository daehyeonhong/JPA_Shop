package book.shop.repository;

import book.shop.domain.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class OrderRepository {
    private final EntityManager entityManager;

    public void save(Order order) {
        this.entityManager.persist(order);
    }

    public Order findOne(Long id) {
        return this.entityManager.find(Order.class, id);
    }

}
