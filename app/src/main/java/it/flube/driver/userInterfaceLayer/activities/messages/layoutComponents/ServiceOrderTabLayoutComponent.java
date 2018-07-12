/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.userInterfaceLayer.activities.messages.layoutComponents;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.util.ArrayList;
import java.util.Map;

import it.flube.driver.R;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 7/6/2018
 * Project : Driver
 */
public class ServiceOrderTabLayoutComponent
        implements TabLayout.OnTabSelectedListener {

    private static final String TAG = "ServiceOrderTabLayoutComponent";

    private TabLayout tabLayout;

    private ContactPersonCustomerLayoutComponent customer;
    private ContactPersonServiceProviderLayoutComponent serviceProvider;

    private Boolean thisOrderHasCustomer;
    private Boolean thisOrderHasServiceProvider;

    private ArrayList<String> serviceOrderList;
    private ContactPersonsByServiceOrder contactMap;

    public ServiceOrderTabLayoutComponent(AppCompatActivity activity){
        tabLayout = (TabLayout) activity.findViewById(R.id.service_order_tab);
        tabLayout.addOnTabSelectedListener(this);

        customer = new ContactPersonCustomerLayoutComponent(activity);
        serviceProvider = new ContactPersonServiceProviderLayoutComponent(activity);

        thisOrderHasCustomer = false;
        thisOrderHasServiceProvider = false;
        Timber.tag(TAG).d("...ServiceOrderTabLayoutComponent created");
    }

    public void setValues(ArrayList<String> serviceOrderList, ContactPersonsByServiceOrder contactMap){
        tabLayout.setVisibility(View.INVISIBLE);
        this.serviceOrderList = serviceOrderList;
        this.contactMap = contactMap;

        //set the number of tabs for the number of service orders
        tabLayout.removeAllTabs();

        for (int i = 0; i < serviceOrderList.size(); i++){
            String tabText = "Order " + Integer.toString(i+1);
            tabLayout.addTab(tabLayout.newTab().setText(tabText));
        }
        Timber.tag(TAG).d("...setValues");
    }

    public ContactPerson getCustomerContactPerson(){
        if (thisOrderHasCustomer) {
            return customer.getContactPerson();
        } else {
            return null;
        }
    }

    public ContactPerson getServiceProviderContactPerson(){
        if (thisOrderHasServiceProvider) {
            return serviceProvider.getContactPerson();
        } else {
            return null;
        }
    }

    public void setVisible(){
        tabLayout.setVisibility(View.VISIBLE);
        showTab(tabLayout.getSelectedTabPosition());
        Timber.tag(TAG).d("...setVisible");
    }

    public void setInvisible(){
        tabLayout.setVisibility(View.INVISIBLE);
        customer.setGone();
        serviceProvider.setGone();
        Timber.tag(TAG).d("...set INVISIBLE");
    }

    public void setGone(){
        tabLayout.setVisibility(View.GONE);
        customer.setGone();
        serviceProvider.setGone();

        Timber.tag(TAG).d("...set GONE");
    }

    //// interface for TabLayout listener

    public void onTabSelected(TabLayout.Tab tab) {
        Timber.tag(TAG).d("selected tab -> " + tab.getPosition() + " : " + tab.getText());
        showTab(tab.getPosition());
    }

    public void onTabReselected(TabLayout.Tab tab){
        Timber.tag(TAG).d("re selected tab -> " + tab.getPosition() + " : " + tab.getText());
    }

    public void onTabUnselected(TabLayout.Tab tab){
        Timber.tag(TAG).d("un selected tab -> " + tab.getPosition() + " : " + tab.getText());
    }

    private void showTab(Integer tabPosition){
        Timber.tag(TAG).d("...showing tab " + tabPosition);
        setData(tabPosition);
    }

    private void setData(Integer position){
        // hide the customer & service provider
        customer.setGone();
        serviceProvider.setGone();

        //assume no customer or service provider for this order
        thisOrderHasCustomer = false;
        thisOrderHasServiceProvider = false;

        //get the service order guid
        String serviceOrderGuid = serviceOrderList.get(position);

        //now find the customer & service provider contacts for this service order (if there are any) and make them visible
        for (Map.Entry<String, ContactPerson> thisContact : contactMap.getContactPersonsByServiceOrder().get(serviceOrderGuid).entrySet()){
            switch (thisContact.getValue().getContactRole()){
                case CUSTOMER:
                    customer.setValues(thisContact.getValue());
                    customer.setVisible();
                    thisOrderHasCustomer = true;
                    break;
                case SERVICE_PROVIDER:
                    serviceProvider.setValues(thisContact.getValue());
                    serviceProvider.setVisible();
                    thisOrderHasServiceProvider = true;
                    break;
            }
        }
    }

}
