package book.shop.domain;

import book.shop.enumerate.Ids;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = Ids.MEMBER_ID)
    Long id;
    String name;
    @Embedded
    Address address;
    @OneToMany(mappedBy = Ids.MEMBER)
    List<Order> orders = new ArrayList<>();
}
