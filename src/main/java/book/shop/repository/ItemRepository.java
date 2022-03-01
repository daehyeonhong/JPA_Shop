package book.shop.repository;

import book.shop.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {
    private final EntityManager entityManager;

    public void save(final Item item) {
        if (item.getId() == null) this.entityManager.persist(item);
        else this.entityManager.merge(item);
    }

    public Item findOne(final Long id) {
        return this.entityManager.find(Item.class, id);
    }

    public List<Item> findAll() {
        return this.entityManager
                .createQuery("select i from Item i", Item.class)
                .getResultList();
    }
}
