package book.shop.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class BookForm {
    Long id;
    String name;
    int price;
    int stockQuantity;
    String author;
    String isbn;
}
