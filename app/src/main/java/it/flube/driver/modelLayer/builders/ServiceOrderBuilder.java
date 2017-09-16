/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import it.flube.driver.modelLayer.entities.ChatMessage;
import it.flube.driver.modelLayer.entities.MapPing;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrder;
import it.flube.driver.modelLayer.entities.serviceOrder.ServiceOrderAbstractStep;

/**
 * Created on 9/3/2017
 * Project : Driver
 */

public class ServiceOrderBuilder {
    private ServiceOrder serviceOrder;

    private ServiceOrderBuilder(@NonNull Builder builder) {
        this.serviceOrder = builder.serviceOrder;
    }

    private ServiceOrder getServiceOrder(){
        return serviceOrder;
    }

    public static class Builder {
        private ServiceOrder serviceOrder;

        public Builder(){
            serviceOrder = new ServiceOrder();
            serviceOrder.setGUID(UUID.randomUUID().toString());
            serviceOrder.setStatus(ServiceOrder.ServiceOrderStatus.NOT_STARTED);
            serviceOrder.setStepIndex(0);
            serviceOrder.setStepList(new ArrayList<ServiceOrderAbstractStep>());
            serviceOrder.setMapPingList(new ArrayList<MapPing>());
            serviceOrder.setDriverChatHistory(new ArrayList<ChatMessage>());
            serviceOrder.setCustomerChatHistory(new ArrayList<ChatMessage>());
            serviceOrder.setServiceProviderChatHistory(new ArrayList<ChatMessage>());
        }

        public Builder guid(@NonNull String guid) {
            this.serviceOrder.setGUID(guid);
            return this;
        }

        public Builder title(@NonNull String title){
            this.serviceOrder.setTitle(title);
            return this;
        }

        public Builder description(@NonNull String description) {
            this.serviceOrder.setDescription(description);
            return this;
        }

        public Builder status(@NonNull ServiceOrder.ServiceOrderStatus status) {
            this.serviceOrder.setStatus(status);
            return this;
        }

        private Date addMinutesToDate(@NonNull Date initialDate, @NonNull Integer minutesToAdd){
            Calendar cal = Calendar.getInstance();
            cal.setTime(initialDate);
            cal.add(Calendar.MINUTE, minutesToAdd);
            return cal.getTime();
        }

        public Builder startTime(@NonNull Date startTime) {
            this.serviceOrder.setStartTime(new TimestampBuilder.Builder(startTime).build());
            return this;
        }

        public Builder startTime(@NonNull Date startTime, @NonNull Integer minutesToAdd) {
            this.serviceOrder.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(startTime, minutesToAdd)).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime) {
            this.serviceOrder.setFinishTime(new TimestampBuilder.Builder(finishTime).build());
            return this;
        }

        public Builder finishTime(@NonNull Date finishTime, @NonNull Integer minutesToAdd) {
            this.serviceOrder.setStartTime(new TimestampBuilder.Builder(addMinutesToDate(finishTime, minutesToAdd)).build());
            return this;
        }

        public Builder addStep(@NonNull Integer stepIndex, @NonNull ServiceOrderAbstractStep step){
            this.serviceOrder.getStepList().add(stepIndex, step);
            return this;
        }

        private void validate(@NonNull ServiceOrder serviceOrder){
            // required PRESENT (must not be null)
            if (serviceOrder.getGUID() == null) {
                throw new IllegalStateException("GUID is null");
            }

            if (serviceOrder.getTitle() == null) {
                throw new IllegalStateException("title is null");
            }

            if (serviceOrder.getDescription() == null) {
                throw new IllegalStateException("description is null");
            }

            if (serviceOrder.getStartTime() == null) {
                throw new IllegalStateException("startTime is null");
            }

            if (serviceOrder.getFinishTime() == null) {
                throw new IllegalStateException("finishTime is null");
            }

            if (serviceOrder.getMapPingList() == null) {
                throw new IllegalStateException("mapPingList is null");
            } else {
                if (!serviceOrder.getMapPingList().isEmpty()) {
                    throw new IllegalStateException("mapPingList is not EMPTY");
                }
            }

            if (serviceOrder.getDriverChatHistory() == null) {
                throw new IllegalStateException("driverChatHistory is null");
            } else {
                if (!serviceOrder.getDriverChatHistory().isEmpty()) {
                    throw new IllegalStateException("driverChatHistory is not EMPTY");
                }
            }

            if (serviceOrder.getCustomerChatHistory() == null) {
                throw new IllegalStateException("driverChatHistory is null");
            } else {
                if (!serviceOrder.getCustomerChatHistory().isEmpty()) {
                    throw new IllegalStateException("customerChatHistory is not EMPTY");
                }
            }

            if (serviceOrder.getServiceProviderChatHistory() == null) {
                throw new IllegalStateException("serviceProviderChatHistory is null");
            } else {
                if (!serviceOrder.getServiceProviderChatHistory().isEmpty()) {
                    throw new IllegalStateException("serviceProviderChatHistory is not EMPTY");
                }
            }

            if (serviceOrder.getStepList() == null) {
                throw new IllegalStateException("serviceProviderChatHistory is null");
            } else {
                if (serviceOrder.getStepList().isEmpty()) {
                    throw new IllegalStateException("serviceProviderChatHistory is EMPTY");
                }
            }

            // required SPECIFIC VALUE
            if (serviceOrder.getStepIndex() != 0) {
                throw new IllegalStateException("stepIndex is not zero");
            }

        }

        public ServiceOrder build(){
            ServiceOrder serviceOrder = new ServiceOrderBuilder(this).getServiceOrder();
            validate(serviceOrder);
            return serviceOrder;
        }
    }
}
