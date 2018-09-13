/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.HashMap;

import it.flube.libbatchdata.entities.ProductList;

/**
 * Created on 9/10/2018
 * Project : Driver
 */
public class ProductListBuilder {
    private ProductList productList;

    private ProductListBuilder(Builder builder){
        this.productList = builder.productList;
    }

    private ProductList getProductList(){
        return this.productList;
    }

    public static class Builder {
        private ProductList productList;

        public Builder(){
            this.productList = new ProductList();
            //setup itemMap
            this.productList.setCartItemMap(new HashMap<String, String>());
        }

        public Builder addCartItem(String cartItem){
            this.productList.getCartItemMap().put(BuilderUtilities.generateGuid(), cartItem);
            return this;
        }

        private void validate(ProductList productList){

        }

        public ProductList build(){
            ProductList productList = new ProductListBuilder(this).getProductList();
            validate(productList);
            return productList;
        }
    }
}
