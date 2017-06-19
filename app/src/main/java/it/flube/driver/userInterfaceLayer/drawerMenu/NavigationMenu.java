/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.drawerMenu;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.CompoundButton;

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
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.tapadoo.alerter.Alerter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import it.flube.driver.R;
import it.flube.driver.dataLayer.messaging.eventBus.driverMessageEvents.ReceivedCurrentOffersMessage;
import it.flube.driver.dataLayer.services.RealTimeMessagingAndLocationUpdatesForegroundService;
import it.flube.driver.dataLayer.services.ServerCommService;
import it.flube.driver.modelLayer.entities.DriverSingleton;
import it.flube.driver.userInterfaceLayer.activities.MainActivityDELETE;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.AccountItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.DemoItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.EarningsItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.HelpItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.HomeItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.MessagesItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.OffersItemClickListener;
import it.flube.driver.userInterfaceLayer.drawerMenu.itemClickListeners.ScheduledBatchesClickListener;

/**
 * Created on 5/23/2017
 * Project : Driver
 */

public class NavigationMenu {
    private static final String TAG = "NavigationMenu";
    private Drawer mDrawer;
    private AppCompatActivity mActivity;
    private Toolbar mToolbar;
    private SwitchCompat mSwitch;
    private static boolean mSearchingForOffers;


    public NavigationMenu(AppCompatActivity activity, Toolbar toolbar) {
        //register on eventbus
        EventBus.getDefault().register(this);


        mActivity = activity;
        mToolbar = toolbar;
        activity.setSupportActionBar(mToolbar);

        //set up looking for work switch
        mSwitch = (SwitchCompat) activity.findViewById(R.id.switch_looking_for_work);

        //order is important with next 2 statements.  Want to set the switch to the static variable first, THEN set the listener
        // this prevents calling the "onCheckedChanged" listener everytime user switches activities
        mSwitch.setChecked(mSearchingForOffers);
        mSwitch.setOnCheckedChangeListener(new mySwitchListener());

        //setup navigation drawer
        mDrawer = new DrawerBuilder()
                .withActivity(mActivity)
                .withToolbar(mToolbar)
                .withDisplayBelowStatusBar(true)
                .withActionBarDrawerToggleAnimated(true)
                .withActionBarDrawerToggle(true)
                .withTranslucentStatusBar(false)
                .withFullscreen(false)
                .withAccountHeader(buildAccountHeader())
                .withSelectedItem(-1)
                .addDrawerItems(
                    new PrimaryDrawerItem().withName(R.string.nav_menu_home).withIdentifier(1).withSelectable(false).withOnDrawerItemClickListener(new HomeItemClickListener()),
                    new PrimaryDrawerItem().withName(R.string.nav_menu_offers).withIdentifier(2).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                            .withOnDrawerItemClickListener(new OffersItemClickListener()),
                    new DividerDrawerItem(),


                    new SecondaryDrawerItem().withName(R.string.nav_menu_scheduled_batches).withIdentifier(3).withSelectable(false).withOnDrawerItemClickListener(new ScheduledBatchesClickListener()),
                    new SecondaryDrawerItem().withName(R.string.nav_menu_messages).withIdentifier(4).withSelectable(false).withOnDrawerItemClickListener(new MessagesItemClickListener()),
                    new DividerDrawerItem(),

                    new SecondaryDrawerItem().withName(R.string.nav_menu_earnings).withIdentifier(5).withSelectable(false).withOnDrawerItemClickListener(new EarningsItemClickListener()),
                    new SecondaryDrawerItem().withName(R.string.nav_menu_account).withIdentifier(6).withSelectable(false).withOnDrawerItemClickListener(new AccountItemClickListener()),
                    new DividerDrawerItem(),

                    new SecondaryDrawerItem().withName(R.string.nav_menu_help).withIdentifier(7).withSelectable(false).withOnDrawerItemClickListener(new HelpItemClickListener()),
                    new SecondaryDrawerItem().withName(R.string.nav_menu_demo).withIdentifier(8).withSelectable(false).withOnDrawerItemClickListener(new DemoItemClickListener())
                )
                .build();
    }

    public Drawer getDrawer(){ return mDrawer; }

    private AccountHeader buildAccountHeader() {
        //create profile
        IProfile profile = new ProfileDrawerItem().withName(DriverSingleton.getInstance().getFirstName() + " " + DriverSingleton.getInstance().getLastName())
                .withEmail(DriverSingleton.getInstance().getEmail()).withIcon(R.drawable.demo_profile_pic);
        return new AccountHeaderBuilder()
                .withActivity(mActivity)
                .withCompactStyle(true)
                .withHeaderBackground(R.drawable.account_header_background)
                .addProfiles(profile)
                .withSelectionListEnabledForSingleProfile(false)
                .build();
    }

    private class mySwitchListener implements CompoundButton.OnCheckedChangeListener {

        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                //turn on searching for offers
                //Alerter.create(mActivity)
                //        .setTitle("Searching For Offers")
                //        .setText("TURNING ON")
                //        .show();
                mSearchingForOffers = true;
                //String serverUrl, String clientId, String lookingForOffersChannelName, String batchActivityChannelName
                String serverUrl = "https://api.cloudconfidant.com/concierge-oil-service/ably/token";
                String clientId = DriverSingleton.getInstance().getClientId();
                String lookingForOffersChannelName = "LookingForOffers";
                String batchActivityChannelName = "BatchActivity";
                mActivity.startService(RealTimeMessagingAndLocationUpdatesForegroundService.startIntent(mActivity, serverUrl, clientId,lookingForOffersChannelName,batchActivityChannelName));

            } else {
                // turn off searching for offers
                //Alerter.create(mActivity)
                //        .setTitle("Searching For Offers")
                //        .setText("TURNING OFF")
                //        .show();
                mSearchingForOffers = false;
                mDrawer.updateBadge(2, new StringHolder(null));
                mActivity.startService(RealTimeMessagingAndLocationUpdatesForegroundService.shutdownIntent(mActivity));
            }
        }

    }

    /////
    /////   Message Received Events
    /////
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(ReceivedCurrentOffersMessage msg) {
        mDrawer.updateBadge(2, new StringHolder(Integer.toString(msg.getCurrentOfferList().size()) + ""));
        Log.d(TAG,"received " + Integer.toString(msg.getCurrentOfferList().size()) + " offers");
    }

}
