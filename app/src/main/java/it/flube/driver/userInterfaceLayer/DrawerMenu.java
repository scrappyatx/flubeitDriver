/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

import android.content.Context;
import android.graphics.Color;
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
import it.flube.driver.deviceLayer.realtimeMessaging.receiveMessageHandlers.CurrentOffersMessageHandler;
import it.flube.driver.useCaseLayer.interfaces.MobileDeviceInterface;
import timber.log.Timber;

/**
 * Created on 5/23/2017
 * Project : Driver
 */

public class DrawerMenu {
    private static final String TAG = "DrawerMenu";
    private static boolean mSearchingForOffers;

    private Drawer drawer;
    private AppCompatActivity activity;
    private Toolbar toolbar;
    private SwitchCompat toolbarSwitch;
    private ActivityNavigator navigator;
    private MobileDeviceInterface device;


    public DrawerMenu(@NonNull AppCompatActivity activity, @NonNull ActivityNavigator navigator, @NonNull int titleId) {
        this.activity = activity;
        this.navigator = navigator;
        device = AndroidDevice.getInstance();
        createToolbar(titleId);
        createDrawer();
        EventBus.getDefault().register(this);
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

        addStandardMenuItems();
        if (device.getUser().isDeveloperToolsMenuEnabled()) {
            addDeveloperToolsMenuItems();
        }

    }

    private void addStandardMenuItems() {
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_home).withIcon(FontAwesome.Icon.faw_home)
                .withIdentifier(1).withSelectable(false).withOnDrawerItemClickListener(new HomeItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_offers).withIcon(FontAwesome.Icon.faw_car)
                .withIdentifier(2).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                .withOnDrawerItemClickListener(new OffersItemClickListener()));

        drawer.addItem( new DividerDrawerItem());

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_scheduled_batches).withIcon(FontAwesome.Icon.faw_calendar)
                .withIdentifier(3).withSelectable(false).withOnDrawerItemClickListener(new ScheduledBatchesClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_messages).withIcon(FontAwesome.Icon.faw_comments)
                .withIdentifier(4).withSelectable(false).withOnDrawerItemClickListener(new MessagesItemClickListener()));

        drawer.addItem(new DividerDrawerItem());

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_earnings).withIcon(FontAwesome.Icon.faw_usd)
                .withIdentifier(5).withSelectable(false).withOnDrawerItemClickListener(new EarningsItemClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_account).withIcon(FontAwesome.Icon.faw_user_circle)
                .withIdentifier(6).withSelectable(false).withOnDrawerItemClickListener(new AccountItemClickListener()));

        drawer.addItem(new DividerDrawerItem());

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_help).withIcon(FontAwesome.Icon.faw_question_circle)
                .withIdentifier(7).withSelectable(false).withOnDrawerItemClickListener(new HelpItemClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_demo).withIcon(FontAwesome.Icon.faw_ambulance)
                .withIdentifier(8).withSelectable(false).withOnDrawerItemClickListener(new DemoItemClickListener()));

    }

    private void addDeveloperToolsMenuItems(){
        drawer.addItem(new SectionDrawerItem().withName("Developer Tools"));
        drawer.addItem(new SecondaryDrawerItem().withName("System Status").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(9).withSelectable(false));

        drawer.addItem(new SecondaryDrawerItem().withName("OFFER messaging").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(10).withSelectable(false));

        drawer.addItem(new SecondaryDrawerItem().withName("BATCH messaging").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(11).withSelectable(false));

        drawer.addItem(new SecondaryDrawerItem().withName("ACTIVE BATCH messaging").withIcon(FontAwesome.Icon.faw_wrench)
                .withIdentifier(12).withSelectable(false));

    }


    public Drawer getDrawer(){ return drawer; }

    private AccountHeader buildAccountHeader() {
        //create profile

        String photoUrl = "http://lorempixel.com/60/60/people/";
        //TODO should be AppUser.getInstance().getDriver().getDisplayName();


        IProfile profile = new ProfileDrawerItem().withName(device.getUser().getDriver().getDisplayName())
                .withEmail(device.getUser().getDriver().getEmail()).withIcon(photoUrl);

                //.withIcon(R.drawable.demo_profile_pic)
        return new AccountHeaderBuilder()
                .withActivity(activity)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.account_header_background)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .build();
    }



    public void close() {
        drawer = null;
        activity = null;
        toolbar = null;
        toolbarSwitch = null;
        navigator = null;
        device = null;
        EventBus.getDefault().unregister(this);
    }


    private class toolbarSwitchListener implements CompoundButton.OnCheckedChangeListener {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //turn on searching for offers
                //Alerter.create(mActivity)
                //        .setTitle("Searching For Offers")
                //        .setText("TURNING ON")
                //        .show();
                mSearchingForOffers = true;
                //String serverUrl, String clientId, String lookingForOffersChannelName, String batchActivityChannelNam

                //String tokenUrl = thisDevice.getAppRemoteConfig().getRealtimeMessagingAuthTokenUrl();
                //String clientId = thisDevice.getUser().getDriver().getClientId();
                //String lookingForOffersChannelName = thisDevice.getAppRemoteConfig().getRealtimeMessagingLookingForOffersChannelName();
                //String batchActivityChannelName = thisDevice.getAppRemoteConfig().getRealtimeMessagingBatchActivityChannelName();
                //String lookingForOffersDemoChannelName = thisDevice.getAppRemoteConfig().getRealtimeMessagingLookingForOffersDemoChannelName();

                //Timber.tag(TAG).d(" ****** Ably Realtime Messaging Connection Values ******");
                //Timber.tag(TAG).d("tokenUrl                        --> " + tokenUrl);
                //Timber.tag(TAG).d("clientId                        --> " + clientId);
                //Timber.tag(TAG).d("looking for offers channel      --> " + lookingForOffersChannelName );
                //Timber.tag(TAG).d("batch activity channel          --> " + batchActivityChannelName);
                //Timber.tag(TAG).d("looking for offers demo channel --> " + lookingForOffersDemoChannelName);
                //Timber.tag(TAG).d(" ****** Ably Realtime Messaging Connection Values ******");

                //mActivity.startService(RealTimeMessagingAndLocationUpdatesForegroundService.startIntent(mActivity, tokenUrl, clientId,lookingForOffersChannelName,batchActivityChannelName));

            } else {
                // turn off searching for offers
                //Alerter.create(mActivity)
                //        .setTitle("Searching For Offers")
                //        .setText("TURNING OFF")
                //        .show();
                mSearchingForOffers = false;
                //drawer.updateBadge(2, new StringHolder(null));
                //mActivity.startService(RealTimeMessagingAndLocationUpdatesForegroundService.shutdownIntent(mActivity));
            }
        }

    }

    ///
    ///  private classes for item click listeners
    ///
    private class AccountItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityAccount(activity);
            Timber.tag(TAG).d("clicked on ACCOUNT menu item");
            return false;
        }
    }

    private class DemoItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityDemo(activity);
            Timber.tag(TAG).d("clicked on DEMO menu");
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

    private class OffersItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityOffers(activity);
            Timber.tag(TAG).d("clicked on OFFERS");
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

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(CurrentOffersMessageHandler.CurrentOffersEvent event) {
        try {
            drawer.updateBadge(2, new StringHolder(Integer.toString(event.getOfferCount()) + ""));
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " offers");
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

}
