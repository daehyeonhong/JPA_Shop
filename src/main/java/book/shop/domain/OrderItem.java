package book.shop.domain;

import book.shop.domain.item.Item;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import static book.shop.enumerate.Ids.ITEM_ID;
import static book.shop.enumerate.Ids.ORDER_ID;
import static book.shop.enumerate.Ids.ORDER_ITEM_ID;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@NoArgsConstructor(access = PROTECTED)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = ORDER_ITEM_ID)
    Long id;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = ITEM_ID)
    Item item;
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = ORDER_ID)
    Order order;
    BigDecimal orderPrice; // 주문 가격
    int count; // 주문 수량

    //==Construct Method==//
    public static OrderItem createOrderItem(final Item item, BigDecimal orderPrice, int count) {
        final OrderItem orderItem = new OrderItem();
        orderItem.setItem(item);
        orderItem.setOrderPrice(orderPrice);
        orderItem.setCount(count);

        item.removeStock(count);
        return orderItem;
    }

    //==Business Logic==//
    public void cancel() {
        this.getItem().addStock(count);
    }

    //==조회 Logic==//
    public BigDecimal getTotalPrice() {
        return this.getOrderPrice().multiply(BigDecimal.valueOf(this.count));
    }
}
