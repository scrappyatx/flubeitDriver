/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities;

import java.util.HashMap;

/**
 * Created on 9/10/2018
 * Project : Driver
 */
public class ProductList {
    private HashMap<String, String> cartItemMap;

    public HashMap<String, String> getCartItemMap() {
        return cartItemMap;
    }

    public void setCartItemMap(HashMap<String, String> cartItemMap) {
        this.cartItemMap = cartItemMap;
    }
}
