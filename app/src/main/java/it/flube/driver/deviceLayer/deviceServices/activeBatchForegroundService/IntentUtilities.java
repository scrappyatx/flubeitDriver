/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.deviceLayer.deviceServices.activeBatchForegroundService;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import it.flube.driver.R;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.authorizePaymentStep.AuthorizePaymentActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.giveAssetStep.GiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.navigationStep.NavigationActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.photoStep.photoList.PhotoActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.receiveAssetStep.ReceiveAssetActivity;
import it.flube.driver.userInterfaceLayer.activities.activeBatch.orderSteps.userTriggerStep.UserTriggerActivity;
import it.flube.driver.userInterfaceLayer.activities.home.HomeActivity;
import it.flube.libbatchdata.interfaces.OrderStepInterface;
import timber.log.Timber;

/**
 * Created on 8/30/2018
 * Project : Driver
 */
public class IntentUtilities {
    private static final String TAG="IntentUtilities";

    private static final String EXTRA_ACTION = "action";
    private static final String EXTRA_NOTIFICATION_TEXT = "notificationText";
    private static final String EXTRA_NOTIFICATION_SUBTEXT = "notificationSubText";
    private static final String EXTRA_CLIENT_ID = "clientId";
    private static final String EXTRA_BATCH_GUID = "batchGuid";
    private static final String EXTRA_SERVICE_ORDER_GUID = "serviceOrderGuid";
    private static final String EXTRA_ORDER_STEP_GUID = "orderStepGuid";
    private static final String EXTRA_TASK_TYPE = "taskType";

    public static final String ACTION_START = "start";
    public static final String ACTION_SHUTDOWN = "shutdown";
    public static final String ACTION_UPDATE = "update";
    public static final String ACTION_GOTO_ACTIVE_BATCH = "goto";

    public static Intent getStartIntent(Context appContext, String clientId, String batchGuid, String serviceOrderGuid, String orderStepGuid, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType){
        Intent i = new Intent(appContext,ActiveBatchForegroundService.class);
        i.putExtra(EXTRA_ACTION, ACTION_START);
        fillIntentBody(i, clientId, batchGuid, serviceOrderGuid, orderStepGuid, notificationText, notificationSubText, taskType);
        return i;
    }

    public static Intent getShutdownIntent(Context appContext) {
        Intent i = new Intent(appContext,ActiveBatchForegroundService.class);
        i.putExtra(EXTRA_ACTION, ACTION_SHUTDOWN);
        return i;
    }

    public static Intent getUpdateIntent(Context appContext, String clientId, String batchGuid, String serviceOrderGuid, String orderStepGuid, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType){
        //Intent i = new Intent(context, ActiveBatchForegroundService.class);
        Intent i = new Intent(appContext, ActiveBatchForegroundService.class);
        i.putExtra(EXTRA_ACTION, ACTION_UPDATE);
        fillIntentBody(i, clientId, batchGuid, serviceOrderGuid, orderStepGuid, notificationText, notificationSubText, taskType);
        return i;
    }

    private static void fillIntentBody(Intent i, String clientId, String batchGuid, String serviceOrderGuid, String orderStepGuid, String notificationText, String notificationSubText, OrderStepInterface.TaskType taskType){
        i.putExtra(EXTRA_NOTIFICATION_TEXT, notificationText);
        i.putExtra(EXTRA_NOTIFICATION_SUBTEXT, notificationSubText);
        i.putExtra(EXTRA_TASK_TYPE, taskType);

        i.putExtra(EXTRA_CLIENT_ID, clientId);

        i.putExtra(EXTRA_BATCH_GUID, batchGuid);
        i.putExtra(EXTRA_SERVICE_ORDER_GUID, serviceOrderGuid);
        i.putExtra(EXTRA_ORDER_STEP_GUID, orderStepGuid);
    }

    public static String getIntentAction(Intent i){
        String action;
        if (i.hasExtra(EXTRA_ACTION)) {
            action = i.getStringExtra(EXTRA_ACTION);
            Timber.tag(TAG).d("   ...action --> " + action);
        } else {
            action = null;
        }
        return action;
    }

    public static String getExtraNotificationText(Context context, Intent i){
        String notificationText;

        Timber.tag(TAG).d("      getting notification text...");

        if (i.hasExtra(EXTRA_NOTIFICATION_TEXT)){
            notificationText = i.getStringExtra(EXTRA_NOTIFICATION_TEXT);
            Timber.tag(TAG).d("         ...got from intent extra");
        } else {
            notificationText = context.getResources().getString(R.string.active_batch_service_notification_text);
            Timber.tag(TAG).w("         ...no intent extra, got default from resource");
        }

        Timber.tag(TAG).d("      ...notificationText = " + notificationText);
        return notificationText;
    }

