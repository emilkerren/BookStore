package com.bookies.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Emil on 2017-03-01.
 */
public class Commands {
    public static final String SHOW_COMMANDS = "show commands";
    public static final String SHOW_BOOKS = "show books";
    public static final String SHOW_CART = "show cart";
    public static final String ADD_TO_CART = "add to cart";
    public static final String BUY_CART = "buy cart";
    public static final String SEARCH_BOOKS = "search books";
    public static final String REMOVE_CART_ITEM = "remove cart item";
    public static final String BUY = "buy";

    public static final List<String> commands = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(SHOW_COMMANDS);
                add(SHOW_BOOKS);
                add(SHOW_CART);
                add(ADD_TO_CART+" [number of items] [book title/autor]");
                add(BUY_CART);
                add(SEARCH_BOOKS +" [book title/autor]");
                add(REMOVE_CART_ITEM + " [index of 'show cart list item']");
                add(BUY + "[book]");
            }});

}
