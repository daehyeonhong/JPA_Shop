package book.shop.domain;

import book.shop.enumerate.DeliveryStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import static book.shop.enumerate.Ids.DELIVERY;
import static book.shop.enumerate.Ids.DELIVERY_ID;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class Delivery {
    @Id
    @GeneratedValue
    @Column(name = DELIVERY_ID)
    Long id;
    @JsonIgnore
    @OneToOne(fetch = LAZY, mappedBy = DELIVERY)
    Order order;
    @Embedded
    Address address;
    @Enumerated(value = STRING)
    DeliveryStatus status;
}
