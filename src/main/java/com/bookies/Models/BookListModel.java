package com.bookies.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookListModel implements BookList {
    private List<Item> items;
    private List<Book> bookList;
    public BookListModel(List<Item> items) {
        this.items = items;
        this.bookList = items.stream()
                .filter(element->element.getItem() instanceof Book)
                .map(element->(Book)element.getItem())
                .collect(Collectors.toList());
    }

    @Override
    public Book[] list(String searchString) {
        List<Book> foundBooks = new ArrayList<>();
        for (Item item : items) {
            Book book = (Book)item.getItem();
            if(book.getAuthor().equalsIgnoreCase(searchString)) {
                foundBooks.add(book);
            }
            if(book.getTitle().equalsIgnoreCase(searchString)) {
                foundBooks.add(book);
            }
        }
        Book[] booksArray = new Book[foundBooks.size()];
        booksArray = foundBooks.toArray(booksArray);
        return booksArray;
    }

    @Override
    public boolean add(Book book, int quantity) {
        int beforeAdd = items.size();
        Item item = new Item();
        item.setItem(book);
        item.setQuantity(quantity);
        items.add(item);
        bookList.add(book);
        int afterAdd = items.size();

        if(afterAdd > beforeAdd)
            return true;
        else
            return false;
    }

    @Override
    public int[] buy(Book... books) {
        int[] indexesOfBooksToBuy = new int[books.length];
        int i = 0;
        for (Book book : books) {
            indexesOfBooksToBuy[i] = bookList.indexOf(book);
            i++;
        }
        int okToBuy = 0;
        int notInStock = 1;
        int doesNotExist = 2;
        String status = String.format("OK({0}),\nNOT_IN_STOCK({1}),\nDOES_NOT_EXIST({2})", okToBuy, notInStock, doesNotExist);
        System.out.println(status);
        return indexesOfBooksToBuy;
    }

    public List<Book> getBooks() {
        return bookList;
    }

    public List<Item> getItems() {
        return items;
    }
}
