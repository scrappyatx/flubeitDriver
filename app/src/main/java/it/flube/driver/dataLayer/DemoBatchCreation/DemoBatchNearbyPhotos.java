/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.dataLayer.DemoBatchCreation;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Random;

import it.flube.driver.modelLayer.builders.AddressLocationBuilder;
import it.flube.driver.modelLayer.builders.BatchHolderBuilder;
import it.flube.driver.modelLayer.builders.BuilderUtilities;
import it.flube.driver.modelLayer.builders.DestinationBuilder;
import it.flube.driver.modelLayer.builders.DisplayDistanceBuilder;
import it.flube.driver.modelLayer.builders.DisplayTimingBuilder;
import it.flube.driver.modelLayer.builders.LatLonLocationBuilder;
import it.flube.driver.modelLayer.builders.PhotoRequestBuilder;
import it.flube.driver.modelLayer.builders.PhotoStepBuilder;
import it.flube.driver.modelLayer.builders.PotentialEarningsBuilder;
import it.flube.driver.modelLayer.builders.ServiceOrderScaffoldBuilder;
import it.flube.driver.modelLayer.entities.AddressLocation;
import it.flube.driver.modelLayer.entities.PotentialEarnings;
import it.flube.driver.modelLayer.entities.Destination;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.modelLayer.entities.LatLonLocation;
import it.flube.driver.modelLayer.builders.NavigationStepBuilder;
import it.flube.driver.modelLayer.entities.batch.BatchDetail;
import it.flube.driver.modelLayer.entities.batch.BatchHolder;
import it.flube.driver.modelLayer.interfaces.DemoBatchInterface;

/**
 * Created on 8/16/2017
 * Project : Driver
 */

public class DemoBatchNearbyPhotos implements DemoBatchInterface {
    private static final String TAG = "DemoBatchNearbyPhotos";
    private ArrayList<String> batchTitleList;
    public DemoBatchNearbyPhotos(){
        createBatchTitle();
    }

    public BatchHolder createDemoBatch(@NonNull Driver driver) {
        // if user doesn't supply a batchGUID, we create one
        return createBatch(driver, BuilderUtilities.generateGuid());
    }

    public BatchHolder createDemoBatch(@NonNull Driver driver, @NonNull String batchGuid){
        //use the batchGuid the user supplied
        return createBatch(driver, batchGuid);
    }

