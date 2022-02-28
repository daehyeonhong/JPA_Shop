package book.shop.domain;

import book.shop.domain.item.Item;
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
import static book.shop.enumerate.Ids.CATEGORY_ID;
import static book.shop.enumerate.Ids.CATEGORY_ITEM;
import static book.shop.enumerate.Ids.ITEM_ID;
import static book.shop.enumerate.Ids.PARENT;
import static book.shop.enumerate.Ids.PARENT_ID;
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = CATEGORY_ID)
    Long id;
    String name;
    @ManyToMany
    @JoinTable(name = CATEGORY_ITEM, joinColumns = @JoinColumn(name = CATEGORY_ID), inverseJoinColumns = @JoinColumn(name = ITEM_ID))
    List<Item> items = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = PARENT_ID)
    Category parent;
    @OneToMany(mappedBy = PARENT)
    List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
