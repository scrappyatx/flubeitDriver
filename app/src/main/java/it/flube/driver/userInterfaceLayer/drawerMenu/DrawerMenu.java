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
import it.flube.driver.userInterfaceLayer.userInterfaceEvents.activeBatch.ActiveBatchUpdatedEvent;
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

    private static final Integer ID_SYSTEM_STATUS = 13;
    private static final Integer ID_OFFER_MESSAGING = 14;
    private static final Integer ID_BATCH_MESSAGING = 15;
    private static final Integer ID_ACTIVE_BATCH_MESSAGING = 16;

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
        createToolbar(titleId);
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

    private void createToolbar(int titleId) {
        toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        toolbar.setTitle(titleId);
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

        if (device.getActiveBatch().hasActiveBatch()){
            addActiveBatchMenuItems();
        }

        addOffersMenuItems();
        addScheduledWorkMenuItems();
        addEarningsAndAccountMenuItems();
        addHelpMenuItems();

        if (device.getUser().getDriver().getUserRoles().getDev()) {
            addDeveloperToolsMenuItems();
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

    private void addActiveBatchMenuItems(){

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_batch_itinerary).withIcon(FontAwesome.Icon.faw_list_alt)
                .withIdentifier(ID_BATCH_ITINERARY).withSelectable(false).withOnDrawerItemClickListener(new BatchItineraryItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_order_itinerary).withIcon(FontAwesome.Icon.faw_list_alt)
                .withIdentifier(ID_ORDER_ITINERARY).withSelectable(false).withOnDrawerItemClickListener(new OrderItineraryItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_current_step).withIcon(FontAwesome.Icon.faw_shopping_bag)
                .withIdentifier(ID_ORDER_STEP).withSelectable(false).withOnDrawerItemClickListener(new CurrentStepItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_messages).withIcon(FontAwesome.Icon.faw_comments)
                .withIdentifier(ID_MESSAGES).withSelectable(false).withOnDrawerItemClickListener(new MessagesItemClickListener()));

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
        drawer.addItem(new SectionDrawerItem().withName("Developer Tools"));

        drawer.addItem(new SecondaryDrawerItem().withName("System Status").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_SYSTEM_STATUS).withSelectable(false));

        drawer.addItem(new SecondaryDrawerItem().withName("OFFER messaging").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_OFFER_MESSAGING).withSelectable(false));

        drawer.addItem(new SecondaryDrawerItem().withName("BATCH messaging").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_BATCH_MESSAGING).withSelectable(false));

        drawer.addItem(new SecondaryDrawerItem().withName("ACTIVE BATCH messaging").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(ID_ACTIVE_BATCH_MESSAGING).withSelectable(false));

    }



    public Drawer getDrawer(){ return drawer; }

    private AccountHeader buildAccountHeader() {
        //String photoUrl = "http://lorempixel.com/60/60/people/";

        IProfile profile = new ProfileDrawerItem().withName(device.getUser().getDriver().getNameSettings().getDisplayName())
                .withEmail(device.getUser().getDriver().getEmail()).withIcon(device.getUser().getDriver().getPhotoUrl());

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

    //// active batch step changed event
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ActiveBatchUpdatedEvent event) {
        Timber.tag(TAG).d("active batch current step changed!");
        navigator.gotoActiveBatchStep(activity);
    }

    //// UI update events - offer & batch counts
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received public offers count updated event");
        updatePersonalOffersCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received personal offers count updated event");
        updatePersonalOffersCount();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOfferCountUpdatedEvent event) {
        Timber.tag(TAG).d("received demmo offers count updated event");
        updateDemoOffersCount();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchCountUpdateEvent event) {
        Timber.tag(TAG).d("received scheduled batches count updated event");
        updateScheduledBatchesCount();
    }
}
