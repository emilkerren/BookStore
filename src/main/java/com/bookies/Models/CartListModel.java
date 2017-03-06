package com.bookies.Models;

import com.bookies.DAL.DataAccessLayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CartListModel implements BookList {

    private List<Book> itemsInCart = new ArrayList<Book>();

    @Override
    public Book[] list(String searchString) {
        return new Book[0];
    }

    @Override
    public boolean add(Book book, int quantity) {
        Item item  = retrieveBookAsItem(book);
        if (quantity > item.getQuantity()) {
            return false;
        }

        for (int i = 0; i < quantity; i++) {
            itemsInCart.add(book);
        }
        return true;
    }

    private Item retrieveBookAsItem(Book book) {
        Item item = DataAccessLayer.getItemList().stream().filter(element->element.getItem() instanceof Book)
//                .filter(((Book) books -> books.getItem()).equals(this.cartListModel.getItemsInCart()))
                .filter(element -> element.getItem().equals(book)).findFirst().orElse(null);
        return item;
    }

    @Override
    public int[] buy(Book... books) {
        int notInStock = 0;
        int ok = 0;
        int doesNotExist = 0;
        for (int i = 0; i < books.length; i++) {
            Item item  = retrieveBookAsItem(books[i]);
            if(item == null) {
                doesNotExist++;
            } else if (item.getQuantity() == 0) {
                notInStock++;
            } else{
                ok++;
            }
        }
        int[] statuses = {ok, notInStock, doesNotExist};
        return statuses;
    }

    public List<Item> buyAllItemsInCart(List<Item> items) {
        Book[] booksArray = new Book[itemsInCart.size()];
        booksArray = itemsInCart.toArray(booksArray);

        int[] statuses = buy(booksArray);
        String status = String.format("OK(%1$d),\nNOT_IN_STOCK(%2$d),\nDOES_NOT_EXIST(%3$d)", statuses[0], statuses[1], statuses[2]);
        System.out.println(status);

        BigDecimal price = new BigDecimal(0.00);
        for (Book book : itemsInCart) {
            items.stream().filter(b -> b.getItem().equals(book)).findFirst().get().decreaseQuantity();
            System.out.println("book price: "+book.getPrice());
            price = price.add(new BigDecimal(book.getPrice().doubleValue()));
        }

        System.out.println("Total price: "+price);

        itemsInCart.clear();
        return items;
    }

    public void removeItemFromCart(int index) {
        itemsInCart.remove(index);
    }

    public List<Item> getBooksInCartAsItem() {
        List<Item> cartItems =  DataAccessLayer.getItemList().stream()
                .filter(element->element.getItem() instanceof Book)
//                .filter(((Book) books -> books.getItem()).equals(this.cartListModel.getItemsInCart()))
                .filter(element -> element.getItem().equals(itemsInCart))
                .collect(Collectors.toList());
        return cartItems;
    }

    public List<Book> getItemsInCart() {
        return itemsInCart;
    }

    public void showItemsInCart() {
        for (int i = 0; i < getItemsInCart().size(); i++) {
            Book book = getItemsInCart().get(i);
            System.out.println("["+ i +"] cart item: "+book.toString());
        }
    }
}
