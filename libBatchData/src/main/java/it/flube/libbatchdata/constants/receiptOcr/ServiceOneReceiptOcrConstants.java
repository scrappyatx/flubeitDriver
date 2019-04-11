/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.constants.receiptOcr;

/**
 * Created on 3/8/2019
 * Project : Driver
 */
public class ServiceOneReceiptOcrConstants {

    public static final Boolean HAS_TRANSACTION_ID = true;
    public static final Boolean HAS_VEHICLE_ID = true;
    public static final Boolean HAS_VEHICLE_MILEAGE = true;
    public static final Boolean HAS_VEHICLE_VIN = true;
    public static final Boolean HAS_SERVICE_CHECKLIST = false;
    public static final Boolean HAS_TOTAL_CHARGED = true;

    public static final String TRANSACTION_ID_START_SENTINAL = "TRANSACTION NO";
    public static final String TRANSACTION_ID_END_SENTINAL = "INVOICE NO";
    public static final String TRANSACTION_ID_PATTERN = "[0-9]{14}";

    public static final String VEHICLE_ID_START_SENTINAL = "VEHICLE ID";
    public static final String VEHICLE_ID_END_SENTINAL = "Customer Information";
    public static final String VEHICLE_ID_PATTERN = "[A-Za-z]{2}[-][A-Za-z0-9]{1,10}";

    public static final String VEHICLE_MILEAGE_START_SENTINAL = "MILEAGE";
    public static final String VEHICLE_MILEAGE_END_SENTINAL = "ALT ID";
    public static final String VEHICLE_MILEAGE_PATTERN = "[0-9]{1,6}";

    public static final String VEHICLE_VIN_START_SENTINAL = "VIN";
    public static final String VEHICLE_VIN_END_SENTINAL = "MILEAGE";
    public static final String VEHICLE_VIN_PATTERN = "[A-HJ-NPR-Z0-9]{17}";

    public static final String SERVICE_CHECKLIST_START_SENTINAL = "";
    public static final String SERVICE_CHECKLIST_END_SENTINAL = "";
    public static final String SERVICE_CHECKLIST_PATTERN = "";

    public static final String TOTAL_CHARGED_START_SENTINAL = "TOTAL";
    public static final String TOTAL_CHARGED_END_SENTINAL = "CHANGE";
    public static final String TOTAL_CHARGED_PATTERN = "[$][0-9]{1,3}[.][0-9]{2}";

}