    public static String getExtraNotificationSubtext(Context context, Intent i){
        String notificationSubText;

        Timber.tag(TAG).d("      getting notification subText...");

        if (i.hasExtra(EXTRA_NOTIFICATION_SUBTEXT)){
            notificationSubText = i.getStringExtra(EXTRA_NOTIFICATION_SUBTEXT);
            Timber.tag(TAG).d("         ...got from intent extra");
        } else {
            notificationSubText = context.getResources().getString(R.string.active_batch_service_notification_subtext);
            Timber.tag(TAG).w("         ...no intent extra, got default from resource");
        }

        Timber.tag(TAG).d("      ...notificationSubText = " + notificationSubText);
        return notificationSubText;
    }

    public static OrderStepInterface.TaskType getExtraTaskType(Intent i){
        OrderStepInterface.TaskType taskType;
        Timber.tag(TAG).d("      getting task type...");

        if (i.hasExtra(EXTRA_TASK_TYPE)){
            taskType = (OrderStepInterface.TaskType) i.getSerializableExtra(EXTRA_TASK_TYPE);
            Timber.tag(TAG).d("         ...got from intent extra");
        } else {
            taskType = OrderStepInterface.TaskType.WAIT_FOR_USER_TRIGGER;
            Timber.tag(TAG).w("         ...no intent extra, just picked one for default");
        }

        Timber.tag(TAG).d("      ...taskType = " + taskType.toString());
        return taskType;
    }

    public static String getExtraClientId(Intent i){
        Timber.tag(TAG).d("      getExtraClientId...");
        String clientId;
        if (i.hasExtra(EXTRA_CLIENT_ID)){
            clientId = i.getStringExtra(EXTRA_CLIENT_ID);
        } else {
            clientId = null;
        }
        return clientId;
    }

    public static String getExtraBatchGuid(Intent i){
        Timber.tag(TAG).d("     getExtraBatchGuid...");
        String batchGuid;
        if (i.hasExtra(EXTRA_BATCH_GUID)){
            batchGuid = i.getStringExtra(EXTRA_BATCH_GUID);
        } else {
            batchGuid = null;
        }
        return batchGuid;
    }

    public static String getExtraServiceOrderGuid(Intent i){
        Timber.tag(TAG).d("     getExtraServiceOrderGuid...");
        String serviceOrderGuid;
        if (i.hasExtra(EXTRA_SERVICE_ORDER_GUID)){
            serviceOrderGuid = i.getStringExtra(EXTRA_SERVICE_ORDER_GUID);
        } else {
            serviceOrderGuid = null;
        }
        return serviceOrderGuid;
    }

    public static String getExtraOrderStepGuid(Intent i){
        Timber.tag(TAG).d("     getExtraOrderStepGuid...");
        String orderStepGuid;
        if (i.hasExtra(EXTRA_ORDER_STEP_GUID)){
            orderStepGuid = i.getStringExtra(EXTRA_ORDER_STEP_GUID);
        } else {
            orderStepGuid = null;
        }
        return orderStepGuid;
    }

    public static PendingIntent getNotificationClickIntent(Context context, OrderStepInterface.TaskType taskType){
        //Intent i = new Intent(getApplicationContext(), ActiveBatchForegroundService.class);
        //i.putExtra(EXTRA_ACTION, ACTION_GOTO_ACTIVE_BATCH);
        // create pending intent.  Uses system time in milliseconds to have a unique ID for the pending intent
        //return PendingIntent.getService(getApplicationContext(), (int) System.currentTimeMillis(), i, 0);

        Intent i;
        switch (taskType){
            case GIVE_ASSET:
                i = new Intent(context.getApplicationContext(), GiveAssetActivity.class);
                break;
            case RECEIVE_ASSET:
                i = new Intent(context.getApplicationContext(), ReceiveAssetActivity.class);
                break;
            case AUTHORIZE_PAYMENT:
                i = new Intent(context.getApplicationContext(), AuthorizePaymentActivity.class);
                break;
            case WAIT_FOR_USER_TRIGGER:
                i = new Intent(context.getApplicationContext(), UserTriggerActivity.class);
                break;
            case NAVIGATION:
                i = new Intent(context.getApplicationContext(), NavigationActivity.class);
                break;
            case TAKE_PHOTOS:
                i = new Intent(context.getApplicationContext(), PhotoActivity.class);
                break;
            default:
                i = new Intent(context.getApplicationContext(), HomeActivity.class);
                break;
        }
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        return PendingIntent.getActivity(context, (int) System.currentTimeMillis(), i, PendingIntent.FLAG_UPDATE_CURRENT);
    }

}
