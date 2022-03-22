package book.shop.repository.order.query;

import book.shop.domain.Address;
import book.shop.enumerate.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class OrderFlatDto {
    Long orderId;
    String name;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    Address address;

    String ItemName;
    BigDecimal orderPrice;
    int count;

    public OrderFlatDto(final Long orderId, final String name, final LocalDateTime orderDate, final OrderStatus orderStatus, final Address address, final String itemName, final BigDecimal orderPrice, final int count) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.ItemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }
}
