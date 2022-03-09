package book.shop.controller;

import book.shop.domain.Member;
import book.shop.domain.Order;
import book.shop.domain.item.Item;
import book.shop.repository.OrderSearch;
import book.shop.service.ItemService;
import book.shop.service.MemberService;
import book.shop.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

@Controller
@RequestMapping(value = "/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final ItemService itemService;

    @GetMapping(value = "")
    public String createForm(final Model model) {
        List<Member> members = this.memberService.findMembers();
        List<Item> items = this.itemService.findItems();

        model.addAttribute("members", members);
        model.addAttribute("items", items);
        return "order/orderForm";
    }

    @PostMapping(value = "")
    public String order(@RequestParam final Long memberId,
                        @RequestParam final Long itemId,
                        @RequestParam final int count) {
        this.orderService.order(memberId, itemId, count);
        return "redirect:/orders";
    }

    @GetMapping(value = "s")
    public String orderList(@ModelAttribute final OrderSearch orderSearch, final Model model) {
        List<Order> order = this.orderService.findOrder(orderSearch);
        model.addAttribute("orders", order);
        return "order/orderList";
    }

    @PostMapping(value = "s/{orderId}/cancel")
    public String cancelOrder(@PathVariable final Long orderId) {
        this.orderService.cancelOrder(orderId);
        return "redirect:/orders";
    }
}
