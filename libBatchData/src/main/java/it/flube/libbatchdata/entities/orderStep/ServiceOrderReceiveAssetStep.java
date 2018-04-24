/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.ArrayList;

import it.flube.libbatchdata.entities.ContactPerson;

import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderReceiveAssetStep extends AbstractStep implements OrderStepInterface {

    private ArrayList<AssetTransfer> assetList;
    private ContactPerson contactPerson;
    private AssetTransferInterface.TransferType transferType;

    /// implement the getters/setters for the unique data in this step type
    public ArrayList<AssetTransfer> getAssetList(){
        return assetList;
    }

    public void setAssetList(ArrayList<AssetTransfer> assetList){
        this.assetList = assetList;
    }

    public ContactPerson getContactPerson(){
        return contactPerson;
    }

    public void setContactPerson(ContactPerson contactPerson){
        this.contactPerson = contactPerson;
    }

    public AssetTransferInterface.TransferType getTransferType() {
        return transferType;
    }

    public void setTransferType(AssetTransferInterface.TransferType transferType) {
        this.transferType = transferType;
    }
}
