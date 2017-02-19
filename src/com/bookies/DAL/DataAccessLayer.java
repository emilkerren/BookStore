package com.bookies.DAL;

import com.bookies.Models.Book;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Emil on 2017-02-18.
 */
public class DataAccessLayer {

    public List<Book> getBooks() {
        List<Book> bookList = new ArrayList<>();
        try {
            String pageText;
            URL url = new URL("http://www.contribe.se/bookstoredata/bookstoredata.txt");
            URLConnection conn = url.openConnection();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), StandardCharsets.UTF_8))) {
                pageText = reader.lines().collect(Collectors.joining("\n"));
            }

            String[] lines = pageText.split("\n");
            for (String line : lines) {
                if (null != line) {
                    String[] splittedLine = line.split(";");
                    Book book = new Book();
                    book.setTitle(splittedLine[0]);
                    book.setAuthor(splittedLine[1]);
                    book.setPrice(convertToBigDecimal(splittedLine[2]));

                    bookList.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bookList;
    }

    private BigDecimal convertToBigDecimal(String s) {
        String newString = s.replaceAll(",", "");
        BigDecimal bigDecimal = new BigDecimal(newString);
        return bigDecimal;
    }

}
