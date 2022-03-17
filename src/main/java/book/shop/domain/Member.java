package book.shop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class Member {
    @Id
    @GeneratedValue
    @Column(name = "member_id")
    Long id;
    //    @Column(unique = true)
    String name;
    @Embedded
    Address address;
    @JsonIgnore
    @OneToMany(mappedBy = "member")
    List<Order> orders = new ArrayList<>();
}
