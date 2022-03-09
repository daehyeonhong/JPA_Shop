package book.shop.service;

import book.shop.domain.Address;
import book.shop.domain.Member;
import book.shop.domain.Order;
import book.shop.domain.item.Book;
import book.shop.domain.item.Item;
import book.shop.enumerate.OrderStatus;
import book.shop.exception.NotEnoughException;
import book.shop.repository.OrderRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Transactional
class OrderServiceTests {
    @Autowired
    private EntityManager entityManager;
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName(value = "상품주문")
    void order() {
        //given
         Member member = this.createMember();

        Item book = this.createItem("MobyDick", 11000, 10);

        final int orderCount = 2;

        //when
        final Long orderId = this.orderService.order(member.getId(), book.getId(), orderCount);

        //then
        Order getOrder = this.orderRepository.findOne(orderId);

        assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        assertEquals(1, getOrder.getOrderItems().size(), "주문한 상품 종류 수가 정확해야 한다.");
        assertEquals(10000 * orderCount
                , getOrder.getTotalPrice()
                , "주문한 가격은 가격 * 수량이다.");
        assertEquals(8, book.getStockQuantity(), "주문 수량만큼 재고가 줄어야 한다.");
    }

    @Test
    @DisplayName(value = "주문재고초과")
    void orderStockOver() {
        //given
        Member member = this.createMember();
        Item item = this.createItem("Snow County", 10000, 30);
        //when
        final int orderCount = 31;
        //then
        assertThrows(NotEnoughException.class, () -> OrderServiceTests.this.orderService.order(member.getId(), item.getId(), orderCount));
    }

    @Test
    @DisplayName(value = "주문취소")
    void cancel() {
        //given
        Member member = this.createMember();
        Item item = createItem("La Peste", 15000, 20);
        final int orderCount = 2;

        final Long orderId = this.orderService.order(member.getId(), item.getId(), orderCount);
        //when
        this.orderService.cancelOrder(orderId);
        //then
        Order order = this.orderRepository.findOne(orderId);

        assertEquals(OrderStatus.CANCEL, order.getStatus(), "주문 취소시 상태는 CANCEL");
        assertEquals(20, item.getStockQuantity(), "주문이 취소된 상품은 재고가 복귀되어야 한다.");
    }

    private Item createItem(final String name, final int price, final int quantity) {
        Item book = new Book();
        book.setName(name);
        book.setPrice(price);
        book.setStockQuantity(quantity);
        this.entityManager.persist(book);
        return book;
    }

    private Member createMember() {
        Member member = new Member();
        member.setName("name");
        member.setAddress(new Address("Seoul", "Gangnam", "12345"));
        this.entityManager.persist(member);
        return member;
    }
}
