package book.shop.domain.item;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import static book.shop.enumerate.Ids.ALBUM;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
@DiscriminatorValue(value = ALBUM)
public class Album extends Item {
    String artist;
    String etc;
}
