package com.bookies.Models;

import java.math.BigDecimal;

/**
 * Created by Emil on 2017-02-18.
 */
public class Book {
    private String title;
    private String author;
    private BigDecimal price;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Book: Title: "+title+ ", Author: "+author+", Price: "+price;
    }
}
