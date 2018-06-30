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
    private static final String FRONT_CORNER_DRIVER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Ffront_corner_driver_view.png?alt=media&token=0ee3734b-7164-44aa-bbda-d822fe9dc759";
    private static final String FRONT_CORNER_PASSENGER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Ffront_corner_passenger_view.png?alt=media&token=ed1c4837-90a6-4c9b-8dba-8039de77ff0f";
    private static final String FRONT_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Ffront_view.png?alt=media&token=e0acc726-0c88-49ec-8651-3905a36a65b9";
    private static final String REAR_CORNER_DRIVER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Frear_corner_driver_view.png?alt=media&token=5c12d346-ec46-489f-96d8-c74afa908c50";
    private static final String REAR_CORNER_PASSENGER_VIEW_HINT_URL="https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Frear_corner_passenger_view.png?alt=media&token=7fa91969-30c7-4fc1-86ff-52abfe61985d";
    private static final String REAR_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Frear_view.png?alt=media&token=eb4b07c1-06f9-4aae-b9b1-a56143601a26";
    private static final String SIDE_DRIVER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Fside_driver_view.png?alt=media&token=dff8e64e-7f15-467f-868a-5792ecead8c9";
    private static final String SIDE_PASSENGER_VIEW_HINT_URL = "https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/photoHints%2FgenericAuto%2Fside_passenger_view.png?alt=media&token=828df158-f3e3-475f-955d-21a25a42c83a";


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
