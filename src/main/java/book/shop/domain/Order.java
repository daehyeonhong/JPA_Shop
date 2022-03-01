package book.shop.domain;

import book.shop.enumerate.DeliveryStatus;
import book.shop.enumerate.OrderStatus;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import static book.shop.enumerate.Ids.DELIVERY_ID;
import static book.shop.enumerate.Ids.MEMBER_ID;
import static book.shop.enumerate.Ids.ORDER;
import static book.shop.enumerate.Ids.ORDERS;
import static book.shop.enumerate.Ids.ORDER_ID;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@Table(name = ORDERS)
@FieldDefaults(level = PRIVATE)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = ORDER_ID)
    Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = MEMBER_ID)
    Member member;
    @OneToMany(mappedBy = ORDER, cascade = ALL)
    List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = DELIVERY_ID)
    Delivery delivery;
    LocalDateTime orderDate;
    @Enumerated(value = STRING)
    OrderStatus status;

    //==연관관계 메서드==//
    public void setMember(Member member) {
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this);
    }

    //==Construct Method==//
    public static Order createOrder(final Member member, final Delivery delivery, final OrderItem... orderItems) {
        final Order order = new Order();
        order.setMember(member);
        order.setDelivery(delivery);
        for (final OrderItem orderItem : orderItems) order.addOrderItem(orderItem);
        order.setStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
        return order;
    }

    //==주문 취소 Method==//
    public void cancel() {
        if (this.delivery.getStatus() == DeliveryStatus.COMP)
            throw new IllegalStateException("이미 배송완료된 상품은 취소가 불가능합니다.");
        this.setStatus(OrderStatus.CANCEL);
        for (final OrderItem orderItem : this.orderItems) {
            orderItem.cancel();
        }
    }

    //==조회 Logic==//

    /**
     * 전체 주문 가격 조회
     * @return 전체 주문 가격
     */
    public BigDecimal getTotalPrice() {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (OrderItem orderItem : this.orderItems) totalPrice = totalPrice.add(orderItem.getTotalPrice());
        return totalPrice;
    }
}
