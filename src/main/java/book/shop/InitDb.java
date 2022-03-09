package book.shop;

import book.shop.domain.Address;
import book.shop.domain.Delivery;
import book.shop.domain.Member;
import book.shop.domain.Order;
import book.shop.domain.OrderItem;
import book.shop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

/**
 * 총 주문 두 건
 * * userA
 * * * JPA1 BOOK
 * * * JPA2 BOOK
 * * userB
 * * * SPRING1 BOOK
 * * * SPRING2 BOOK
 */
@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        this.initService.firstDbInit();
        this.initService.secondDbInit();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {
        private final EntityManager entityManager;

        public void firstDbInit() {
            Member member = createMember("userA", "Seoul", "13", "91011");
            this.entityManager.persist(member);

            Book book1 = createBook("JPA1 BOOK", 10000, 100);
            this.entityManager.persist(book1);
            Book book2 = createBook("JPA2 BOOK", 20000, 100);
            this.entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            this.entityManager.persist(order);
        }

        public void secondDbInit() {
            Member member = createMember("userB", "Tokyo", "1", "12421");
            this.entityManager.persist(member);

            Book book1 = createBook("SPRING1 BOOK", 20000, 300);
            this.entityManager.persist(book1);
            Book book2 = createBook("SPRING2 BOOK", 40000, 200);
            this.entityManager.persist(book2);

            OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 4);
            OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 3);

            Delivery delivery = createDelivery(member);
            Order order = Order.createOrder(member, delivery, orderItem1, orderItem2);
            this.entityManager.persist(order);
        }

        private Delivery createDelivery(Member member) {
            Delivery delivery = new Delivery();
            delivery.setAddress(member.getAddress());
            return delivery;
        }

        private Book createBook(final String name, final int price, final int stockQuantity) {
            Book book = new Book();
            book.setName(name);
            book.setPrice(price);
            book.setStockQuantity(stockQuantity);
            return book;
        }

        private Member createMember(final String name, final String city, final String street, final String zipcode) {
            Member member = new Member();
            member.setName(name);
            member.setAddress(new Address(city, street, zipcode));
            return member;
        }
    }
}
