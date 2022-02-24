package book.shop.domain;

import book.shop.enumerate.Ids;
import book.shop.enumerate.OrderStatus;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = Ids.ORDERS)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Order {
    @Id
    @GeneratedValue
    @Column(name = Ids.ORDER_ID)
    Long id;
    @ManyToOne
    @JoinColumn(name = Ids.MEMBER_ID)
    Member member;
    @OneToMany(mappedBy = Ids.ORDER)
    List<OrderItem> orderItems = new ArrayList<>();
    @OneToOne
    @JoinColumn(name = Ids.DELIVERY_ID)
    Delivery delivery;
    LocalDateTime orderDate;
    @Enumerated(EnumType.STRING)
    OrderStatus status;
}
