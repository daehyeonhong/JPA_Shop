package book.shop.api;

import book.shop.domain.Order;
import book.shop.repository.OrderRepository;
import book.shop.repository.order.simplequery.OrderSimpleQueryDto;
import book.shop.repository.order.simplequery.OrderSimpleQueryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import static java.util.stream.Collectors.toList;

/**
 * xToOne(ManyToOne, OneToOne)
 * Order
 * Order -> Member
 * Order -> Delivery
 */
@Slf4j
@RestController
@RequiredArgsConstructor
public class OrderSimpleApiController {
    private final OrderRepository orderRepository;
    private final OrderSimpleQueryRepository orderSimpleQueryRepository;

    @GetMapping(value = "/api/v1/simple-orders")
    public List<Order> ordersV1() {
        List<Order> orders = this.orderRepository.findAll();
        for (Order order : orders)
            log.info("{}, {}", order.getMember().getName(), order.getDelivery().getStatus());
        return orders;
    }

    @GetMapping(value = "/api/v2/simple-orders")
    public List<OrderSimpleQueryDto> orderV2() {
        List<Order> orders = this.orderRepository.findAll();
        return orders.stream()
                .map(order -> new OrderSimpleQueryDto(order.getId(),
                        order.getMember().getName(),
                        order.getOrderDate(),
                        order.getStatus(),
                        order.getDelivery().getAddress()))
                .collect(toList());
    }

    @GetMapping(value = "/api/v3/simple-orders")
    public List<OrderSimpleQueryDto> orderV3() {
        List<Order> orders = this.orderRepository.findWithMemberDelivery();
        return orders.stream()
                .map(order -> new OrderSimpleQueryDto(order.getId(),
                        order.getMember().getName(),
                        order.getOrderDate(),
                        order.getStatus(),
                        order.getDelivery().getAddress()))
                .collect(toList());
    }

    @GetMapping(value = "/api/v4/simple-orders")
    public List<OrderSimpleQueryDto> orderV4() {
        return this.orderSimpleQueryRepository.findOrderDto();
    }

}
