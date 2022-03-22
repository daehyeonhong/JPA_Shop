package book.shop.repository.order.query;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import javax.persistence.EntityManager;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

@Repository
@RequiredArgsConstructor
public class OrderQueryRepository {
    private final EntityManager entityManager;

    public List<OrderQueryDto> findOrderQueryDtos() {
        final List<OrderQueryDto> orders = this.findOrders();
        orders.forEach(orderQueryDto -> {
            final List<OrderItemQueryDto> orderItems = this.findOrderItems(orderQueryDto.getOrderId());
            orderQueryDto.setOrderItems(orderItems);
        });
        return orders;
    }

    public List<OrderQueryDto> findAllByDtoOptimization() {
        final List<OrderQueryDto> orders = this.findOrders();
        final Map<Long, List<OrderItemQueryDto>> orderItemMap = this.findOrderItemMap(this.toOrderIds(orders));
        orders.forEach(orderQueryDto -> orderQueryDto.setOrderItems(orderItemMap.get(orderQueryDto.getOrderId())));
        return orders;
    }

    public List<OrderQueryDto> findAllByDtoFlat() {
        return this.findOrderItemFlat().stream()
                .collect(groupingBy(OrderQueryDto::new, mapping(OrderItemQueryDto::new, toList())))
                .entrySet().stream().map(OrderQueryDto::new)
                .collect(toList());
    }

    private List<OrderFlatDto> findOrderItemFlat() {
        return this.entityManager.createQuery(
                "select new book.shop.repository.order.query.OrderFlatDto(o.id,m.name,o.orderDate,o.status,d.address,i.name,oi.orderPrice,oi.count)" +
                        " from Order o" +
                        " join o.member m" +
                        " join o.delivery d" +
                        " join o.orderItems oi" +
                        " join oi.item i", OrderFlatDto.class
        ).getResultList();
    }

    private Map<Long, List<OrderItemQueryDto>> findOrderItemMap(final List<Long> orderIds) {
        final List<OrderItemQueryDto> orderItems = this.entityManager.createQuery(
                        "select new book.shop.repository.order.query.OrderItemQueryDto(oi.order.id,oi.item.name,oi.orderPrice,oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id in :orderIds"
                        , OrderItemQueryDto.class
                ).setParameter("orderIds", orderIds)
                .getResultList();
        return orderItems.stream()
                .collect(Collectors.groupingBy(OrderItemQueryDto::getOrderId));
    }

    private List<Long> toOrderIds(final List<OrderQueryDto> orders) {
        return orders.stream()
                .map(OrderQueryDto::getOrderId)
                .collect(toList());
    }

    private List<OrderItemQueryDto> findOrderItems(final Long orderId) {
        return this.entityManager.createQuery(
                        "select new book.shop.repository.order.query.OrderItemQueryDto(oi.order.id,oi.item.name,oi.orderPrice,oi.count)" +
                                " from OrderItem oi" +
                                " join oi.item i" +
                                " where oi.order.id = :orderId", OrderItemQueryDto.class
                ).setParameter("orderId", orderId)
                .getResultList();
    }

    private List<OrderQueryDto> findOrders() {
        return this.entityManager.createQuery(
                        "select new book.shop.repository.order.query.OrderQueryDto(o.id,m.name,o.orderDate,o.status,d.address)" +
                                " from Order o" +
                                " join o.member m" +
                                " join o.delivery d", OrderQueryDto.class)
                .getResultList();
    }
}
