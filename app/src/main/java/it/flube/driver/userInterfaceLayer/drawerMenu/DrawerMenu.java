/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.CompoundButton;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.holder.BadgeStyle;
import com.mikepenz.materialdrawer.holder.StringHolder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.SectionDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.AndroidDevice;
import it.flube.driver.modelLayer.entities.driver.Driver;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchFinishedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchRemovedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedBatchWaitingToFinishEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedNoBatchEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedStepStartedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchCountUpdateEvent;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceEventHandler;
import it.flube.libbatchdata.builders.BuilderUtilities;
import timber.log.Timber;

/**
 * Created on 5/23/2017
 * Project : Driver
 */

public class DrawerMenu implements
        DriverManager.Response {
    ///  Singleton class using Initialization-on-demand holder idiom
    ///  ref: https://en.wikipedia.org/wiki/Initialization-on-demand_holder_idiom

    private static class Loader {
        static volatile DrawerMenu instance = new DrawerMenu();
    }

    ///
    ///  constructor is private, instances can only be created internally by the class
    ///
    private DrawerMenu() {
        objectGuid = BuilderUtilities.generateGuid();

        driverManager = new DriverManager(this);



        activity = null;
        gotActivity = false;



        Timber.tag(TAG).d("created (%s)", objectGuid);
    }

    ///
    ///  getInstance() provides access to the singleton instance outside the class
    ///
    public static DrawerMenu getInstance() {
        return DrawerMenu.Loader.instance;
    }


    private static final String TAG = "DrawerMenu";

    private static final Integer ID_HOME = 1;

    private static final Integer ID_BATCH_ITINERARY=2;
    private static final Integer ID_ORDER_ITINERARY=3;
    private static final Integer ID_ORDER_STEP=4;
    private static final Integer ID_MESSAGES = 5;

    private static final Integer ID_PUBLIC_OFFERS = 6;
    private static final Integer ID_PERSONAL_OFFERS = 7;
    private static final Integer ID_DEMO_OFFERS = 8;

    private static final Integer ID_SCHEDULED_WORK = 9;

    private static final Integer ID_EARNINGS = 10;
    private static final Integer ID_ACCOUNT = 11;
    private static final Integer ID_HELP = 12;

    private static final Integer ID_DEV_TEST_OFFERS = 13;
    private static final Integer ID_DEV_TEST_EARNINGS = 14;
    //private static final Integer ID_BATCH_MESSAGING = 15;
    //private static final Integer ID_ACTIVE_BATCH_MESSAGING = 16;


    private Drawer drawer;

    private AppCompatActivity activity;
    private Boolean gotActivity;

    private DriverManager driverManager;

    private Toolbar toolbar;
    private SwitchCompat toolbarSwitch;

    private UserInterfaceEventHandler alertEventHandler;

    private String objectGuid;



    /////
    //// Activity functions
    ////
    public void setActivity(@NonNull AppCompatActivity activity, int titleId){
        this.activity = activity;

        toolbar = new DrawerManager().createToolbar(activity, titleId);
        createDrawer(toolbar);

        if (!EventBus.getDefault().isRegistered(this)){
            EventBus.getDefault().register(this);

        } else {
            Timber.tag(TAG).w("...eventBus already registered, this shouldn't happen");
        }


        alertEventHandler = new UserInterfaceEventHandler(activity);
        Timber.tag(TAG).d("setActivity (%s)", objectGuid);
    }

    public void clearActivity(){
        close();
        Timber.tag(TAG).d("clearActivity (%s)", objectGuid);
    }


    public void close() {
        //process no more events for this activity
        EventBus.getDefault().unregister(this);

        drawer = null;
        activity = null;
        toolbar = null;
        toolbarSwitch = null;
        alertEventHandler.close();

        Timber.tag(TAG).d("close (%s)", objectGuid);
    }



    private void createDrawer(Toolbar toolbar) {
        //setup navigation drawer
        drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .withTranslucentStatusBar(false)
                .withFullscreen(false)
                .withAccountHeader(driverManager.getAccountHeader(activity))
                .withSelectedItem(-1)
                .build();

        addHomeMenuItems();

        addMessageMenuItems();

        if (AndroidDevice.getInstance().getActiveBatch().hasActiveBatch()){
            addActiveBatchMenuItems();
        }

        addOffersMenuItems();
        addScheduledWorkMenuItems();
        addEarningsAndAccountMenuItems();
        addHelpMenuItems();

        if (driverManager.hasDriver()){
            if (driverManager.getDriver().getUserRoles().getDev()) {
                addDeveloperToolsMenuItems();
            } else {
                if (driverManager.getDriver().getUserRoles().getQa()){
                    addQaToolsMenuItems();
                }
            }
        }

        updatePersonalOffersCount();
        updatePublicOffersCount();
        updateDemoOffersCount();
        updateScheduledBatchesCount();

    }

    public void driverManangerRedoAccountHeader(Driver driver){
        Timber.tag(TAG).d("driverManangerRedoAccountHeader (%s)", objectGuid);
        createDrawer(toolbar);
    }

    private void addHomeMenuItems(){
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_home).withIcon(FontAwesome.Icon.faw_home)
                .withIdentifier(ID_HOME).withSelectable(false).withOnDrawerItemClickListener(new HomeItemClickListener()));

        drawer.addItem( new DividerDrawerItem());
    }

    private void addMessageMenuItems(){
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_messages).withIcon(FontAwesome.Icon.faw_comments)
                .withIdentifier(ID_MESSAGES).withSelectable(false).withOnDrawerItemClickListener(new MessagesItemClickListener()));

        drawer.addItem( new DividerDrawerItem());
    }

    private void addActiveBatchMenuItems(){

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_batch_itinerary).withIcon(FontAwesome.Icon.faw_list_alt)
                .withIdentifier(ID_BATCH_ITINERARY).withSelectable(false).withOnDrawerItemClickListener(new BatchItineraryItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_order_itinerary).withIcon(FontAwesome.Icon.faw_list_alt)
                .withIdentifier(ID_ORDER_ITINERARY).withSelectable(false).withOnDrawerItemClickListener(new OrderItineraryItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_current_step).withIcon(FontAwesome.Icon.faw_shopping_bag)
                .withIdentifier(ID_ORDER_STEP).withSelectable(false).withOnDrawerItemClickListener(new CurrentStepItemClickListener()));

        drawer.addItem( new DividerDrawerItem());
    }

    private void addOffersMenuItems(){

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_offers).withIcon(FontAwesome.Icon.faw_car)
                .withIdentifier(ID_PUBLIC_OFFERS).withSelectable(false).withBadgeStyle(new BadgeStyle()
                        .withTextColor(activity.getResources().getColor(R.color.colorTextPrimaryLight))
                        .withColorRes(R.color.colorNavMenuPublicOfferHighlight))
                .withOnDrawerItemClickListener(new PublicOffersItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_personal_offers).withIcon(FontAwesome.Icon.faw_bicycle)
                .withIdentifier(ID_PERSONAL_OFFERS).withSelectable(false).withBadgeStyle(new BadgeStyle()
                        .withTextColor(activity.getResources().getColor(R.color.colorTextPrimaryLight))
                        .withColorRes(R.color.colorNavMenuPersonalOfferHighlight))
                .withOnDrawerItemClickListener(new PersonalOffersItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_demo).withIcon(FontAwesome.Icon.faw_ambulance)
                .withIdentifier(ID_DEMO_OFFERS).withSelectable(false).withBadgeStyle(new BadgeStyle()
                        .withTextColor(activity.getResources().getColor(R.color.colorTextPrimaryLight))
                        .withColorRes(R.color.colorNavMenuDemoOfferHighlight))
                .withOnDrawerItemClickListener(new DemoOffersClickListener()));

        drawer.addItem( new DividerDrawerItem());
    }

    private void addScheduledWorkMenuItems(){
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_scheduled_batches).withIcon(FontAwesome.Icon.faw_calendar)
                .withIdentifier(ID_SCHEDULED_WORK).withSelectable(false).withBadgeStyle(new BadgeStyle()
                        .withTextColor(activity.getResources().getColor(R.color.colorTextPrimaryLight))
                        .withColorRes(R.color.colorNavMenuScheduledBatchHighlight))
                .withOnDrawerItemClickListener(new ScheduledBatchesClickListener()));

        drawer.addItem(new DividerDrawerItem());
    }

    private void addEarningsAndAccountMenuItems(){
        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_earnings).withIcon(FontAwesome.Icon.faw_usd)
                .withIdentifier(ID_EARNINGS).withSelectable(false).withOnDrawerItemClickListener(new EarningsItemClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_account).withIcon(FontAwesome.Icon.faw_user_circle)
                .withIdentifier(ID_ACCOUNT).withSelectable(false).withOnDrawerItemClickListener(new AccountItemClickListener()));

        drawer.addItem(new DividerDrawerItem());
    }

    private void addHelpMenuItems() {

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_help).withIcon(FontAwesome.Icon.faw_question_circle)
                .withIdentifier(ID_HELP).withSelectable(false).withOnDrawerItemClickListener(new HelpItemClickListener()));

    }

    private void addDeveloperToolsMenuItems(){
        drawer.addItem(new SectionDrawerItem().withName(R.string.nav_menu_dev_section_title));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_dev_offers).withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_DEV_TEST_OFFERS).withSelectable(false).withOnDrawerItemClickListener(new DevTestOffersClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_dev_earnings).withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_DEV_TEST_EARNINGS).withSelectable(false).withOnDrawerItemClickListener(new DevTestEarningsClickListener()));

    }

    private void addQaToolsMenuItems(){
        drawer.addItem(new SectionDrawerItem().withName(R.string.nav_menu_qa_section_title));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_dev_offers).withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_DEV_TEST_OFFERS).withSelectable(false).withOnDrawerItemClickListener(new DevTestOffersClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_dev_earnings).withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_DEV_TEST_EARNINGS).withSelectable(false).withOnDrawerItemClickListener(new DevTestEarningsClickListener()));

    }


    public Drawer getDrawer(){
        return drawer;
    }



    private void updatePersonalOffersCount(){
        Integer offerCount = AndroidDevice.getInstance().getOfferLists().getPersonalOffers().size();
        Timber.tag(TAG).d("personal offers count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_PERSONAL_OFFERS, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_PERSONAL_OFFERS, null);
        }
    }

    private void updatePublicOffersCount(){
        Integer offerCount = AndroidDevice.getInstance().getOfferLists().getPublicOffers().size();
        Timber.tag(TAG).d("public offers count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_PUBLIC_OFFERS, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_PUBLIC_OFFERS, null);
        }
    }

    private void updateDemoOffersCount(){
        Integer offerCount = AndroidDevice.getInstance().getOfferLists().getDemoOffers().size();
        Timber.tag(TAG).d("demo offers count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_DEMO_OFFERS, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_DEMO_OFFERS, null);
        }
    }

    private void updateScheduledBatchesCount(){
        Integer offerCount = AndroidDevice.getInstance().getOfferLists().getScheduledBatches().size();
        Timber.tag(TAG).d("scheduled batches count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_SCHEDULED_WORK, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_SCHEDULED_WORK, null);
        }
    }


    ///
    ///  private classes for item click listeners
    ///

    private class BatchItineraryItemClickListener implements Drawer.OnDrawerItemClickListener {
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoBatchItinerary(activity);
            Timber.tag(TAG).d("clicked on BATCH ITINERARY menu item");
            return false;
        }
    }

    private class OrderItineraryItemClickListener implements Drawer.OnDrawerItemClickListener {
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoOrderItinerary(activity);
            Timber.tag(TAG).d("clicked on ORDER ITINERARY menu item");
            return false;
        }
    }

    private class CurrentStepItemClickListener implements Drawer.OnDrawerItemClickListener {
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

            Timber.tag(TAG).d("clicked on CURRENT STEP menu item");
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActiveBatchStep(activity);
            return false;
        }
    }

    private class AccountItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityAccount(activity);
            Timber.tag(TAG).d("clicked on ACCOUNT menu item");
            return false;
        }
    }

    private class EarningsItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityEarnings(activity);
            Timber.tag(TAG).d("clicked on EARNINGS menu");
            return false;
        }
    }

    private class HelpItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityHelp(activity);
            Timber.tag(TAG).d("clicked on HELP");
            return false;
        }
    }

    private class HomeItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityHome(activity);
            Timber.tag(TAG).d("clicked on HOME");
            return false;
        }
    }

    private class MessagesItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityMessages(activity);
            Timber.tag(TAG).d("clicked on MESSAGES");
            return false;
        }
    }

    private class PublicOffersItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityPublicOffers(activity);
            Timber.tag(TAG).d("clicked on PUBLIC OFFERS");
            return false;
        }
    }


    private class PersonalOffersItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityPersonalOffers(activity);
            Timber.tag(TAG).d("clicked on PERSONAL OFFERS");
            return false;
        }
    }

    private class DemoOffersClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityDemoOffers(activity);
            Timber.tag(TAG).d("clicked on DEMO OFFERS");
            return false;
        }

    }

    private class ScheduledBatchesClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            ActivityNavigator.getInstance().gotoActivityScheduledBatches(activity);
            Timber.tag(TAG).d("clicked on SCHEDULED BATCHES");
            return false;
        }
    }

    private class DevTestOffersClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            //do something with the clicked item
            ActivityNavigator.getInstance().gotoActivityTestOffersMake(activity);
            Timber.tag(TAG).d("clicked on dev test offers");
            return false;
        }
    }

    private class DevTestEarningsClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            //do something with the clicked item
            ActivityNavigator.getInstance().gotoActivityTestEarnings(activity);
            Timber.tag(TAG).d("clicked on dev test earnings");
            return false;
        }
    }


    /////
    //// UI update events - offer & batch counts
    ////

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received public offers count updated event");
        updatePublicOffersCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received personal offers count updated event");
        updatePersonalOffersCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received demo offers count updated event");
        updateDemoOffersCount();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchCountUpdateEvent event) {
        Timber.tag(TAG).d("received scheduled batches count updated event");
        updateScheduledBatchesCount();
    }
}
