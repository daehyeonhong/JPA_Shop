package book.shop.domain;

import book.shop.domain.item.Item;
import book.shop.enumerate.Ids;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderItem {
    @Id
    @GeneratedValue
    @Column(name = Ids.ORDER_ITEM_ID)
    Long id;
    @ManyToOne
    @JoinColumn(name = Ids.ITEM_ID)
    Item item;
    @ManyToOne
    @JoinColumn(name = Ids.ORDER_ID)
    Order order;
    BigDecimal orderPrice; // 주문 가격
    int count; // 주문 수량
}
