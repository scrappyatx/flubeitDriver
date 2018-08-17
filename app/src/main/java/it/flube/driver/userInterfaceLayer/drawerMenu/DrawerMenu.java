/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu;

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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedStepStartedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.DemoOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PersonalOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.offerListUpdates.PublicOfferCountUpdatedEvent;
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.scheduledBatchListUpdates.ScheduledBatchCountUpdateEvent;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.userInterfaceLayer.activityNavigator.ActivityNavigator;
import it.flube.driver.userInterfaceLayer.userInterfaceEventHandlers.UserInterfaceEventHandler;
import timber.log.Timber;

/**
 * Created on 5/23/2017
 * Project : Driver
 */

public class DrawerMenu {
    private static final String TAG = "DrawerMenu";
    private static boolean mSearchingForOffers;

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
    private Toolbar toolbar;

    private SwitchCompat toolbarSwitch;
    private ActivityNavigator navigator;
    private UserInterfaceEventHandler alertEventHandler;

    private MobileDeviceInterface device;


    public DrawerMenu(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator, @NonNull int titleId) {
        this.activity = activity;
        this.navigator = navigator;
        device = AndroidDevice.getInstance();
        createToolbar(activity, titleId);
        createDrawer();
        EventBus.getDefault().register(this);

        alertEventHandler = new UserInterfaceEventHandler(activity, navigator);
    }

    public void close() {
        drawer = null;
        activity = null;
        toolbar = null;
        toolbarSwitch = null;
        navigator = null;
        device = null;
        alertEventHandler.close();
        EventBus.getDefault().unregister(this);
    }

