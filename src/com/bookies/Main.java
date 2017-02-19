package com.bookies;

import com.bookies.Controllers.BookiesController;
import com.bookies.DAL.DataAccessLayer;
import com.bookies.Models.BookListModel;
import com.bookies.Views.BookiesView;

public class Main {

    public static void main(String[] args) {
	    System.out.println("Hello");

        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        BookiesView bookiesView = new BookiesView();
        BookListModel bookListModel = new BookListModel(dataAccessLayer.getBooks());
        BookiesController bookiesController = new BookiesController(bookListModel, null);
        bookiesController.runApp();
    }
}