    public BatchHolder createBatch(@NonNull Driver driver, @NonNull String batchGuid) {

        return new BatchHolderBuilder.Builder()
                .batchType(BatchDetail.BatchType.MOBILE_DEMO)
                .claimStatus(BatchDetail.ClaimStatus.NOT_CLAIMED)
                .guid(batchGuid)
                .title(getRandomTitle())
                .description("some description text")
                .iconUrl(getRandomIconUrl())
                .displayTiming(new DisplayTimingBuilder.Builder()
                        .date("Today")
                        .hours("9:30 am - 12:00 pm")
                        .duration("2.5 hours")
                        .offerExpiryDate("Claim by today, 3:00 pm")
                        .build())
                .displayDistance(new DisplayDistanceBuilder.Builder()
                        .distanceToTravel("18 miles")
                        .distanceIndicatorUrl(getRandomDistanceIndicatorUrl())
                        .build())
                .potentialEarnings(new PotentialEarningsBuilder.Builder()
                        .payRateInCents(2800)
                        .earningsType(PotentialEarnings.EarningsType.FIXED_FEE)
                        .plusTips(true)
                        .build())
                .expectedStartTime(BuilderUtilities.getNowDate())
                .expectedFinishTime(BuilderUtilities.getFutureDate(150))
                .addServiceOrder(new ServiceOrderScaffoldBuilder.Builder()
                        .title("DEMO ORDER")
                        .description("Walk to the destination and take a photo")
                        .startTime(BuilderUtilities.getNowDate())
                        .finishTime(BuilderUtilities.getFutureDate(30))
                        .addStep(new NavigationStepBuilder.Builder()
                                .title("Go to end of street")
                                .description("Navigate to the end of the street")
                                .startTime(BuilderUtilities.getNowDate())
                                .finishTime(BuilderUtilities.getNowDate(),10)
                                .destination(new DestinationBuilder.Builder()
                                        .targetAddress(getAddressByClientId(driver.getClientId()))
                                        .targetLatLon(getLatLonLocationByClientID(driver.getClientId()))
                                        .targetType(Destination.DestinationType.OTHER)
                                        .build())
                                .milestoneWhenFinished("Arrived At Destination")
                                .build())
                        .addStep(new PhotoStepBuilder.Builder()
                                .title("Take three photos")
                                .description("Take three photos of things around you")
                                .startTime(BuilderUtilities.getNowDate(), 10)
                                .finishTime(BuilderUtilities.getNowDate(), 20)
                                .milestoneWhenFinished("Photos Taken")
                                .addPhotoRequest(new PhotoRequestBuilder.Builder()
                                        .title("First Photo")
                                        .description("This is the first photo to take")
                                        .build())
                                .addPhotoRequest(new PhotoRequestBuilder.Builder()
                                        .title("Second Photo")
                                        .description("This is the second photo to take")
                                        .build())
                                .addPhotoRequest(new PhotoRequestBuilder.Builder()
                                        .title("Third Photo")
                                        .description("This is the third photo to take")
                                        .build())
                                .build())
                        .build())
                .build();
    }





