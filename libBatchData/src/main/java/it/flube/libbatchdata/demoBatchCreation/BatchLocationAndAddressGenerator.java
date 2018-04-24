/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.demoBatchCreation;

import it.flube.libbatchdata.builders.AddressLocationBuilder;
import it.flube.libbatchdata.builders.LatLonLocationBuilder;
import it.flube.libbatchdata.entities.AddressLocation;
import it.flube.libbatchdata.entities.LatLonLocation;

/**
 * Created on 4/23/2018
 * Project : Driver
 */
public class BatchLocationAndAddressGenerator {
    /// CORY client IDs
    private static final String COCOPEBBLE_TEST_AT_TEST_COM_CLIENT_ID = "UrA04KD3kfQfF7W6ljQ3jeukuAi2";
    private static final String CORYPLUSPLUS_AT_GMAIL_COM_CLIENT_ID = "GG85NOrJ7MOpRn0Xk97hRza5V8k2";

    // BRYAN CLIENT IDs
    private static final String TESTTT_AT_TEST_COM_CLIENT_ID = "Gg1g9iiHDjTqRhxEFoOLZ3DWOzw1";
    private static final String GODWINBW_AT_YAHOO_COM_CLIENT_ID = "EtqQbEGuwrdTOjeGLbUK5b845R03";

    // SEAN CLIENT IDs
    private static final String SHADYGUY_AT_TEST_COM_CLIENT_ID = "ftEt6rBZ31gp2AjJ3ludOFNF9Dp2";
    private static final String FLUBER_THREE_AT_GMAIL_COM_CLIENT_ID = "wXDKdJU1vvMcJPLzEWPewVc1JKG3";

    // CAROLINE CLIENT IDs
    private static final String TARDPUP_AT_TEST_COM_CLIENT_ID = "a7ES7oawh9cbmAGKN93c0g41Izk1";


    public static AddressLocation getAddressByClientId(String clientId){
        String street;
        String city;
        String state;
        String zip;

        switch (clientId) {
            case COCOPEBBLE_TEST_AT_TEST_COM_CLIENT_ID: //cocopebble@test.com
            case CORYPLUSPLUS_AT_GMAIL_COM_CLIENT_ID: //coryplusplus@gmail.com

                // cory kelly
                // 607 Hyde Park Place, Austin TX 78748
                // lat lon = (30.176713, -97.798745)
                //
                // nearby:
                // 617 Hyde Park Place, Austin TX 78747
                // lat lon = (30.176970, -97.799686)

                street = "617 Hyde Park Place";
                city = "Austin";
                state = "TX";
                zip = "78747";
                break;
            case TESTTT_AT_TEST_COM_CLIENT_ID:  //testtt@test.com
            case GODWINBW_AT_YAHOO_COM_CLIENT_ID: //godwinbw@yahoo.com
                //bryan godwin
                //2001 summercrest cove, round rock tx 78681
                // lat lon = (30.545792, -97.757828)
                //
                // nearby: 4318 south summercrest loop, round rock tx 78681
                // lat lon =(30.546022, -97.75694)

                street = "4318 South Summercrest Loop";
                city = "Round Rock";
                state = "TX";
                zip = "78681";
                break;
            case TARDPUP_AT_TEST_COM_CLIENT_ID:
                // caroline godwin
                // 9359 Lincoln Blvd #3247, Los Angeles CA 90045
                // lat lon (33.954765, -118.414605)
                //
                // nearby:
                // 9253 pacific coast highway, los angeles ca 90045
                // lat lon = (33.956787, -118.416301)
                street = "9253 Pacific Coast Highway";
                city = "Los Angeles";
                state = "CA";
                zip = "90045";


                break;
            case SHADYGUY_AT_TEST_COM_CLIENT_ID: //shadyguy@test.com
            case FLUBER_THREE_AT_GMAIL_COM_CLIENT_ID: //fluberthree@gmail.com
                // sean howell
                // 2020 E 2nd St Unit A, Austin TX 78702
                // lat lon =(30.257000, -97.721278)
                //
                // nearby:
                // 200 Caney Street, Austin TX 78702
                // lat lon = (30.256994, -97.721284)

                street = "200 Caney Street";
                city = "Austin";
                state = "TX";
                zip = "78702";

                break;
            default :
                // 202 East 35th Street, Austin, TX
                // lat lon = (30.298974, -97.733049)

                street = "202 East 35th Street";
                city = "Austin";
                state = "TX";
                zip = "78705";
                break;
        }

        return new AddressLocationBuilder.Builder()
                .street(street)
                .city(city)
                .state(state)
                .zip(zip)
                .build();

    }



    public static LatLonLocation getLatLonLocationByClientID(String clientId){
        Double latitude;
        Double longitude;

        switch (clientId) {
            case COCOPEBBLE_TEST_AT_TEST_COM_CLIENT_ID: //cocopebble@test.com
            case CORYPLUSPLUS_AT_GMAIL_COM_CLIENT_ID: //coryplusplus@gmail.com
                // cory kelly
                // 607 Hyde Park Place, Austin TX 78748
                // lat lon = (30.176713, -97.798745)
                //
                // nearby:
                // 617 Hyde Park Place, Austin TX 78747
                // lat lon = (30.176970, -97.799686)

                latitude = 30.176970;
                longitude = -97.799686;
                break;
            case TESTTT_AT_TEST_COM_CLIENT_ID:  //testtt@test.com
            case GODWINBW_AT_YAHOO_COM_CLIENT_ID: //godwinbw@yahoo.com
                //bryan godwin
                //2001 summercrest cove, round rock tx 78681
                // lat lon = (30.545792, -97.757828)
                //
                // nearby: 4318 south summercrest loop, round rock tx 78681
                // lat lon =(30.546022, -97.75694)
                latitude = 30.546022;
                longitude = -97.75694;

                break;
            case TARDPUP_AT_TEST_COM_CLIENT_ID:
                // caroline godwin
                // 9359 Lincoln Blvd #3247, Los Angeles CA 90045
                // lat lon (33.954765, -118.414605)
                //
                // nearby:
                // 9253 pacific coast highway, los angeles ca 90045
                // lat lon = (33.956787, -118.416301)
                latitude = 33.956787;
                longitude = -118.416301;


                break;
            case SHADYGUY_AT_TEST_COM_CLIENT_ID: //shadyguy@test.com
            case FLUBER_THREE_AT_GMAIL_COM_CLIENT_ID: //fluberthree@gmail.com
                // sean howell
                // 2020 E 2nd St Unit A, Austin TX 78702
                // lat lon =(30.257000, -97.721278)
                //
                // nearby:
                // 200 Caney Street, Austin TX 78702
                // lat lon = (30.256994, -97.721284)


                latitude = 30.256994;
                longitude = -97.721284;

                break;
            default :
                // 202 East 35th Street, Austin, TX
                // lat lon = (30.298974, -97.733049)
                latitude = 30.298974;
                longitude = -97.733049;
                break;
        }


        return new LatLonLocationBuilder.Builder()
                .location(latitude, longitude)
                .build();
    }



}
