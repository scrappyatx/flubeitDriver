/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.offers.testOffers;

import java.util.ArrayList;

import it.flube.libbatchdata.interfaces.DemoBatchInterface;
import timber.log.Timber;

/**
 * Created on 6/27/2018
 * Project : Driver
 */
public class TestOfferUtilities {
    private static final String TAG = "TestOfferUtilities";

    private static final String ITEM_1_OPTION_NAME = "Single Step -> Authorize Payment";
    private static final String ITEM_1_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepAuthorizePayment";

    private static final String ITEM_2_OPTION_NAME = "Single Step -> Give Asset with Signature";
    private static final String ITEM_2_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepGiveAssetWithSignature";

    private static final String ITEM_3_OPTION_NAME = "Single Step -> Navigation";
    private static final String ITEM_3_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepNavigation";

    private static final String ITEM_4_OPTION_NAME = "Single Step -> One Photo";
    private static final String ITEM_4_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepOnePhoto";

    private static final String ITEM_5_OPTION_NAME = "Single Step -> Three Photos";
    private static final String ITEM_5_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepThreePhoto";

    private static final String ITEM_6_OPTION_NAME = "Single Step -> Vehicle Photos";
    private static final String ITEM_6_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepVehiclePhotos";

    private static final String ITEM_7_OPTION_NAME = "Single Step -> Receive Asset with Signature";
    private static final String ITEM_7_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepReceiveAssetSignature";

    private static final String ITEM_8_OPTION_NAME = "Single Step -> User Trigger";
    private static final String ITEM_8_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchSingleStepUserTrigger";

    private static final String ITEM_9_OPTION_NAME = "Two Service Orders -> Single Step Each Order";
    private static final String ITEM_9_CLASS_NAME = "it.flube.libbatchdata.demoBatchCreation.DemoBatchTwoServiceOrderSingleStep";

    private static final String ITEM_10_OPTION_NAME = "";
    private static final String ITEM_10_CLASS_NAME = "";

    private static final String ITEM_11_OPTION_NAME = "";
    private static final String ITEM_11_CLASS_NAME = "";

    private static final String ITEM_12_OPTION_NAME = "";
    private static final String ITEM_12_CLASS_NAME = "";


    public static ArrayList<TestOfferOption> getOptionsList() {
        ArrayList<TestOfferOption> optionsList = new ArrayList<TestOfferOption>();

        optionsList.add(getOfferOption(ITEM_1_OPTION_NAME, ITEM_1_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_2_OPTION_NAME, ITEM_2_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_3_OPTION_NAME, ITEM_3_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_4_OPTION_NAME, ITEM_4_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_5_OPTION_NAME, ITEM_5_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_6_OPTION_NAME, ITEM_6_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_7_OPTION_NAME, ITEM_7_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_8_OPTION_NAME, ITEM_8_CLASS_NAME));
        optionsList.add(getOfferOption(ITEM_9_OPTION_NAME, ITEM_9_CLASS_NAME));
        //optionsList.add(getOfferOption(ITEM_10_OPTION_NAME, ITEM_10_CLASS_NAME));
        //optionsList.add(getOfferOption(ITEM_11_OPTION_NAME, ITEM_11_CLASS_NAME));
        //optionsList.add(getOfferOption(ITEM_12_OPTION_NAME, ITEM_12_CLASS_NAME));

        return optionsList;
    }

    private static TestOfferOption getOfferOption(String optionName, String className){
        TestOfferOption option = new TestOfferOption();
        option.setOptionName(optionName);
        option.setClassName(className);
        return option;
    }

    public static DemoBatchInterface getClassByName(String classname){
        try {
            Timber.tag(TAG).d("getClassByName START...");
            Timber.tag(TAG).d("   ...classname -> " + classname);
            Class myClass = Class.forName(classname);

            Timber.tag(TAG).d("   ...found class, returning instance");
            return (DemoBatchInterface) myClass.newInstance();

        } catch (ClassNotFoundException e) {
            Timber.tag(TAG).e(e);
        } catch (InstantiationException e) {
            Timber.tag(TAG).e(e);
        } catch (IllegalAccessException e) {
            Timber.tag(TAG).e(e);
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
        return null;
    }

}
