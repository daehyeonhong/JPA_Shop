package book.shop.api;

import book.shop.domain.Address;
import book.shop.domain.Order;
import book.shop.enumerate.OrderStatus;
import book.shop.repository.OrderRepository;
import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDateTime;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;

    @GetMapping(value = "/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = this.orderRepository.findAll();
        for (Order order : orders) {
            order.getMember().getName();
//            order.getTotalPrice();
            order.getDelivery().getStatus();
        }
        return orders;
    }

    @GetMapping(value = "/api/v2/simple-orders")
    public List<SimpleOrderDto> orderV2() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream()
                .map(SimpleOrderDto::new)
                .collect(toList());
    }

    @Data
    @FieldDefaults(level = AccessLevel.PRIVATE)
    static class SimpleOrderDto {
        Long orderId;
        String name;
        LocalDateTime orderDate;
        OrderStatus orderStatus;
        Address address;

        public SimpleOrderDto(Order order) {
            this.orderId = order.getId();
            this.name = order.getMember().getName();
            this.orderDate = order.getOrderDate();
            this.orderStatus = order.getStatus();
            this.address = order.getDelivery().getAddress();
        }
    }
}
