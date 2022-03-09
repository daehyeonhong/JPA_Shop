package book.shop.controller;

import book.shop.domain.BookForm;
import book.shop.domain.item.Book;
import book.shop.domain.item.Item;
import book.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping(value = "/items")
@RequiredArgsConstructor
public class ItemController {
    private final ItemService itemService;

    @GetMapping(value = "/new")
    public String createForm(final Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemForm";
    }

    @PostMapping(value = "/new")
    public String create(final BookForm bookForm) {
        Book book = new Book();
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());
        this.itemService.saveItem(book);
        return "redirect:/items";
    }

    @GetMapping(value = "")
    public String list(final Model model) {
        List<Item> items = this.itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping(value = "/{itemId}/edit")
    public String updateItemForm(@PathVariable final Long itemId, final Model model) {
        Book item = (Book) this.itemService.findOne(itemId);

        final BookForm bookForm = new BookForm();
        bookForm.setId(item.getId());
        bookForm.setName(item.getName());
        bookForm.setPrice(item.getPrice());
        bookForm.setStockQuantity(item.getStockQuantity());
        bookForm.setAuthor(item.getAuthor());
        bookForm.setIsbn(item.getIsbn());
        model.addAttribute("form", bookForm);
        return "items/updateItemForm";
    }

    @PostMapping(value = "/{itemId}/edit")
    public String updateItem(@PathVariable final Long itemId, @ModelAttribute final BookForm bookForm) {
        Book book = new Book();
        book.setId(itemId);
        book.setName(bookForm.getName());
        book.setPrice(bookForm.getPrice());
        book.setStockQuantity(bookForm.getStockQuantity());
        book.setAuthor(bookForm.getAuthor());
        book.setIsbn(bookForm.getIsbn());

        this.itemService.updateItem(itemId, book);
        return "redirect:items";
    }
}
