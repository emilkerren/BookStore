package com.bookies.Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Commands {
    public static final String SHOW_COMMANDS = "show commands";
    public static final String SHOW_BOOKS = "show books";
    public static final String SHOW_CART = "show cart";
    public static final String ADD_TO_CART = "add to cart";
    public static final String ADD_TO_INVENTORY = "add to inventory";
    public static final String BUY_CART = "buy cart";
    public static final String SEARCH_BOOKS = "search books";
    public static final String REMOVE_CART_ITEM = "remove cart item";
    public static final String BUY = "buy";
    public static final String EXIT = "exit";

    public static final List<String> commands = Collections.unmodifiableList(
            new ArrayList<String>() {{
                add(SHOW_COMMANDS);
                add(SHOW_BOOKS);
                add(SHOW_CART);
                add(ADD_TO_CART+" [number of items] | [book title/author]");
                add(ADD_TO_INVENTORY+" [number of items] | [book title] | [author] | [price]");
                add(BUY_CART);
                add(SEARCH_BOOKS +" [book title/author]");
                add(REMOVE_CART_ITEM + " [index of 'show cart list item']");
                add(BUY + " [book,book2,book3]");
                add(EXIT);
            }});

}
