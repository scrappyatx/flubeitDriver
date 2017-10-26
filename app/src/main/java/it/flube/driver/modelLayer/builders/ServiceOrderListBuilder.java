/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import java.util.ArrayList;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderList;

/**
 * Created on 9/20/2017
 * Project : Driver
 */

public class ServiceOrderListBuilder {
    private ServiceOrderList serviceOrderList;

    private ServiceOrderListBuilder(Builder builder){
        this.serviceOrderList = builder.serviceOrderList;
    }

    private ServiceOrderList getServiceOrderList(){
        return serviceOrderList;
    }

    public static class Builder {
        private ServiceOrderList serviceOrderList;

        public Builder(){
            this.serviceOrderList = new ServiceOrderList();
            this.serviceOrderList.setGuid(BuilderUtilities.generateGuid());
            this.serviceOrderList.setCurrentServiceOrder(0);
        }

        public Builder guid(String guid){
            this.serviceOrderList.setGuid(guid);
            return this;
        }

        public Builder batchGuid(String guid){
            this.serviceOrderList.setBatchGuid(guid);
            return this;
        }

        public Builder batchDetailGuid(String guid){
            this.serviceOrderList.setBatchDetailGuid(guid);
            return this;
        }

        public Builder currentServiceOrder(Integer currentServiceOrder){
            this.serviceOrderList.setCurrentServiceOrder(currentServiceOrder);
            return this;
        }


        private void validate(ServiceOrderList serviceOrderList){

        }

        public ServiceOrderList build(){
            ServiceOrderList serviceOrderList = new ServiceOrderListBuilder(this).getServiceOrderList();
            validate(serviceOrderList);
            return serviceOrderList;
        }
    }
}
