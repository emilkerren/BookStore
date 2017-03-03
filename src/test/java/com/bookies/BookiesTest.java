package com.bookies;

import com.bookies.Controllers.BookiesController;
import com.bookies.DAL.DataAccessLayer;
import com.bookies.Models.Book;
import com.bookies.Models.BookListModel;
import com.bookies.Models.CartListModel;
import com.bookies.Models.Item;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Emil on 2017-03-03.
 */
public class BookiesTest {

    private List<Item> items = new ArrayList<>();
    private BookListModel bookiesListModel;
    private BookiesController bookiesController;
    private CartListModel cartListModel;
    private DataAccessLayer dataAccessLayer;


    @Before
    public void setUp() throws Exception {
        dataAccessLayer = new DataAccessLayer();
        items = dataAccessLayer.getBooks();
        bookiesListModel = new BookListModel(items);
        cartListModel = new CartListModel();
        bookiesController = new BookiesController(bookiesListModel, cartListModel, null);
    }

    @After
    public void tearDown() throws Exception {
        items.clear();
        bookiesController = null;
        cartListModel = null;
        bookiesListModel = null;
        dataAccessLayer = null;
    }

    @Test
    public void items_should_be_populated_from_site() {
        assertEquals(7, items.size());
    }

//    @Test
//    public void should_show_commands() {
//        bookiesController.commands("show commands");
//    }


    @Test
    public void should_return_books_of_search() {
        Book[] genericTitleBooks = bookiesListModel.list("Generic Title");
        assertEquals(2, genericTitleBooks.length);

        Book[] mastering = bookiesListModel.list("mastering åäö");
        assertEquals(1, mastering.length);

        Book[] fail = bookiesListModel.list("fail");
        assertEquals(0, fail.length);
    }

    @Test
    public void should_not_add_book_to_cart_because_of_too_low_inventory() {
        String book = "rich bloke";
        Book[] foundBooks = bookiesListModel.list((book));  //2 books
        Book chosenBook = foundBooks[1]; //index 0 has 1 quantity, index 1 has 0
        boolean added = this.cartListModel.add(chosenBook, 2);
//        addToCart(quantity, chosenBook);

        assertEquals(false, added);
        assertEquals(0, cartListModel.getItemsInCart().size());
    }

    @Test
    public void should_add_book_to_cart() {
        String book = "mastering åäö";
        Book[] foundBooks = bookiesListModel.list((book));  //1 book found
        Book chosenBook = foundBooks[0]; //index 0 has 1 quantity, index 1 has 0
        boolean added = this.cartListModel.add(chosenBook, 1);

        assertEquals(true, added);
        assertEquals(1, cartListModel.getItemsInCart().size());
    }

    @Test
    public void should_remove_book_from_cart() {
        addSomeBooksToCart();

        cartListModel.removeItemFromCart(3);
        cartListModel.removeItemFromCart(1);

        assertEquals(2, cartListModel.getItemsInCart().size());
    }

    private void addSomeBooksToCart(){
        List<String> titles = new ArrayList<>();
        titles.add("mastering åäö");
        titles.add("First Author");
        titles.add("How To Spend Money");
        titles.add("Cunning Bastard");

        for (String title : titles) {
            Book[] foundBooks = bookiesListModel.list(title);  //1 book found
            Book chosenBook = foundBooks[0]; //index 0 has 1 quantity, index 1 has 0
            boolean added = this.cartListModel.add(chosenBook, 1);
        }
    }
}