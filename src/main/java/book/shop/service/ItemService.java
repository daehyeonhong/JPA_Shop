package book.shop.service;

import book.shop.domain.item.Item;
import book.shop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
    private final ItemRepository itemRepository;

    @Transactional
    public void saveItem(Item item) {
        this.itemRepository.save(item);
    }

    public void updateItem(final Long id, Item item) {
        Item findItem = this.itemRepository.findOne(id);
        findItem.setPrice(item.getPrice());
        findItem.setName(item.getName());
        findItem.setStockQuantity(item.getStockQuantity());
    }

    public List<Item> findItems() {
        return this.itemRepository.findAll();
    }

    public Item findOne(final Long itemId) {
        return this.itemRepository.findOne(itemId);
    }
}
