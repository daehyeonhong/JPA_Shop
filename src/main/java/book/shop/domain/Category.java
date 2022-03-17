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
import static javax.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PRIVATE;

@Entity
@Getter
@Setter
@FieldDefaults(level = PRIVATE)
public class Category {
    @Id
    @GeneratedValue
    @Column(name = "category_id")
    Long id;
    String name;
    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name = "category_id"), inverseJoinColumns = @JoinColumn(name = "item_id"))
    List<Item> items = new ArrayList<>();
    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    Category parent;
    @OneToMany(mappedBy = "parent")
    List<Category> child = new ArrayList<>();

    public void addChildCategory(Category child) {
        this.child.add(child);
        child.setParent(this);
    }
}
