/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents;

import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents.AssetTransferItemsRowLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.layoutComponents.SignatureRequestRowLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailDueByLayoutComponents;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailSwipeCompleteButtonComponent;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.stepLayoutComponents.StepDetailTitleLayoutComponents;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.SignatureRequest;
import it.flube.libbatchdata.entities.orderStep.ServiceOrderReceiveAssetStep;
import it.flube.libbatchdata.interfaces.AssetTransferInterface;
import ng.max.slideview.SlideView;
import timber.log.Timber;

/**
 * Created on 7/25/2018
 * Project : Driver
 */
public class ReceiveAssetLayoutComponents implements
        StepDetailSwipeCompleteButtonComponent.Response,
        SignatureRequestRowLayoutComponents.Response,
        AssetTransferItemsRowLayoutComponents.Response,
        ContactPersonLayoutComponent.Response {

    private static final String TAG = "ReceiveAssetLayoutComponents";

    private static final String DEFAULT_TRANSFER_TYPE_DISPLAY_TEXT = "Unknown";

    ///
    /// wrapper for UI elements in activity_receive_asset_step.xml
    ///
    private StepDetailTitleLayoutComponents stepTitle;
    private StepDetailDueByLayoutComponents stepDueBy;

    private TextView transferType;

    private AssetTransferItemsRowLayoutComponents itemsRow;
    private SignatureRequestRowLayoutComponents signatureRow;
    private ContactPersonLayoutComponent contact;

    private StepDetailSwipeCompleteButtonComponent stepComplete;

    private ServiceOrderReceiveAssetStep orderStep;
    private Response response;

    public ReceiveAssetLayoutComponents(AppCompatActivity activity, String stepCompleteButtonCaption, Response response){
        this.response = response;

        stepTitle = new StepDetailTitleLayoutComponents(activity);
        stepDueBy = new StepDetailDueByLayoutComponents(activity);
        transferType = (TextView) activity.findViewById(R.id.transfer_type_text);
        itemsRow = new AssetTransferItemsRowLayoutComponents(activity, this);
        signatureRow = new SignatureRequestRowLayoutComponents(activity, this);
        stepComplete = new StepDetailSwipeCompleteButtonComponent(activity, stepCompleteButtonCaption, this);
        contact = new ContactPersonLayoutComponent(activity, this);

        setInvisible();
        Timber.tag(TAG).d("created");
    }


    public void setValues(AppCompatActivity activity, ServiceOrderReceiveAssetStep orderStep){
        this.orderStep = orderStep;
        stepTitle.setValues(activity, orderStep);
        stepDueBy.setValues(activity, orderStep);
        transferType.setText(getTransferTypeDisplayText(activity, orderStep.getTransferType()));
        itemsRow.setValues(activity, orderStep.getAssetList());

        if (orderStep.getRequireSignature()) {
            signatureRow.setValues(orderStep.getSignatureRequest());
        }

        contact.setValues(orderStep.getContactPerson());
        Timber.tag(TAG).d("setValues");
    }

    private String getTransferTypeDisplayText(AppCompatActivity activity, AssetTransferInterface.TransferType transferType){
        Timber.tag(TAG).d("getTransferTypeDisplayText, transferType -> " + transferType.toString());
        String displayText = DEFAULT_TRANSFER_TYPE_DISPLAY_TEXT;

        switch (transferType){
            case TRANSER_TO_CUSTOMER:
                displayText = activity.getResources().getString(R.string.asset_transfer_type_to_customer);
                break;
            case TRANSFER_FROM_CUSTOMER:
                displayText = activity.getResources().getString(R.string.asset_transfer_type_from_customer);
                break;
            case TRANSFER_TO_SERVICE_PROVIDER:
                displayText = activity.getResources().getString(R.string.asset_transfer_type_to_service_provider);
                break;
            case TRANSFER_FROM_SERVICE_PROVIDER:
                displayText = activity.getResources().getString(R.string.asset_transfer_type_from_service_provider);
                break;
            default:
                //should never get here
                Timber.tag(TAG).w("...should never get here");
        }
        return displayText;
    }

    public void showWaitingAnimationAndBanner(AppCompatActivity activity){
        Timber.tag(TAG).d("showWaitingAnimationBanner");
        itemsRow.setInvisible();
        signatureRow.setInvisible();
        contact.setInvisible();
        stepComplete.showWaitingAnimationAndBanner(activity.getString(R.string.receive_asset_step_completed_banner_text));
    }

    public ServiceOrderReceiveAssetStep getOrderStep(){
        Timber.tag(TAG).d("ServiceOrderReceiveAssetStep");
        return this.orderStep;
    }

    public void setCalling(){
        contact.setCalling();
    }

    public ContactPerson getContactPerson(){
        return contact.getContactPerson();
    }

    public void setVisible(Boolean hasPermissionToCall){
        if (orderStep != null) {
            stepTitle.setVisible();
            stepDueBy.setVisible();
            transferType.setVisibility(View.INVISIBLE);
            itemsRow.setVisible();
            signatureRow.setVisible();
            contact.setVisible(hasPermissionToCall);
            if (itemsRow.allTransfersAreComplete() && signatureRow.signatureIsComplete()) {
                stepComplete.setVisible();
            } else {
                stepComplete.setGone();
            }
        } else {
            //don't have an order step
            stepTitle.setInvisible();
            stepDueBy.setInvisible();
            transferType.setVisibility(View.INVISIBLE);
            itemsRow.setInvisible();
            signatureRow.setInvisible();
            stepComplete.setInvisible();
            contact.setInvisible();
        }
        Timber.tag(TAG).d("setVisible");
    }

    public void setInvisible(){
        stepTitle.setInvisible();
        stepDueBy.setInvisible();
        transferType.setVisibility(View.INVISIBLE);
        itemsRow.setInvisible();
        signatureRow.setInvisible();
        contact.setInvisible();
        stepComplete.setInvisible();
        Timber.tag(TAG).d("setInvisible");
    }

    public void setGone(){
        stepTitle.setGone();
        stepDueBy.setGone();
        transferType.setVisibility(View.GONE);
        itemsRow.setGone();
        signatureRow.setGone();
        contact.setGone();
        stepComplete.setGone();
        Timber.tag(TAG).d("setGone");
    }

    public void close(){
        stepTitle.close();
        stepDueBy.close();
        itemsRow.close();
        signatureRow.close();
        contact.close();
        stepComplete.close();

        stepTitle=null;
        stepDueBy=null;
        itemsRow=null;
        signatureRow=null;
        stepComplete=null;
        transferType=null;
        response = null;

        Timber.tag(TAG).d("closed");
    }

    ///
    /// interface for SignatureRequestRowLayoutComponents
    ///
    public void signatureRowClicked(SignatureRequest signatureRequest){
        Timber.tag(TAG).d("signatureRowClicked");
        response.signatureRowClicked(signatureRequest);
    }

    ///
    /// interface for AssetTransferItemsRowLayoutComponents
    ///
    public void itemRowClicked(){
        Timber.tag(TAG).d("itemRowClicked, number of items -> " + orderStep.getAssetList().size());
        if (orderStep.getAssetList().size() > 1){
            response.itemsRowClickedWithMultipleItems();
        } else {
            Timber.tag(TAG).d("  ...assetGuid -> " + orderStep.getAssetList().entrySet().iterator().next().getKey());
            response.itemsRowClickedWithOneItem(orderStep.getAssetList().entrySet().iterator().next().getKey());
        }
    }

    ///
    /// interface for ContactPersonLayoutComponents.Response
    ///
    public void textButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("textButtonClicked");
        response.contactPersonTextClicked(dialPhoneNumber);
    }

    public void callButtonClicked(String dialPhoneNumber){
        Timber.tag(TAG).d("callButtonClicked");
        response.contactPersonCallClicked(dialPhoneNumber);
    }

    public void appInfoButtonClicked(){
        Timber.tag(TAG).d("appInfoButtonClicked");
        response.appInfoClicked();
    }

    /// OnSlideCompleteListener interface
    public void stepDetailSwipeCompleteButtonClicked(){
        Timber.tag(TAG).d("stepDetailSwipeCompleteButtonClicked");
        response.stepCompleteClicked(orderStep);
    }

    public interface Response {
        void signatureRowClicked(SignatureRequest signatureRequest);

        void itemsRowClickedWithMultipleItems();

        void itemsRowClickedWithOneItem(String assetGuid);

        void contactPersonCallClicked(String dialPhoneNumber);

        void contactPersonTextClicked(String dialPhoneNumber);

        void stepCompleteClicked(ServiceOrderReceiveAssetStep orderStepd);

        void appInfoClicked();
    }


}
