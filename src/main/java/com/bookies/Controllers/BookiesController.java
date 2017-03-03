package com.bookies.Controllers;

import com.bookies.Models.Book;
import com.bookies.Models.BookListModel;
import com.bookies.Models.CartListModel;
import com.bookies.Utils.InputReader;
import com.bookies.Views.BookiesView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.bookies.Utils.Commands.*;


/**
 * Created by Emil on 2017-02-18.
 */
public class BookiesController {
    private static final String UNRECOGNIZED_COMMAND = "This command is not recognized...Error: ";
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

    private List<Book> getBooks() {
        return this.bookiesListModel.getBooks();
    }

    private void showCart() {
        cartListModel.showItemsInCart();
    }

    private void addToCart(int quantity, Book book) {
        boolean succesfullyAddedToCart = this.cartListModel.add(book, quantity);
        System.out.println(succesfullyAddedToCart ? "Items was successfully added to cart" : "Items was not added to cart..");
    }

    public void commands(String command) {

        if (command.equalsIgnoreCase(SHOW_CART)) {
            try {
                showCart();
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        } else if (command.equalsIgnoreCase(SHOW_BOOKS)) {
            try {
                showAllBooks();
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        } else if (command.startsWith(SEARCH_BOOKS)) {
            try {
                String[] search = command.split("search books ");
                System.out.println("book to seach:" + search[1]);
                Book[] foundBooks = this.bookiesListModel.list(search[1]);
                for (Book foundBook : foundBooks) {
                    System.out.println(foundBook);
                }
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        } else if (command.equalsIgnoreCase(BUY_CART)) {
            buyCart();
        } else if (command.startsWith(ADD_TO_CART)) {
            try {
                String[] splittedString = command.split(" ");

                int quantity = Integer.valueOf(splittedString[3]);
                String commandSplit[] = command.split("\\d");
                String book = commandSplit[1].trim();

                Book[] foundBooks = this.bookiesListModel.list(book);
                Book chosenBook = chosenBookFromSearchResultQuestion(foundBooks);
                addToCart(quantity, chosenBook);
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        } else if (command.startsWith(REMOVE_CART_ITEM)) {
            try {
                String[] splittedString = command.split(REMOVE_CART_ITEM);
                int index = Integer.valueOf(splittedString[1].trim());
                cartListModel.removeItemFromCart(index);
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        } else if (command.equalsIgnoreCase(SHOW_COMMANDS)) {
            try {
                for (String s : commands) {
                    System.out.println(s);
                }
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        } else if (command.startsWith(BUY)) {
            try {
                List<String> bookStrings = new ArrayList<>();
                if (command.contains(",")) {
                    String bookStringsArray[] = command.replaceFirst("buy ", "").split(",");
                    bookStrings.addAll(Arrays.asList(bookStringsArray));
                } else {
                    String bookString = command.replaceFirst("buy ", "");
                    bookStrings.add(bookString.trim());
                }

                Book[] books = new Book[]{};
                List<Book> bookList = new ArrayList<>();

                for (int i = 0; i < bookStrings.size(); i++) {
                    String bookString = bookStrings.get(i).trim();
                    Book[] tempBooks = this.bookiesListModel.list(bookString);
                    bookList.addAll(Arrays.asList(tempBooks));
                }
                Book[] tempArray = new Book[bookList.size()];
                books = bookList.toArray(tempArray);
                cartListModel.buy(books);
            } catch (Exception e) {
                System.out.println(UNRECOGNIZED_COMMAND + e.getMessage());
            }
        }

        else if(command.equalsIgnoreCase(EXIT)) {
            System.exit(1);
        }
    }

    private void buyCart() {
        this.cartListModel.buyAllItems();
    }

    private void showAllBooks() {
        System.out.println("All the books in storage: ");
        for (Book book : getBooks()) {
            System.out.println(book.toString());
        }
    }

    private Book chosenBookFromSearchResultQuestion(Book[] foundBooks) {
        scanner = new Scanner(System.in);
        if (foundBooks.length >= 2) {
            System.out.println("Which of these would you like to purchase?");
            for (int i = 0; i < foundBooks.length; i++) {
                System.out.println("[" + i + "]" + foundBooks[i].toString());
            }

            int scannerInt = InputReader.scanInt(scanner);

            return foundBooks[scannerInt];
        }
        return foundBooks[0];
    }

    private Book[] searchBooksQuestion() {
//        scanner.reset();
        System.out.println("search author or title: ");
        String scannerStringSearch = InputReader.scan(scanner);

        Book[] foundBooks = this.bookiesListModel.list(scannerStringSearch);
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

    private void addToCartQuestion(int quantity, Book chosenBook) {
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

    private int quantityQuestion() {
        scanner.reset();
        System.out.println("How many would you like to purchase?");
        int quantity = InputReader.scanInt(scanner);
        if (quantity != -1) {
            return quantity;
        } else {
            System.out.println("you didn't write a number...try again..");
            quantityQuestion();
        }
        return -1;
    }
}
