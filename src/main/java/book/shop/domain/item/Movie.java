package book.shop.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@DiscriminatorValue("M")
public class Movie extends Item {
    String director;
    String actor;
}
