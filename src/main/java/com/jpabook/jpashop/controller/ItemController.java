package com.jpabook.jpashop.controller;

import com.jpabook.jpashop.domain.Item.Book;
import com.jpabook.jpashop.domain.Item.Item;
import com.jpabook.jpashop.domain.MemberInfo;
import com.jpabook.jpashop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new BookForm());
        return "items/createItemsForm";
    }

    @PostMapping("/items/new")
    public String create(@Valid BookForm form, BindingResult result, HttpServletRequest httpServletRequest) {
        if (result.hasErrors()) {
            return "items/createItemsForm";
        }

        HttpSession session = httpServletRequest.getSession();
        MemberInfo memberInfo = (MemberInfo) session.getAttribute("memberInfo");

        Book book = new Book();
        book.create(form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        itemService.saveItem(book, memberInfo.getId());

        return "redirect:/";
    }

    @GetMapping("/items")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "items/itemList";
    }

    @GetMapping("items/{itemId}/edit")
    public String updateItemForm(@PathVariable("itemId") Long itemId, Model model) {
        Book book = (Book) itemService.findOne(itemId); // 캐스팅하는 것은 좋지 않은 습관. 나중에 고치자

        BookForm form = new BookForm();
        form.setId(book.getId());
        form.setName(book.getName());
        form.setPrice(book.getPrice());
        form.setStockQuantity(book.getStockQuantity());
        form.setAuthor(book.getAuthor());
        form.setIsbn(book.getIsbn());

        model.addAttribute("form", form);
        return "items/updateItemForm";
    }

    @PostMapping("items/{itemId}/edit")
    public String updateItem(@PathVariable Long itemId, @ModelAttribute("form") BookForm form) {
        // 해당 상품에 대한 권한이 있는지 체크하는 로직이 있으면 보안상 좋음
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity(), form.getAuthor(), form.getIsbn());
        return "redirect:/items";
    }
}
