package book.shop.domain;

import book.shop.enumerate.DeliveryStatus;
import book.shop.enumerate.Ids;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = Ids.DELIVERY_ID)
    Long id;
    @OneToOne
    Order order;
    @Embedded
    Address address;
    @Enumerated(EnumType.STRING)
    DeliveryStatus status;
}
