/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.entities.orderStep;

import java.util.ArrayList;
import java.util.HashMap;

import it.flube.libbatchdata.entities.ContactPerson;

import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.assetTransfer.AssetTransfer;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import it.flube.libbatchdata.interfaces.OrderStepInterface;

/**
 * Created on 8/9/2017
 * Project : Driver
 */

public class ServiceOrderReceiveAssetStep extends AbstractStep implements OrderStepInterface {

    private HashMap<String, AssetTransfer> assetList;
    private ContactPerson contactPerson;
    private AssetTransferInterface.TransferType transferType;

    private Boolean requireSignature;
    private SignatureRequest signatureRequest;

    /// implement the getters/setters for the unique data in this step type
    public HashMap<String, AssetTransfer> getAssetList(){
        return assetList;
    }

    public void setAssetList(HashMap<String, AssetTransfer> assetList){
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

    public Boolean getRequireSignature() {
        return requireSignature;
    }

    public void setRequireSignature(Boolean requireSignature) {
        this.requireSignature = requireSignature;
    }

    public SignatureRequest getSignatureRequest() {
        return signatureRequest;
    }

    public void setSignatureRequest(SignatureRequest signatureRequest) {
        this.signatureRequest = signatureRequest;
    }
}
