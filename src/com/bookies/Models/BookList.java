package com.bookies.Models;

/**
 * Created by Emil on 2017-02-18.
 */
public interface BookList {
    public Book[] list(String searchString);
    public boolean add(Book book, int quantity);
    public int[] buy(Book... books);
}
