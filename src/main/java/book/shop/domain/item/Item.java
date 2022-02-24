package book.shop.domain.item;

import book.shop.domain.Category;
import book.shop.enumerate.Ids;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DiscriminatorColumn(name = Ids.DTYPE)
public abstract class Item {
    @Id
    @Column(name = Ids.ITEM_ID)
    Long id;
    String name;
    BigDecimal price;
    int stockQuantity;
    @ManyToMany(mappedBy = Ids.ITEMS)
    List<Category> categories = new ArrayList<>();
}
