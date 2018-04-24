/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.asset;

import it.flube.libbatchdata.interfaces.AssetInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public abstract class AbstractAsset {

    protected String guid;
    protected String name;
    protected String description;

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
