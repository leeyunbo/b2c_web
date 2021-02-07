package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItermController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemsForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult result) {
        if (result.hasErrors()) {
            return "items/createItemsForm";
        }

        Book book = new Book();

        // 나중에 createBook() 메서드로 통합시켜서 만들어놓자 => 엔티티에 ( Setter 방지 )
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }
}
