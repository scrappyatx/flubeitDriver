/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders.serviceOrder;


import it.flube.libbatchdata.builders.BuilderUtilities;
import it.flube.libbatchdata.builders.ProductListBuilder;
import it.flube.libbatchdata.entities.ProductList;
import it.flube.libbatchdata.entities.Timestamp;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;

/**
 * Created on 9/26/2017
 * Project : Driver
 */

public class ServiceOrderBuilder {
    private ServiceOrder serviceOrder;

    private ServiceOrderBuilder(Builder builder){
        this.serviceOrder = builder.serviceOrder;
    }

    private ServiceOrder getServiceOrder(){
        return serviceOrder;
    }

    public static class Builder {
        private ServiceOrder serviceOrder;

        public Builder(){
            this.serviceOrder = new ServiceOrder();
            this.serviceOrder.setGuid(BuilderUtilities.generateGuid());
            this.serviceOrder.setStatus(ServiceOrder.ServiceOrderStatus.NOT_STARTED);
            this.serviceOrder.setProductList(new ProductListBuilder.Builder().build());
        }

        public Builder guid(String guid){
            this.serviceOrder.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.serviceOrder.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.serviceOrder.setBatchDetailGuid(guid);
            return this;
        }

        public Builder title(String title){
            this.serviceOrder.setTitle(title);
            return this;
        }

        public Builder description(String description){
            this.serviceOrder.setDescription(description);
            return this;
        }

        public Builder status(ServiceOrder.ServiceOrderStatus status){
            this.serviceOrder.setStatus(status);
            return this;
        }

        public Builder sequence(Integer sequence){
            this.serviceOrder.setSequence(sequence);
            return this;
        }

        public Builder totalSteps(Integer totalSteps){
            this.serviceOrder.setTotalSteps(totalSteps);
            return this;
        }

        public Builder startTime(Timestamp startTime) {
            this.serviceOrder.setStartTime(startTime);
            return this;
        }

        public Builder finishTime(Timestamp finishTime) {
            this.serviceOrder.setFinishTime(finishTime);
            return this;
        }

        public Builder productList(ProductList productList){
            this.serviceOrder.setProductList(productList);
            return this;
        }

        private void validate(ServiceOrder serviceOrder){

        }

        public ServiceOrder build(){
            ServiceOrder serviceOrder = new ServiceOrderBuilder(this).getServiceOrder();
            validate(serviceOrder);
            return serviceOrder;
        }
    }
}
