package book.shop.api;

import book.shop.domain.Order;
import book.shop.repository.OrderRepository;
import book.shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

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
        final List<Order> orders = this.orderRepository.findAll(new OrderSearch());
        for (final Order order : orders) {
            order.getMember().getName();
//            order.getTotalPrice();
            order.getDelivery().getStatus();
        }
        return orders;
    }
}
