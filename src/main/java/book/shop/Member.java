package book.shop;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    Long id;
    String username;
}
