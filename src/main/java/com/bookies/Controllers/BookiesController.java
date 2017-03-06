package com.bookies.Controllers;

import com.bookies.Models.Book;
import com.bookies.Models.BookListModel;
import com.bookies.Models.CartListModel;
import com.bookies.Utils.InputReader;
import com.bookies.Utils.Utils;
import com.bookies.Views.BookiesView;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import static com.bookies.Utils.Commands.*;

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

    private List<Book> getBooks() {
        return this.bookiesListModel.getBooks();
    }

    private void showCart() {
        cartListModel.showItemsInCart();
    }

    private void addToCart(Book book, int quantity) {
        boolean succesfullyAddedToCart = this.cartListModel.add(book, quantity);
        System.out.println(succesfullyAddedToCart ? "Items was successfully added to cart" : "Items was not added to cart..");
    }

    private void addBookToInventory(Book book, int quantity){
        boolean succesfullyAddedToInventory = this.bookiesListModel.add(book, quantity);
        System.out.println(succesfullyAddedToInventory ? "Item was successfully added to inventory" : "Item was not added to inventory..");
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
                String commandSplit[] = command.split("\\|");
                String book = commandSplit[1].trim();

                Book[] foundBooks = this.bookiesListModel.list(book);
                Book chosenBook = chosenBookFromSearchResultQuestion(foundBooks);
                addToCart(chosenBook, quantity);
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
        } else if(command.startsWith(ADD_TO_INVENTORY)) {
            try {
                String[] splittedString = command.split(" ");

                int quantity = Integer.valueOf(splittedString[3]);
                String commandSplit[] = command.split("\\|");

                String title = Utils.toCamelCase(commandSplit[1].trim());
                String author = Utils.toCamelCase(commandSplit[2].trim());

                Book newBook = new Book();
                newBook.setTitle(title.trim());
                newBook.setAuthor(author.trim());

                String stringPrice = commandSplit[3].trim();

                BigDecimal price = new BigDecimal(stringPrice);

                newBook.setPrice(price);
                addBookToInventory(newBook, quantity);

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
}
