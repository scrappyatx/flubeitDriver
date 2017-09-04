/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.builders;

import android.support.annotation.NonNull;

import java.util.UUID;

import it.flube.driver.modelLayer.entities.Photo;

/**
 * Created on 9/2/2017
 * Project : Driver
 */

public class PhotoBuilder {
    private Photo photo;

    private PhotoBuilder(@NonNull Builder builder){
        this.photo = builder.photo;
    }

    private Photo getPhoto(){
        return photo;
    }

    public static class Builder {
        private Photo photo;

        public Builder(@NonNull String title, @NonNull String description){
            photo = new Photo();
            photo.setGUID(UUID.randomUUID().toString());
            photo.setStatus(Photo.PhotoStatus.NO_ATTEMPTS);
            photo.setTitle(title);
            photo.setDescription(description);
        }

        private void validate(@NonNull Photo photo){
            // required PRESENT (must not be null)
            if (photo.getGUID() == null){
                throw new IllegalStateException("GUID is null");
            }

            if (photo.getTitle() == null){
                throw new IllegalStateException("title is null");
            }

            if (photo.getDescription() == null){
                throw new IllegalStateException("description is null");
            }

            //required SPECIFIC VALUE
            if (photo.getStatus() != Photo.PhotoStatus.NO_ATTEMPTS) {
                throw new IllegalStateException("status is not NO_ATTEMPTS");
            }
        }

        public Photo build(){
            Photo photo = new PhotoBuilder(this).getPhoto();
            validate(photo);
            return photo;
        }

    }
}
