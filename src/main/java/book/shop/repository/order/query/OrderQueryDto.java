package book.shop.repository.order.query;

import book.shop.domain.Address;
import book.shop.enumerate.OrderStatus;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map.Entry;
import static lombok.AccessLevel.PRIVATE;

@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@EqualsAndHashCode(of = "orderId")
public class OrderQueryDto {
    Long orderId;
    String name;
    LocalDateTime orderDate;
    OrderStatus orderStatus;
    Address address;
    List<OrderItemQueryDto> orderItems;

    public OrderQueryDto(final Long orderId, final String name, final LocalDateTime orderDate, final OrderStatus orderStatus, final Address address) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
    }

    public OrderQueryDto(final Long orderId, final String name, final LocalDateTime orderDate, final OrderStatus orderStatus, final Address address, final List<OrderItemQueryDto> orderItems) {
        this.orderId = orderId;
        this.name = name;
        this.orderDate = orderDate;
        this.orderStatus = orderStatus;
        this.address = address;
        this.orderItems = orderItems;
    }

    public OrderQueryDto(final OrderFlatDto orderFlatDto) {
        this.orderId = orderFlatDto.getOrderId();
        this.name = orderFlatDto.getName();
        this.orderDate = orderFlatDto.getOrderDate();
        this.orderStatus = orderFlatDto.getOrderStatus();
        this.address = orderFlatDto.getAddress();
    }

    public OrderQueryDto(final Entry<OrderQueryDto, List<OrderItemQueryDto>> orderQueryDtoListEntry) {
        this.orderId = orderQueryDtoListEntry.getKey().getOrderId();
        this.name = orderQueryDtoListEntry.getKey().getName();
        this.orderDate = orderQueryDtoListEntry.getKey().getOrderDate();
        this.orderStatus = orderQueryDtoListEntry.getKey().getOrderStatus();
        this.address = orderQueryDtoListEntry.getKey().getAddress();
        this.orderItems = orderQueryDtoListEntry.getValue();
    }
}
