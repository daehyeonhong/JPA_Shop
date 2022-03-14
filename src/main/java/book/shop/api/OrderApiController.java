package book.shop.api;

import book.shop.domain.Address;
import book.shop.domain.Order;
import book.shop.domain.OrderItem;
import book.shop.enumerate.OrderStatus;
import book.shop.repository.OrderRepository;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {
    private final OrderRepository orderRepository;

    @GetMapping(value = "/api/v1/orders")
    public List<Order> ordersV1() {
        List<Order> orders = this.orderRepository.findAll();
        for (Order order : orders) {
            order.getMember().getName();
            order.getDelivery().getAddress();
            List<OrderItem> orderItems = order.getOrderItems();
            orderItems.stream().forEach(orderItem -> orderItem.getItem().getName());
        }
        return orders;
    }

    @GetMapping(value = "/api/v2/orders")
    public List<OrderDto> ordersV2() {
        return this.orderRepository.findAll().stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @GetMapping(value = "/api/v3/orders")
    public List<OrderDto> ordersV3() {
        return this.orderRepository.findAllWithItem().stream()
                .map(OrderDto::new)
                .collect(toList());
    }

    @Getter
    @Setter
    static class OrderDto {
        private Long orderId;
        private String name;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;
        private Address address;
        private List<OrderItemDto> orderItems;

        public OrderDto(final Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
            this.orderItems = order.getOrderItems().stream()
                    .map(OrderItemDto::new).collect(toList());
        }
    }

    @Getter
    @Setter
    static class OrderItemDto {
        private String itemName;
        private BigDecimal orderPrice;
        private int count;
        private BigDecimal totalPrice;

        public OrderItemDto(final OrderItem orderItem) {
            this.itemName = orderItem.getItem().getName();
            this.orderPrice = orderItem.getOrderPrice();
            this.count = orderItem.getCount();
            this.totalPrice = orderItem.getTotalPrice();
        }
    }
}
