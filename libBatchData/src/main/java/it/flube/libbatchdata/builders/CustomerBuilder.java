/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.entities.Customer;

/**
 * Created on 9/8/2018
 * Project : Driver
 */
public class CustomerBuilder {
    private Customer customer;

    private CustomerBuilder(Builder builder){
        this.customer = builder.customer;
    }

    private Customer getCustomer(){
        return this.customer;
    }

    public static class Builder {
        private Customer customer;

        public Builder(){
            this.customer = new Customer();
        }

        public Builder username(String username){
            this.customer.setUsername(username);
            return this;
        }

        public Builder email(String email){
            this.customer.setEmail(email);
            return this;
        }

        public Builder id(String id){
            this.customer.setId(id);
            return this;
        }

        private void validate(Customer customer){
            if (customer.getId()==null){
                throw new IllegalStateException("id is null");
            }
        }

        public Customer build(){
            Customer customer = new CustomerBuilder(this).getCustomer();
            validate(customer);
            return customer;
        }


    }
}
