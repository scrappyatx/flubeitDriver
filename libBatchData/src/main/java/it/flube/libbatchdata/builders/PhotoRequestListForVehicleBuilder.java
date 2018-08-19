/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import java.util.ArrayList;
import java.util.HashMap;

import it.flube.libbatchdata.entities.PhotoRequest;

/**
 * Created on 1/22/2018
 * Project : Driver
 */

public class PhotoRequestListForVehicleBuilder {
    private static final String FRONT_CORNER_DRIVER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Ffront_corner_driver_view.jpg?alt=media&token=de13718e-1484-4d51-8ff3-93b50e333d8b";
    private static final String FRONT_CORNER_PASSENGER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Ffront_corner_passenger_view.jpg?alt=media&token=0579fbaa-2177-456c-88cc-7b55b2b4d836";
    private static final String FRONT_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Ffront_view.jpg?alt=media&token=d7aa3c0c-4d0f-44df-889b-1b0f28b9441e";
    private static final String REAR_CORNER_DRIVER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Frear_corner_driver_view.jpg?alt=media&token=bdd59a46-c008-453c-94ab-8a3ef0a2339a";
    private static final String REAR_CORNER_PASSENGER_VIEW_HINT_URL="https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Frear_corner_passenger_view.jpg?alt=media&token=88322f4d-9d79-49a8-ad14-9a4462269e30";
    private static final String REAR_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Frear_view.jpg?alt=media&token=9b1539e5-b458-4f68-a40d-c969fd0d15c8";
    private static final String SIDE_DRIVER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Fside_driver_view.jpg?alt=media&token=255a6147-7a2b-4911-95de-4488e8fe575b";
    private static final String SIDE_PASSENGER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAutoSmall%2Fside_passenger_view.jpg?alt=media&token=bf0529ef-de2e-4f95-b35f-5c8502f57703";


    private HashMap<String, PhotoRequest> photoList;

    private PhotoRequestListForVehicleBuilder(Builder builder){
        this.photoList = builder.photoList;
    }

    private HashMap<String, PhotoRequest> getPhotoList(){
        return photoList;
    }

    public static class Builder {
        private HashMap<String, PhotoRequest> photoList;

        public Builder(){
            photoList = new HashMap<String, PhotoRequest>();
            addFrontCornerDriver();
            addFrontView();
            addFrontCornerPassenger();

            addRearCornerDriver();
            addRearCornerPassenger();
            addRearView();

            addSideDriver();
            addSidePassenger();
        }

        private void addFrontCornerDriver(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                            .title("Front Corner Driver")
                            .description("Take this photo facing the front corner of the vehicle on the driver side")
                            .sequence(this.photoList.size()+1)
                            .photoHintUrl(FRONT_CORNER_DRIVER_VIEW_HINT_URL)
                            .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addFrontCornerPassenger(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Front Corner Passenger")
                    .description("Take this photo facing the front corner of the vehicle on the passenger side")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(FRONT_CORNER_PASSENGER_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addFrontView(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Front")
                    .description("Take this photo facing the front of the vehicle")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(FRONT_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addRearCornerDriver(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Rear Corner Driver")
                    .description("Take this photo facing the rear corner of the vehicle of the vehicle on the driver side")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(REAR_CORNER_DRIVER_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addRearCornerPassenger(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Rear Corner Passenger")
                    .description("Take this photo facing the rear corner of the vehicle on the passenger side")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(REAR_CORNER_PASSENGER_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addRearView(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Rear")
                    .description("Take this photo facing the rear of the vehicle")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(REAR_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addSideDriver(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Side Driver")
                    .description("Take this photo facing the side of the vehicle on the driver side")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(SIDE_DRIVER_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void addSidePassenger(){
            PhotoRequest photoRequest = new PhotoRequestBuilder.Builder()
                    .title("Side Passenger")
                    .description("Take this photo facing the side of the vehicle on the passenger side")
                    .sequence(this.photoList.size()+1)
                    .photoHintUrl(SIDE_PASSENGER_VIEW_HINT_URL)
                    .build();
            this.photoList.put(photoRequest.getGuid(), photoRequest);
        }

        private void validate(HashMap<String, PhotoRequest> photoList){
            // required PRESENT (must not be null)
            if (photoList.size() <= 0){
                throw new IllegalStateException("no photos in photoList");
            }
        }

        public HashMap<String, PhotoRequest> build(){
            HashMap<String, PhotoRequest> photoList = new PhotoRequestListForVehicleBuilder(this).getPhotoList();
            validate(photoList);
            return photoList;
        }

    }
}
