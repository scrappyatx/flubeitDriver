/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer;

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
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.ClaimOfferResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.ClaimDemoOfferResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.demoOffers.DemoOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.personalOffers.PersonalOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.offers.publicOffers.PublicOffersAvailableResponseHandler;
import it.flube.driver.dataLayer.useCaseResponseHandlers.scheduledBatches.ScheduledBatchesAvailableResponseHandler;
import it.flube.driver.modelLayer.interfaces.MobileDeviceInterface;
import it.flube.driver.userInterfaceLayer.activities.offers.claimOffer.OfferClaimAlerts;
import timber.log.Timber;

/**
 * Created on 5/23/2017
 * Project : Driver
 */

public class DrawerMenu {
    private static final String TAG = "DrawerMenu";
    private static boolean mSearchingForOffers;

    private static final Integer ID_HOME = 1;
    private static final Integer ID_PUBLIC_OFFERS = 2;
    private static final Integer ID_PERSONAL_OFFERS = 3;
    private static final Integer ID_SCHEDULED_WORK = 4;
    private static final Integer ID_MESSAGES = 5;
    private static final Integer ID_EARNINGS = 6;
    private static final Integer ID_ACCOUNT = 7;
    private static final Integer ID_HELP = 8;
    private static final Integer ID_DEMO_OFFERS = 9;
    private static final Integer ID_SYSTEM_STATUS = 10;
    private static final Integer ID_OFFER_MESSAGING = 11;
    private static final Integer ID_BATCH_MESSAGING = 12;
    private static final Integer ID_ACTIVE_BATCH_MESSAGING = 13;

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
        toolbarSwitch.setVisibility(View.INVISIBLE);
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
        if (device.getUser().getDriver().isDev()) {
            addDeveloperToolsMenuItems();
        }

    }

    private void addStandardMenuItems() {
        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_home).withIcon(FontAwesome.Icon.faw_home)
                .withIdentifier(ID_HOME).withSelectable(false).withOnDrawerItemClickListener(new HomeItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_offers).withIcon(FontAwesome.Icon.faw_car)
                .withIdentifier(ID_PUBLIC_OFFERS).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                .withOnDrawerItemClickListener(new PublicOffersItemClickListener()));

        drawer.addItem(new PrimaryDrawerItem().withName(R.string.nav_menu_personal_offers).withIcon(FontAwesome.Icon.faw_bicycle)
                .withIdentifier(ID_PERSONAL_OFFERS).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                .withOnDrawerItemClickListener(new PersonalOffersItemClickListener()));

        drawer.addItem( new DividerDrawerItem());

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_scheduled_batches).withIcon(FontAwesome.Icon.faw_calendar)
                .withIdentifier(ID_SCHEDULED_WORK).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                .withOnDrawerItemClickListener(new ScheduledBatchesClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_messages).withIcon(FontAwesome.Icon.faw_comments)
                .withIdentifier(ID_MESSAGES).withSelectable(false).withOnDrawerItemClickListener(new MessagesItemClickListener()));

        drawer.addItem(new DividerDrawerItem());

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_earnings).withIcon(FontAwesome.Icon.faw_usd)
                .withIdentifier(ID_EARNINGS).withSelectable(false).withOnDrawerItemClickListener(new EarningsItemClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_account).withIcon(FontAwesome.Icon.faw_user_circle)
                .withIdentifier(ID_ACCOUNT).withSelectable(false).withOnDrawerItemClickListener(new AccountItemClickListener()));

        drawer.addItem(new DividerDrawerItem());

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_help).withIcon(FontAwesome.Icon.faw_question_circle)
                .withIdentifier(ID_HELP).withSelectable(false).withOnDrawerItemClickListener(new HelpItemClickListener()));

        drawer.addItem(new SecondaryDrawerItem().withName(R.string.nav_menu_demo).withIcon(FontAwesome.Icon.faw_ambulance)
                .withIdentifier(ID_DEMO_OFFERS).withSelectable(false).withBadgeStyle(new BadgeStyle().withTextColor(Color.WHITE).withColorRes(R.color.md_red_700))
                .withOnDrawerItemClickListener(new DemoOffersClickListener()));

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

        IProfile profile = new ProfileDrawerItem().withName(device.getUser().getDriver().getDisplayName())
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
                mSearchingForOffers = true;
            } else {
                mSearchingForOffers = false;
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
            navigator.gotoActivityOffers(activity);
            Timber.tag(TAG).d("clicked on OFFERS");
            return false;
        }
    }


    private class PersonalOffersItemClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            //navigator.gotoActivityOffers(activity);
            Timber.tag(TAG).d("clicked on PERSONAL OFFERS");
            return false;
        }
    }

    private class DemoOffersClickListener implements Drawer.OnDrawerItemClickListener {

        public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
            // do something with the clicked item :D
            navigator.gotoActivityDemo(activity);
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

    //// UI update events - offer & batch counts


    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(PublicOffersAvailableResponseHandler.AvailablePublicOffersEvent event) {
        try {
            if (event.getOfferCount() > 0) {
                drawer.updateBadge(ID_PUBLIC_OFFERS, new StringHolder(Integer.toString(event.getOfferCount()) + ""));
            } else {
                drawer.updateBadge(ID_PUBLIC_OFFERS, null);
            }
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " public offers");
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(PersonalOffersAvailableResponseHandler.AvailablePersonalOffersEvent event) {
        try {
            if (event.getOfferCount() > 0) {
                drawer.updateBadge(ID_PERSONAL_OFFERS, new StringHolder(Integer.toString(event.getOfferCount()) + ""));
            } else {
                drawer.updateBadge(ID_PERSONAL_OFFERS, null);
            }
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " personal offers");
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(DemoOffersAvailableResponseHandler.AvailableDemoOffersEvent event) {
        try {
            if (event.getOfferCount() > 0) {
                drawer.updateBadge(ID_DEMO_OFFERS, new StringHolder(Integer.toString(event.getOfferCount()) + ""));
            } else {
                drawer.updateBadge(ID_DEMO_OFFERS, null);
            }
            Timber.tag(TAG).d("received " + Integer.toString(event.getOfferCount()) + " demo offers");
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }


    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ScheduledBatchesAvailableResponseHandler.ScheduledBatchUpdateEvent event) {
        try {
            if (event.getBatchCount() > 0) {
                drawer.updateBadge(ID_SCHEDULED_WORK, new StringHolder(Integer.toString(event.getBatchCount()) + ""));
            } else {
                drawer.updateBadge(ID_SCHEDULED_WORK, null);
            }
            Timber.tag(TAG).d("received " + Integer.toString(event.getBatchCount()) + " scheduled batches");
        } catch (Exception e) {
            Timber.tag(TAG).e(e);
        }
    }

    //// UI update events - claim offer result

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimOfferResponseHandler.ClaimOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimOfferResponseHandler.ClaimOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim offer SUCCESS!");

        EventBus.getDefault().postSticky(new OfferClaimAlerts.ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityOffers(activity);

    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimOfferResponseHandler.ClaimOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimOfferResponseHandler.ClaimOfferFailureEvent.class);

        Timber.tag(TAG).d("claim offer FAILURE!");


        EventBus.getDefault().postSticky(new OfferClaimAlerts.ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityOffers(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimOfferResponseHandler.ClaimOfferTimeoutEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimOfferResponseHandler.ClaimOfferTimeoutEvent.class);

        Timber.tag(TAG).d("claim offer TIMEOUT!");

        EventBus.getDefault().postSticky(new OfferClaimAlerts.ShowClaimOfferTimeoutAlertEvent());
        navigator.gotoActivityOffers(activity);
    }

    /// UI update events - claim demo offer result
    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimDemoOfferResponseHandler.ClaimDemoOfferSuccessEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimDemoOfferResponseHandler.ClaimDemoOfferSuccessEvent.class);

        Timber.tag(TAG).d("claim demo offer SUCCESS!");

        EventBus.getDefault().postSticky(new OfferClaimAlerts.ShowClaimOfferSuccessAlertEvent());
        navigator.gotoActivityHome(activity);
    }

    @Subscribe(sticky=true, threadMode = ThreadMode.MAIN)
    public void onEvent(ClaimDemoOfferResponseHandler.ClaimDemoOfferFailureEvent event) {
        EventBus.getDefault().removeStickyEvent(ClaimDemoOfferResponseHandler.ClaimDemoOfferFailureEvent.class);

        Timber.tag(TAG).d("claim demo offer FAILURE!");


        EventBus.getDefault().postSticky(new OfferClaimAlerts.ShowClaimOfferFailureAlertEvent());
        navigator.gotoActivityHome(activity);
    }

}
