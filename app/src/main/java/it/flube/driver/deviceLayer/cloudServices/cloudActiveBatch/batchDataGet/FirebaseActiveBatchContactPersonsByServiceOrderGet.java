/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.batchDataGet;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import it.flube.driver.modelLayer.interfaces.CloudActiveBatchInterface;
import it.flube.driver.modelLayer.interfaces.CloudDatabaseInterface;
import it.flube.libbatchdata.entities.ContactPerson;
import it.flube.libbatchdata.entities.ContactPersonsByServiceOrder;
import it.flube.libbatchdata.entities.serviceOrder.ServiceOrder;
import timber.log.Timber;

import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_CONTACT_PERSONS_BY_SERVICE_ORDER_NODE;
import static it.flube.driver.deviceLayer.cloudServices.cloudActiveBatch.ActiveBatchFirebaseConstants.BATCH_DATA_SERVICE_ORDER_NODE;

/**
 * Created on 7/5/2018
 * Project : Driver
 */
public class FirebaseActiveBatchContactPersonsByServiceOrderGet implements ValueEventListener {
    private static final String TAG = "FirebaseActiveBatchContactPersonsByServiceOrderGet";


    private CloudActiveBatchInterface.GetContactPersonsByServiceOrderResponse response;

    public void getContactPersonsByServiceOrder(DatabaseReference batchDataRef, String batchGuid, CloudActiveBatchInterface.GetContactPersonsByServiceOrderResponse response){
        this.response = response;

        Timber.tag(TAG).d("batchDataRef = " + batchDataRef.toString());
        Timber.tag(TAG).d("getting contactPersonsByServiceOrder for batchGuid : " + batchGuid);

        batchDataRef.child(batchGuid).child(BATCH_DATA_CONTACT_PERSONS_BY_SERVICE_ORDER_NODE)
                .addListenerForSingleValueEvent(this);

    }

    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
        Timber.tag(TAG).d("...onDataChange");


        if (dataSnapshot.exists()) {
            Timber.tag(TAG).d("   ...contactPersonsByServiceOrder data FOUND!");

            //iterate through children
            if (dataSnapshot.hasChildren()){
                Timber.tag(TAG).d("   ...dataSnapshot has children");

                try {
                    //create a contactPersonByServiceOrderHashMap
                    HashMap<String, HashMap<String, ContactPerson>> contactPersonByServiceOrderMap = new HashMap<String, HashMap<String, ContactPerson>>();

                    for (DataSnapshot serviceOrderChild : dataSnapshot.getChildren()) {
                        Timber.tag(TAG).d("       ...serviceOrderGuid -> " + serviceOrderChild.getKey());

                        //make a hashmap to put all the contact persons for this service order
                        HashMap<String, ContactPerson> contactPersonHashMap = new HashMap<String, ContactPerson>();

                        if (serviceOrderChild.hasChildren()) {
                            for (DataSnapshot contactPersonChild : serviceOrderChild.getChildren()) {
                                Timber.tag(TAG).d("         contactPersonGuid -> " + contactPersonChild.getKey());
                                contactPersonHashMap.put(contactPersonChild.getKey(), contactPersonChild.getValue(ContactPerson.class));
                            }
                        }
                        //put an entry into the hashmap for this service order
                        contactPersonByServiceOrderMap.put(serviceOrderChild.getKey(), contactPersonHashMap);
                    }

                    //put the orderMap into the response object
                    Timber.tag(TAG).d("    ...finished looping through children, building reponse");
                    ContactPersonsByServiceOrder contactList = new ContactPersonsByServiceOrder();
                    contactList.setContactPersonsByServiceOrder(contactPersonByServiceOrderMap);
                    response.cloudGetActiveBatchContactPersonsByServiceOrderSuccess(contactList);
                    Timber.tag(TAG).d("    ...FINISHED SUCCESS");

                } catch (Exception e){
                    Timber.tag(TAG).w("   ...ERROR");
                    Timber.tag(TAG).e(e);
                    response.cloudGetActiveBatchContactPersonsByServiceOrderFailure();
                }

            } else {
                //no children
                Timber.tag(TAG).d("   ...dataSnapshot has no children");
                response.cloudGetActiveBatchContactPersonsByServiceOrderFailure();
            }
        } else {
            // dataSnapshot DOES NOT EXIST
            Timber.tag(TAG).d("   ...dataSnapshot does not exist");
            response.cloudGetActiveBatchContactPersonsByServiceOrderFailure();
        }
    }

    public void onCancelled(@NonNull DatabaseError databaseError) {
        Timber.tag(TAG).w("...onCancelled -->  error : " + databaseError.getCode() + " --> " + databaseError.getMessage());
        response.cloudGetActiveBatchContactPersonsByServiceOrderFailure();
    }
}