    private void createToolbar(AppCompatActivity activity, int titleId) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);


        String toolbarTitle = activity.getResources().getString(titleId);
        Timber.tag(TAG).d("....creating toolbar, title = " + toolbarTitle);
        toolbar.setTitle(toolbarTitle);


        activity.setSupportActionBar(toolbar);

        toolbarSwitch = (SwitchCompat) activity.findViewById(R.id.switch_looking_for_work);
        //order is important with next 2 statements.  Want to set the switch to the static variable first, THEN set the listener
        // this prevents calling the "onCheckedChanged" listener everytime user switches activities
        toolbarSwitch.setChecked(mSearchingForOffers);
        toolbarSwitch.setOnCheckedChangeListener(new toolbarSwitchListener());
        toolbarSwitch.setVisibility(View.GONE);
    }

    private void createDrawer() {
        //setup navigation drawer
        drawer = new DrawerBuilder()
                .withActivity(activity)
                .withToolbar(toolbar)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .withTranslucentStatusBar(false)
                .withFullscreen(false)
                .withAccountHeader(buildAccountHeader())
                .withSelectedItem(-1)
                .build();

        addHomeMenuItems();

        addMessageMenuItems();

        if (device.getActiveBatch().hasActiveBatch()){
            addActiveBatchMenuItems();
        }

        addOffersMenuItems();
        addScheduledWorkMenuItems();
        addEarningsAndAccountMenuItems();
        addHelpMenuItems();

        if (device.getUser().getDriver().getUserRoles().getDev()) {
            addDeveloperToolsMenuItems();
        } else {
            if (device.getUser().getDriver().getUserRoles().getQa()){
                addQaToolsMenuItems();
            }
        }

        updatePersonalOffersCount();
        updatePublicOffersCount();
        updateDemoOffersCount();
        updateScheduledBatchesCount();

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



    public Drawer getDrawer(){ return drawer; }

    private AccountHeader buildAccountHeader() {
        //String photoUrl = "http://lorempixel.com/60/60/people/";
        Driver driver = device.getUser().getDriver();
        Timber.tag(TAG).d("driver display name -> " + driver.getNameSettings().getDisplayName());
        Timber.tag(TAG).d("driver email        -> " + driver.getEmail());

        IProfile profile = new ProfileDrawerItem().withName(driver.getNameSettings().getDisplayName())
                .withEmail(driver.getEmail()).withIcon(device.getUser().getDriver().getPhotoUrl());

                //.withIcon(R.drawable.demo_profile_pic)
        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.account_header_background)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .build();
    }

    private void updatePersonalOffersCount(){
        Integer offerCount = device.getOfferLists().getPersonalOffers().size();
        Timber.tag(TAG).d("personal offers count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_PERSONAL_OFFERS, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_PERSONAL_OFFERS, null);
        }
    }

    private void updatePublicOffersCount(){
        Integer offerCount = device.getOfferLists().getPublicOffers().size();
        Timber.tag(TAG).d("public offers count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_PUBLIC_OFFERS, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_PUBLIC_OFFERS, null);
        }
    }

    private void updateDemoOffersCount(){
        Integer offerCount = device.getOfferLists().getDemoOffers().size();
        Timber.tag(TAG).d("demo offers count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_DEMO_OFFERS, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_DEMO_OFFERS, null);
        }
    }

    private void updateScheduledBatchesCount(){
        Integer offerCount = device.getOfferLists().getScheduledBatches().size();
        Timber.tag(TAG).d("scheduled batches count = " + offerCount);
        if (offerCount > 0) {
            drawer.updateBadge(ID_SCHEDULED_WORK, new StringHolder(Integer.toString(offerCount) + ""));
        } else {
            drawer.updateBadge(ID_SCHEDULED_WORK, null);
        }
    }




    private class toolbarSwitchListener implements CompoundButton.OnCheckedChangeListener {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                mSearchingForOffers = true;
            } else {
                mSearchingForOffers = false;
            }
        }

    }

    ///
    ///  private classes for item click listeners
    ///

    private class BatchItineraryItemClickListener implements Drawer.OnDrawerItemClickListener {
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoBatchItinerary(activity);
            Timber.tag(TAG).d("clicked on BATCH ITINERARY menu item");
            return false;
        }
    }

    private class OrderItineraryItemClickListener implements Drawer.OnDrawerItemClickListener {
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoOrderItinerary(activity);
            Timber.tag(TAG).d("clicked on ORDER ITINERARY menu item");
            return false;
        }
    }

    private class CurrentStepItemClickListener implements Drawer.OnDrawerItemClickListener {
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {

            Timber.tag(TAG).d("clicked on CURRENT STEP menu item");
            // do something with the clicked item :D
            navigator.gotoActiveBatchStep(activity);
            return false;
        }
    }

    private class AccountItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityAccount(activity);
            Timber.tag(TAG).d("clicked on ACCOUNT menu item");
            return false;
        }
    }

    private class EarningsItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityEarnings(activity);
            Timber.tag(TAG).d("clicked on EARNINGS menu");
            return false;
        }
    }

    private class HelpItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityHelp(activity);
            Timber.tag(TAG).d("clicked on HELP");
            return false;
        }
    }

    private class HomeItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityHome(activity);
            Timber.tag(TAG).d("clicked on HOME");
            return false;
        }
    }

    private class MessagesItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityMessages(activity);
            Timber.tag(TAG).d("clicked on MESSAGES");
            return false;
        }
    }

    private class PublicOffersItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityPublicOffers(activity);
            Timber.tag(TAG).d("clicked on PUBLIC OFFERS");
            return false;
        }
    }


    private class PersonalOffersItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityPersonalOffers(activity);
            Timber.tag(TAG).d("clicked on PERSONAL OFFERS");
            return false;
        }
    }

    private class DemoOffersClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityDemoOffers(activity);
            Timber.tag(TAG).d("clicked on DEMO OFFERS");
            return false;
        }

    }

    private class ScheduledBatchesClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityScheduledBatches(activity);
            Timber.tag(TAG).d("clicked on SCHEDULED BATCHES");
            return false;
        }
    }

    private class DevTestOffersClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            //do something with the clicked item
            navigator.gotoActivityTestOffersMake(activity);
            Timber.tag(TAG).d("clicked on dev test offers");
            return false;
        }
    }

    private class DevTestEarningsClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            //do something with the clicked item
            navigator.gotoActivityTestEarnings(activity);
            Timber.tag(TAG).d("clicked on dev test earnings");
            return false;
        }
    }

    /////
    //// ACTIVE BATCH UPDATED events
    ////

    /// step started
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedStepStartedEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedStepStartedEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   actionType       -> " + event.getActionType().toString());
        Timber.tag(TAG).d("   batchStarted     -> " + event.isBatchStarted());
        Timber.tag(TAG).d("   orderStarted     -> " + event.isOrderStarted());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());
        Timber.tag(TAG).d("   serviceOrderGuid -> " + event.getServiceOrderGuid());
        Timber.tag(TAG).d("   stepGuid         -> " + event.getStepGuid());
        Timber.tag(TAG).d("   taskType         -> " + event.getTaskType().toString());

        navigator.gotoActiveBatchStep(activity, event.getActorType(), event.getActionType(), event.isBatchStarted(), event.isOrderStarted(), event.getBatchGuid(), event.getServiceOrderGuid(), event.getStepGuid(), event.getTaskType());
        EventBus.getDefault().removeStickyEvent(event);
    }

    /// batch finished
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedBatchFinishedEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedBatchFinishedEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());

        navigator.gotoActivityHomeAndShowBatchFinishedMessage(activity, event.getActorType(), event.getBatchGuid());
        EventBus.getDefault().removeStickyEvent(event);
    }

    /// batch waiting to finish
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedBatchWaitingToFinishEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedBatchWaitingToFinishEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());

        navigator.gotoWaitingToFinishBatch(activity, event.getActorType(), event.getBatchGuid());
        EventBus.getDefault().removeStickyEvent(event);
    }

    /// batch removed
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedBatchRemovedEvent event){
        Timber.tag(TAG).d("received ActiveBatchUpdatedBatchWaitingToFinishEvent");
        Timber.tag(TAG).d("   actorType        -> " + event.getActorType().toString());
        Timber.tag(TAG).d("   batchGuid        -> " + event.getBatchGuid());

        navigator.gotoActivityHomeAndShowBatchRemovedMessage(activity, event.getActorType(), event.getBatchGuid());
        EventBus.getDefault().removeStickyEvent(event);
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
