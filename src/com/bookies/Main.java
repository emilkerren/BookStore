package com.bookies;

import com.bookies.Controllers.BookiesController;
import com.bookies.DAL.DataAccessLayer;
import com.bookies.Models.BookListModel;
import com.bookies.Models.CartListModel;
import com.bookies.Utils.InputReader;
import com.bookies.Views.BookiesView;

import java.util.Scanner;

public class Main {

    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
	    System.out.println("Hello");
//        scanner = new Scanner(System.in);
        DataAccessLayer dataAccessLayer = new DataAccessLayer();
        BookiesView bookiesView = new BookiesView();
        BookListModel bookListModel = new BookListModel(dataAccessLayer.getBooks());
        CartListModel cartListModel = new CartListModel();
        BookiesController bookiesController = new BookiesController(bookListModel, cartListModel, null);
//        bookiesController.runApp();

        while (scanner.hasNext()) {
            String command = InputReader.scan(scanner);
            bookiesController.commands(command);
        }
    }
}
