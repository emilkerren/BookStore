package com.bookies.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emil on 2017-02-18.
 */
public class BookListModel implements BookList {

    private List<Book> books;
    public BookListModel(List<Book> books) {
        this.books = books;
    }

    @Override
    public Book[] list(String searchString) {
        List<Book> foundBooks = new ArrayList<>();
        for (Book book : books) {
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

    public List<Book> getBooks() {
        return books;
    }

    @Override
    public boolean add(Book book, int quantity) {
        List<Book> extendedBookList = new ArrayList<>(getBooks());
        extendedBookList.add(book);

        return false;
    }

    @Override
    public int[] buy(Book... books) {
        int[] indexesOfBooksToBuy = new int[books.length];
        int i = 0;
        for (Book book : books) {
            indexesOfBooksToBuy[i] = getBooks().indexOf(book);
            i++;
        }

        return indexesOfBooksToBuy;
    }
}
