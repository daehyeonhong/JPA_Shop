package book.shop.service;

import book.shop.domain.Delivery;
import book.shop.domain.Member;
import book.shop.domain.Order;
import book.shop.domain.OrderItem;
import book.shop.domain.item.Item;
import book.shop.repository.ItemRepository;
import book.shop.repository.MemberRepository;
import book.shop.repository.OrderRepository;
import book.shop.repository.OrderSearch;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final MemberRepository memberRepository;
    private final ItemRepository itemRepository;

    /**
     * 주문
     * @param memberId 멤버아이디
     * @param itemId   아이템아이디
     * @param count    수량
     * @return 주문아이디
     */
    @Transactional
    public Long order(final Long memberId, final Long itemId, final int count) {
        //Entity 조회
        Member member = this.memberRepository.fineOne(memberId);
        Item item = this.itemRepository.findOne(itemId);

        //배송정보 생성
        Delivery delivery = new Delivery();
        delivery.setAddress(member.getAddress());

        //주문상품 생성
        OrderItem orderItem = OrderItem.createOrderItem(item, item.getPrice(), count);

        //주문 생성
        Order order = Order.createOrder(member, delivery, orderItem);

        this.orderRepository.save(order);
        return order.getId();
    }

    /**
     * 주문 취소
     * @param orderId 주문번호
     */
    @Transactional
    public void cancelOrder(final Long orderId) {
        Order order = this.orderRepository.findOne(orderId);
        order.cancel();
    }

    public List<Order> findOrder(final OrderSearch orderSearch) {
        return this.orderRepository.findAllByString(orderSearch);
    }

}
