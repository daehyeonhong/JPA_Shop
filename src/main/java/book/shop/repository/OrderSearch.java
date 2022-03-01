package book.shop.repository;

import book.shop.enumerate.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class OrderSearch {
    String memberName;
    OrderStatus orderStatus;
}
