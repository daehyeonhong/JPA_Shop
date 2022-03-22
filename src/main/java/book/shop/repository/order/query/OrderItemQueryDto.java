package book.shop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class OrderItemQueryDto {
    @JsonIgnore
    Long orderId;
    String itemName;
    BigDecimal orderPrice;
    int count;

    public OrderItemQueryDto(final Long orderId, final String itemName, final BigDecimal orderPrice, final int count) {
        this.orderId = orderId;
        this.itemName = itemName;
        this.orderPrice = orderPrice;
        this.count = count;
    }

    public OrderItemQueryDto(final OrderFlatDto orderFlatDto) {
        this.orderId = orderFlatDto.getOrderId();
        this.itemName = orderFlatDto.getItemName();
        this.orderPrice = orderFlatDto.getOrderPrice();
        this.count = orderFlatDto.getCount();
    }
}
