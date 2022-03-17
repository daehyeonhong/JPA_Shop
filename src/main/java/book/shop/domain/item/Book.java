package book.shop.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@DiscriminatorValue(value = "b")
public class Book extends Item {
    String author;
    String isbn;
}
