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
    private BookListModel bookListModel;
    private BookiesController bookiesController;
    private CartListModel cartListModel;
    private DataAccessLayer dataAccessLayer;


    @Before
    public void setUp() throws Exception {
        dataAccessLayer = new DataAccessLayer();
        items = dataAccessLayer.getBooks();
        bookListModel = new BookListModel(items);
        cartListModel = new CartListModel();
        bookiesController = new BookiesController(bookListModel, cartListModel, null);
    }

    @After
    public void tearDown() throws Exception {
        items.clear();
        bookiesController = null;
        cartListModel = null;
        bookListModel = null;
        dataAccessLayer = null;
    }

    @Test
    public void items_should_be_populated_from_site() {
        assertEquals(7, items.size());
    }

//    @Test
    public void should_show_commands() {
        bookiesController.commands("show commands");
    }


    @Test
    public void should_return_books_of_search() {
        Book[] genericTitleBooks = bookListModel.list("Generic Title");
        assertEquals(2, genericTitleBooks.length);

        Book[] mastering = bookListModel.list("mastering åäö");
        assertEquals(1, mastering.length);


    }

//    @Test
    public void should_add_book_to_cart() {

    }

}