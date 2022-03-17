package book.shop.domain.item;

import book.shop.domain.Category;
import book.shop.exception.NotEnoughException;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import static javax.persistence.InheritanceType.SINGLE_TABLE;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Inheritance(strategy = SINGLE_TABLE)
@FieldDefaults(level = PRIVATE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @GeneratedValue
    @Column(name = "item_id")
    Long id;
    String name;
    BigDecimal price;
    int stockQuantity;
    @ManyToMany(mappedBy = "items")
    List<Category> categories = new ArrayList<>();

    /**
     * stock 증가
     * @param quantity 재고
     */
    //==Business Logic==//
    public void addStock(int quantity) {
        this.stockQuantity += quantity;
    }

    public void removeStock(int quantity) {
        final int restStock = this.stockQuantity - quantity;
        if (restStock < 0) throw new NotEnoughException("need more Stock!");
        this.stockQuantity = restStock;
    }
}
