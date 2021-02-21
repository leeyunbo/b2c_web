package com.jpabook.jpashop.domain.Item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("B")
@Getter
@Setter
public class Book extends Item {

    private String author;
    private String isbn;

    public void change(String name, int price, int stockQuantity, String author, String isbn) {
        super.change(name, price, stockQuantity);
        setAuthor(author);
        setIsbn(isbn);
    }

    public void create(String name, int price, int stockQuantity, String author, String isbn) {
        super.create(name, price, stockQuantity);
        setAuthor(author);
        setIsbn(isbn);
    }
}
