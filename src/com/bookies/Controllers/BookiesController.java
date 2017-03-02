package com.bookies.Controllers;

import com.bookies.Models.Book;
import com.bookies.Models.BookListModel;
import com.bookies.Models.CartListModel;
import com.bookies.Models.Item;
import com.bookies.Utils.InputReader;
import com.bookies.Views.BookiesView;
import com.sun.java.swing.plaf.windows.WindowsTreeUI;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static com.bookies.Utils.Commands.*;

/**
 * Created by Emil on 2017-02-18.
 */
public class BookiesController {
    private BookListModel bookiesListModel;
    private CartListModel cartListModel;
    private BookiesView bookiesView;
    private Scanner scanner;

    public BookiesController(BookListModel bookiesListModel, CartListModel cartListModel, BookiesView bookiesView) {
        this.bookiesListModel = bookiesListModel;
        this.bookiesView = bookiesView;
        this.cartListModel = cartListModel;
    }

    public void runApp() {
        scanner = new Scanner(System.in);
        showAllBooks();

        Book[] foundBooks = searchBooksQuestion();

        if (foundBooks.length >= 1) {
            Book chosenBook = chosenBookFromSearchResultQuestion(foundBooks);
            int quantity = quantityQuestion();
            addToCartQuestion(quantity, chosenBook);
            showCart();
//            commands();
        } else {
            boolean repeat = true;
            while (repeat) {
                scanner.reset();
                System.out.println("close app? y/n");
                String scanString = InputReader.scan(scanner);
                if ("y".equalsIgnoreCase(scanString)) {
                    System.exit(1);
                } else if ("n".equalsIgnoreCase(scanString)) {
                    runApp();
                } else {
                    System.out.println("you typed something different..try again...");
                    repeat = true;
                }
            }
        }
    }

    public Book[] searchBooks(String searchString) {
        return this.bookiesListModel.list(searchString);
    }

    public List<Book> getBooks() {
        return this.bookiesListModel.getBooks();
    }

    public void showCart() {
        cartListModel.showItemsInCart();
    }

    public void addToCart(int quantity, Book book) {
       boolean succesfullyAddedToCart = this.cartListModel.add(book, quantity);
        System.out.println(succesfullyAddedToCart ? "Items was successfully added to cart" : "Items was not added to cart..");
    }

    public void commands(String command) {

        if(command.equalsIgnoreCase(SHOW_CART)) {
            showCart();
        }
        else if(command.equalsIgnoreCase(SHOW_BOOKS)) {
           showAllBooks();
        }
        else if(command.startsWith(SEARCH_BOOKS)) {
            String[] search  = command.split("search books ");
            System.out.println("book to seach:" +search[1]);
            Book[] foundBooks = searchBooks(search[1]);
            for (Book foundBook : foundBooks) {
                System.out.println(foundBook);
            }
        }
        else if(command.equalsIgnoreCase(BUY_CART)) {
            buyCart();
        }
        else if(command.startsWith(ADD_TO_CART)) {
            String[] splittedString = command.split(" ");

            int quantity = Integer.valueOf(splittedString[3]);
            String commandSplit[] = command.split("\\d");
            String book = commandSplit[1].trim();

            Book[] foundBooks =  searchBooks(book);
            Book chosenBook = chosenBookFromSearchResultQuestion(foundBooks);
            addToCart(quantity, chosenBook);
        }
        else if(command.startsWith(REMOVE_CART_ITEM)) {
            String[] splittedString = command.split(REMOVE_CART_ITEM);
            int index = Integer.valueOf(splittedString[1].trim());
            cartListModel.removeItemFromCart(index);
        }
        else if(command.equalsIgnoreCase(SHOW_COMMANDS)) {
            for (String s : commands) {
                System.out.println(s);
            }
        }

        else if(command.startsWith(BUY)) {

            String[] bookStrings = command.substring(0, 3).split(",");
            Book[] books = new Book[]{};
            for (int i = 0; i < bookStrings.length; i++) {
                String bookString = bookStrings[i].trim();
                books = searchBooks(bookString);
            }
            cartListModel.buy(books);
        }
    }

    public void buyCart() {
        this.cartListModel.buyAllItems();
    }


    public Book chosenBookFromSearchResultQuestion(Book[] foundBooks) {
        scanner = new Scanner(System.in);
        System.out.println("Which of these would you like to purchase?");
        for (int i = 0; i < foundBooks.length ; i++) {
            System.out.println("["+i+"]" + foundBooks[i].toString());
        }

        int scannerInt = InputReader.scanInt(scanner);

        return foundBooks[scannerInt];
    }

    public void showAllBooks() {
        System.out.println("All the books in storage: ");
        for (Book book : getBooks()) {
            System.out.println(book.toString());
        }
    }

    public Book[] searchBooksQuestion() {
//        scanner.reset();
        System.out.println("search author or title: ");
        String scannerStringSearch = InputReader.scan(scanner);

        Book[] foundBooks = searchBooks(scannerStringSearch);
        if (foundBooks.length >= 1) {
            System.out.println("Found books: ");
            for (Book foundBook : foundBooks) {
                System.out.println(foundBook.toString());
            }
        } else {
            scanner.reset();
            System.out.println("no books found...try again? y/n :");

            String scannerStringYesOrNo = InputReader.scan(scanner);

            if ("y".equalsIgnoreCase(scannerStringYesOrNo)) {
                searchBooksQuestion();
            } else if ("n".equalsIgnoreCase(scannerStringYesOrNo)) {
                System.out.println("search canceled");
            } else {
                System.out.println("you typed something different..try again...");
                searchBooksQuestion();
            }
        }
        return foundBooks;
    }

    public void addToCartQuestion(int quantity, Book chosenBook) {
        scanner.reset();
        System.out.println(quantity >= 2 ? "Add books to cart?" : "Add book to cart?");
        System.out.println("Write \"y\" to add and \"n\" to keep shopping");
        String scannerStringYesOrNo = InputReader.scan(scanner);
        if ("y".equalsIgnoreCase(scannerStringYesOrNo)) {
            addToCart(quantity, chosenBook);
        } else if ("n".equalsIgnoreCase(scannerStringYesOrNo)) {
            System.out.println("book not added to cart");
        } else {
            System.out.println("you typed something different..try again...");
            addToCartQuestion(quantity, chosenBook);
        }
    }

    public int quantityQuestion() {
        scanner.reset();
        System.out.println("How many would you like to purchase?");
        int quantity = InputReader.scanInt(scanner);
        if(quantity != -1) {
            return quantity;
        } else {
            System.out.println("you didn't write a number...try again..");
            quantityQuestion();
        }
        return -1;
    }

//    private void buyQuestion(Scanner scanner, Book[] foundBooks) {
//        System.out.println(foundBooks.length >= 1 ? "Add books to cart?" : "Add book to cart?");
//        System.out.println("Write \"y\" to add and \"n\" to keep shopping");
//        String answer = scanner.nextLine();
//        if (answer.equalsIgnoreCase("y")) {
//            buy(foundBooks);
//        } else if (answer.equalsIgnoreCase("n")) {
//            System.out.println("items not added to cart");
//            foundBooks = null;
//        } else {
//            System.out.println("you typed something different..try again...");
//            addToCartQuestion(scanner, foundBooks);
//        }
//    }
}
