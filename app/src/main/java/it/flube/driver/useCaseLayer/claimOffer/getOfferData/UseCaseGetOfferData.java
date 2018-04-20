/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.useCaseLayer.claimOffer.getOfferData;

import java.util.ArrayList;

import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.userInterfaceLayer.activities.offers.OfferConstants;
import it.flube.libbatchdata.entities.RouteStop;
import it.flube.libbatchdata.entities.batch.BatchDetail;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

/**
 * Created on 4/10/2018
 * Project : Driver
 */
public class UseCaseGetOfferData implements
        Runnable,
        UseCaseGetDemoOfferData.Response,
        UseCaseGetPublicOfferData.Response,
        UseCaseGetPersonalOfferData.Response {

    private final static String TAG = "UseCaseGetOfferData";


    private final MobileDeviceInterface device;
    private final String batchGuid;
    private final OfferConstants.OfferType offerType;
    private final Response response;


    public UseCaseGetOfferData(MobileDeviceInterface device, String batchGuid, OfferConstants.OfferType offerType, Response response){
          this.device = device;
          this.offerType = offerType;
          this.batchGuid = batchGuid;
          this.response = response;

          Timber.tag(TAG).d("offerType -> " + offerType.toString());
          Timber.tag(TAG).d("batchGuid -> " + batchGuid);

    }

    public void run(){
        Timber.tag(TAG).d("Thread -> " + Thread.currentThread().getName());
        Timber.tag(TAG).d("   ...offerType -> " + offerType);

        switch (offerType) {
            case DEMO:
                new UseCaseGetDemoOfferData(device, batchGuid, this).run();
                break;
            case PUBLIC:
                new UseCaseGetPublicOfferData(device, batchGuid, this).run();
                break;
            case PERSONAL:
                new UseCaseGetPersonalOfferData(device, batchGuid, this).run();
                break;
            default:
                response.getOfferDataFailure();
                break;
        }
    }

    public void getOfferDataSuccess(BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList){
        Timber.tag(TAG).d("   ...getOfferDataSuccess");
        response.getOfferDataSuccess(offerType, batchDetail, orderList, routeList);
    }

    public void getOfferDataFailure(){
        Timber.tag(TAG).d("   ...getOfferDataFailure");
        response.getOfferDataFailure();
    }

    public interface Response {
        void getOfferDataSuccess(OfferConstants.OfferType offerType, BatchDetail batchDetail, ArrayList<ServiceOrder> orderList, ArrayList<RouteStop> routeList);

        void getOfferDataFailure();
    }
}
