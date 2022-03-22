package book.shop.repository.order.simplequery;

import book.shop.domain.Address;
import book.shop.enumerate.OrderStatus;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import static lombok.AccessLevel.PRIVATE;

@Data
@FieldDefaults(level = PRIVATE)
public class OrderSimpleQueryDto {
    Long orderId;
    String name;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    Address address;

    public OrderSimpleQueryDto(final Long orderId, final String name, final LocalDateTime orderDate, final OrderStatus orderStatus, final Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }
}
