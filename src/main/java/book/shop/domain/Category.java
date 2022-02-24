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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = Ids.CATEGORY_ID)
    Long id;
    String name;
    @ManyToMany
    @JoinTable(name = Ids.CATEGORY_ITEM, joinColumns = @JoinColumn(name = Ids.CATEGORY_ID), inverseJoinColumns = @JoinColumn(name = Ids.ITEM_ID))
    List<Item> items = new ArrayList<>();
    @ManyToOne
    @JoinColumn(name = Ids.PARENT_ID)
    Category parent;
    @OneToMany(mappedBy = Ids.PARENT)
    List<Category> child = new ArrayList<>();
}
