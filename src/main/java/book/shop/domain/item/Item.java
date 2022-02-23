package book.shop.domain.item;

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
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@FieldDefaults(level = AccessLevel.PRIVATE)
@DiscriminatorColumn(name = "dtype")
public abstract class Item {
    @Id
    @Column(name = Ids.ITEM_ID)
    Long id;
    String name;
    BigDecimal price;
    int stockQuantity;
}
