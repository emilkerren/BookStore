package com.bookies.Models;

import com.bookies.DAL.DataAccessLayer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Emil on 2017-02-19.
 */
public class CartListModel implements BookList {

    private List<Book> itemsInCart = new ArrayList<Book>();

    @Override
    public Book[] list(String searchString) {
        return new Book[0];
    }

    @Override
    public boolean add(Book book, int quantity) {
        Item item  = retrieveBookAsItem(book);
        if (quantity >= item.getQuantity()) {
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
                .filter(element -> element.getItem().equals(book)).findFirst().get();
        return item;
    }

    @Override
    public int[] buy(Book... books) {
        return new int[0];
    }

    public void buyAllItems() {
        BigDecimal price = new BigDecimal(0.00);
        for (Book book : itemsInCart) {
            System.out.println("book price: "+book.getPrice());
            price = price.add(new BigDecimal(book.getPrice().doubleValue()));
        }

        System.out.println("Total price: "+price);
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
