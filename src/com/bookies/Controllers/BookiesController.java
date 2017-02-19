package com.bookies.Controllers;

import com.bookies.Models.Book;
import com.bookies.Models.BookListModel;
import com.bookies.Views.BookiesView;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Emil on 2017-02-18.
 */
public class BookiesController {
    private BookListModel bookiesListModel;
    private BookiesView bookiesView;

    public BookiesController(BookListModel bookiesListModel, BookiesView bookiesView) {
        this.bookiesListModel = bookiesListModel;
        this.bookiesView = bookiesView;
    }

    public Book[] searchBooks(String searchString) {
        return this.bookiesListModel.list(searchString);
    }

    public List<Book> getBooks() {
        return this.bookiesListModel.getBooks();
    }

    public void addToCart(Book... books) {
        List<Book> cartOfBooks = new ArrayList<>();

        int[] indexes = this.bookiesListModel.buy(books);
        for (int index : indexes) {
            cartOfBooks.add(getBooks().get(index));
        }
    }

    public void runApp() {
        showAllBooks();

        Scanner scanner = new Scanner(System.in);
        Book[] foundBooks = searchBooksQuestion(scanner);

        if (foundBooks.length >= 1) {
            addToCartQuestion(scanner, foundBooks);
        } else {

            boolean repeat = true;
            while (repeat) {
                System.out.println("close app? y/n");
                String answer = scanner.nextLine();
                if (answer.equalsIgnoreCase("y")) {
                    System.exit(1);
                } else if (answer.equalsIgnoreCase("n")) {
                    runApp();
                } else {
                    System.out.println("you typed something different..try again...");
                    repeat = true;
                }
            }
        }


    }

    private void showAllBooks() {
        System.out.println("All the books in storage: ");
        for (Book book : getBooks()) {
            System.out.println(book.toString());
        }
    }

    private Book[] searchBooksQuestion(Scanner scanner) {
        System.out.println("search author or title: ");
        Book[] foundBooks = searchBooks(scanner.nextLine());

        if (foundBooks.length >= 1) {
            System.out.println("Found books: ");
            for (Book foundBook : foundBooks) {
                System.out.println("Found Book: " + foundBook.toString());
            }
        } else {
            System.out.println("no books found...try again? y/n :");
            String answer = scanner.nextLine();

            if (answer.equalsIgnoreCase("y")) {
                searchBooksQuestion(scanner);
            } else if (answer.equalsIgnoreCase("n")) {
                System.out.println("search canceled");
            } else {
                System.out.println("you typed something different..try again...");
                searchBooksQuestion(scanner);
            }
        }
        return foundBooks;
    }

    private void addToCartQuestion(Scanner scanner, Book[] foundBooks) {
        System.out.println(foundBooks.length >= 1 ? "Add books to cart?" : "Add book to cart?");
        System.out.println("Write \"y\" to add and \"n\" to keep shopping");
        String answer = scanner.nextLine();
        if (answer.equalsIgnoreCase("y")) {
            addToCart(foundBooks);
        } else if (answer.equalsIgnoreCase("n")) {
            System.out.println("items not added to cart");
            foundBooks = null;
        } else {
            System.out.println("you typed something different..try again...");
            addToCartQuestion(scanner, foundBooks);
        }
    }
}