    private AddressLocation getAddressByClientId(@NonNull String clientId){
        String street;
        String city;
        String state;
        String zip;

        switch (clientId) {
            case "5904da5fff2f3a2fd19d3cf6":
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
            case "59409679ff2f3a45ba272dad":
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
            case "597b2f107729e871ed1775cd":
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
            case "597b2fa17729e871ed1775ce":
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



    private LatLonLocation getLatLonLocationByClientID(@NonNull String clientId){
        Double latitude;
        Double longitude;

        switch (clientId) {
            case "5904da5fff2f3a2fd19d3cf6":
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
            case "59409679ff2f3a45ba272dad":
                //bryan godwin
                //2001 summercrest cove, round rock tx 78681
                // lat lon = (30.545792, -97.757828)
                //
                // nearby: 4318 south summercrest loop, round rock tx 78681
                // lat lon =(30.546022, -97.75694)
                latitude = 30.546022;
                longitude = -97.75694;

                break;
            case "597b2f107729e871ed1775cd":
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
            case "597b2fa17729e871ed1775ce":
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

    public String getRandomDistanceIndicatorUrl(){
        ArrayList<String> urlList = new ArrayList<String>();
        Random random = new Random();
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_long_ver2.png?alt=media&token=3e95c94d-9a53-4be3-acdb-7efc0ed83339");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_med_ver2.png?alt=media&token=dc311b19-d4f2-491d-af3b-f85fa5c0c019");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/distanceIndicators%2Fdistance_short_ver2.png?alt=media&token=aad103c9-a6ca-42ef-b086-74547bafe92e");

        return urlList.get(random.nextInt(urlList.size()));
    }

    public String getRandomIconUrl(){
        ArrayList<String> urlList = new ArrayList<String>();
        Random random = new Random();

        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Foil-change-icon.png?alt=media&token=b1599ce2-67ec-4bda-9a4b-d3a4ae8cdea3");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fkwikkar.PNG?alt=media&token=e60e8956-bb62-4758-abab-811cd1e6546b");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fjiffy-lube_ver2.png?alt=media&token=d4d79f18-1d4c-463f-98f3-11b3572bfd37");
        urlList.add("https://firebasestorage.googleapis.com/v0/b/flubeitdriver.appspot.com/o/serviceProviderImages%2Fexpressoilchange.PNG?alt=media&token=4414e2f8-59ca-474d-95aa-4c7ee424481b");

        return urlList.get(random.nextInt(urlList.size()));
    }

    public String getRandomTitle(){
        Random random = new Random();
        return batchTitleList.get(random.nextInt(batchTitleList.size()));
    }

    public void createBatchTitle(){
        batchTitleList = new ArrayList<String>();

        batchTitleList.add("The Terror in the Rhine");
        batchTitleList.add("The Purrfect Bachelor");
        batchTitleList.add("Threshold of Illusion");
        batchTitleList.add("A Clue in the Country");
        batchTitleList.add("Telltale Pattern");
        batchTitleList.add("Enchantment in June");
        batchTitleList.add("Appleby and the Playboy");
        batchTitleList.add("A Minister's Romance");
        batchTitleList.add("A Human Kept");
        batchTitleList.add("A Gallant Pursuit");
        batchTitleList.add("The Apple of Illusion");
        batchTitleList.add("Thorn of Alpha");
        batchTitleList.add("The Essential Killing");
        batchTitleList.add("Banker's Vineyard");
        batchTitleList.add("The Wayward Jeopardy");
        batchTitleList.add("A Dog's Duty");
        batchTitleList.add("The Hard Unchained");
        batchTitleList.add("The Bodyguard's Sword");
        batchTitleList.add("The Bride's Bodyguard");
        batchTitleList.add("A Trio for Lute");
        batchTitleList.add("Master and Honesty");
        batchTitleList.add("Alibi for a Kidnapping");
        batchTitleList.add("The Legendary Road");
        batchTitleList.add("The Officer's Pass");
        batchTitleList.add("A Lover Affair");
        batchTitleList.add("A Sliver of Evidence");
        batchTitleList.add("Uncharted Guns");
        batchTitleList.add("A Rhythm Tempest");
        batchTitleList.add("An Unconventional Presumption");
        batchTitleList.add("The Gift of the Angel");
        batchTitleList.add("Infinite Companions");
        batchTitleList.add("Foul Showers");
        batchTitleList.add("The Clouds of Abilene");
        batchTitleList.add("Taste of Innocence");
        batchTitleList.add("Lucifer and the Duke");
        batchTitleList.add("Black Reluctant");
        batchTitleList.add("Empty Remains");
        batchTitleList.add("Dead for a Gypsy");
        batchTitleList.add("Sins of Rage");
        batchTitleList.add("The Twelve Raiders");
        batchTitleList.add("Giant Names");
        batchTitleList.add("Hallowed Desert");
        batchTitleList.add("A Holiday Business");
        batchTitleList.add("Accidental Alliances");
        batchTitleList.add("A Villain's Ascension");
        batchTitleList.add("Tongue in Silver");
        batchTitleList.add("Rage of Dawn");
        batchTitleList.add("Dead for a Dealer");
        batchTitleList.add("Menace in the Kitchen");
        batchTitleList.add("Imperial Windows");
        batchTitleList.add("Absolute Response");
        batchTitleList.add("Cavanaugh on Horseback");
        batchTitleList.add("The Salt Spies");
        batchTitleList.add("Fugitive's Breakout");
        batchTitleList.add("Covenant of the Pack");
        batchTitleList.add("A Single's Claim");
        batchTitleList.add("Metal Foe");
        batchTitleList.add("Bungalow Charity");
        batchTitleList.add("Maigret and the Lawman");
        batchTitleList.add("Blood of Rocks");
        batchTitleList.add("The Depth Target");
        batchTitleList.add("Afterglow and Day");
        batchTitleList.add("Saint's Fortune");
        batchTitleList.add("The Mulberry Hex");
        batchTitleList.add("The Sheik's Hotel");
        batchTitleList.add("Tarzan Reckless");
        batchTitleList.add("The Termination Barrier");
        batchTitleList.add("Dust on the Massacre");
        batchTitleList.add("The Brotherhood of the Unicorn");
        batchTitleList.add("The Hero Cake");
        batchTitleList.add("Watch and Deception");
        batchTitleList.add("Days of Anger");
        batchTitleList.add("Changer of Gods");
        batchTitleList.add("Fang and Ash");
        batchTitleList.add("Jack the Champion");
        batchTitleList.add("Gentlemen of the Sun");
        batchTitleList.add("Pillow of the Town");
        batchTitleList.add("Mayhem in the Afterglow");
        batchTitleList.add("Shadows of the Virgin");
        batchTitleList.add("Rebel by Consent");
        batchTitleList.add("A Marriage of Evil");
        batchTitleList.add("Marooned in Blue");
        batchTitleList.add("The Rapture of the Army");
        batchTitleList.add("Hallowed Desert");
        batchTitleList.add("Hero by Command");
        batchTitleList.add("The Maverick's Nose");
        batchTitleList.add("Little Pregnant");
        batchTitleList.add("The Bear's Mistake");
        batchTitleList.add("Painted Detectives");
        batchTitleList.add("Reality Treat");
        batchTitleList.add("Merlin's Prayer");
        batchTitleList.add("Screen Bodies");
        batchTitleList.add("The Escape Cord");
        batchTitleList.add("The Utopia Boss");
        batchTitleList.add("A Reunion Wife");
        batchTitleList.add("An Atomic Thief");
        batchTitleList.add("The Bloody Nabob");
        batchTitleList.add("The Currents of Cthulhu");
        batchTitleList.add("Terms of Prey");
        batchTitleList.add("The Sword of Guilt");
        batchTitleList.add("Confidential Swarm");
        batchTitleList.add("The Lion's Man");
        batchTitleList.add("The Cats of Evangeline");
        batchTitleList.add("Canary Crusade");
        batchTitleList.add("Tarzan the Tiger");
        batchTitleList.add("Caroline and the Mayor");
        batchTitleList.add("Serpent's Anthem");
        batchTitleList.add("A Familiar Guardian");
        batchTitleList.add("The Face of the Century");
        batchTitleList.add("The Legion of Mankind");
        batchTitleList.add("The Body in the Morning");
        batchTitleList.add("Crown of Masks");
        batchTitleList.add("The Jewels of Summer");
        batchTitleList.add("Widow for Beauty");
        batchTitleList.add("An Indomitable Ruse");
        batchTitleList.add("The Phoenix and the Playboy");
        batchTitleList.add("Vine of the Betrayer");
        batchTitleList.add("Kingmaker's Bane");
        batchTitleList.add("Prisoner on the Ladder");
        batchTitleList.add("The Oracle Heroes");
        batchTitleList.add("The Child's Violin");
        batchTitleList.add("Rx for Blackshirt");
        batchTitleList.add("Thug Peak");
        batchTitleList.add("Tides of Dune");
        batchTitleList.add("The Old Gods");
        batchTitleList.add("The Ragged Brother");
        batchTitleList.add("The Comet Feathers");
        batchTitleList.add("The Book of the Law");
        batchTitleList.add("Imperial H");
        batchTitleList.add("Santa's Labyrinth");
        batchTitleList.add("The Babe and the Miss");
        batchTitleList.add("Illusions of Orion");
        batchTitleList.add("A Witch's Pursuit");
        batchTitleList.add("A Candle in the Storm");
        batchTitleList.add("Wedding Heirs");
        batchTitleList.add("Dance for a Blonde");
        batchTitleList.add("Gravity Daughters");
        batchTitleList.add("Terms of Isis");
        batchTitleList.add("The Evil Train");
        batchTitleList.add("Whisper in the Limelight");
        batchTitleList.add("Brush with Love");
        batchTitleList.add("A Cosmic Waltz");
        batchTitleList.add("Radiant Treasures");
        batchTitleList.add("Sources of the Tower");

    }


}
