package com.bookies.Controllers;

import com.bookies.Models.CartListModel;
import com.bookies.Views.CartView;

/**
 * Created by Emil on 2017-02-19.
 */
public class ShoppingCartController {

    private CartView cartView;
    private CartListModel cartListModel;
    public ShoppingCartController(CartListModel cartListModel, CartView cartView) {
        this.cartListModel = cartListModel;
        this.cartView = cartView;
    }
}
