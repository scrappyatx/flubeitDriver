/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.ServiceOrderNotificationSettings;

import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.STEP_LATE_MINUTES_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.STEP_VERY_LATE_MINUTES_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.STEP_LATE_MINUTES_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.STEP_VERY_LATE_MINUTES_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.STEP_LATE_MINUTES_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.STEP_VERY_LATE_MINUTES_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.STEP_LATE_MINUTES_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.STEP_VERY_LATE_MINUTES_STAGING;

/**
 * Created on 1/8/2019
 * Project : Driver
 */
public class ServiceOrderNotificationSettingsBuilder {
    private ServiceOrderNotificationSettings settings;

    private ServiceOrderNotificationSettingsBuilder(Builder builder){
        this.settings = builder.settings;
    }

    private ServiceOrderNotificationSettings getSettings(){
        return this.settings;
    }

    public static class Builder {
        private ServiceOrderNotificationSettings settings;

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            this.settings = new ServiceOrderNotificationSettings();

            switch(targetEnvironment){
                case PRODUCTION:
                    this.settings.setSendTextToServiceProviderWhenOrderStarted(SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION);
                    this.settings.setSendEmailToServiceProviderWhenOrderStarted(SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION);
                    this.settings.setSendVoiceCallToServiceProviderWhenOrderStarted(SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_PRODUCTION);

                    this.settings.setSendTextToCustomerWhenDriverNavigatingToTheirLocation(SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_PRODUCTION);
                    this.settings.setSendEmailToCustomerWhenDriverNavigatingToTheirLocation(SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_PRODUCTION);

                    this.settings.setStepLateMinutes(STEP_LATE_MINUTES_PRODUCTION);
                    this.settings.setStepVeryLateMinutes(STEP_VERY_LATE_MINUTES_PRODUCTION);

                    this.settings.setSendAppNotificationToDriverWhenStepIsLate(SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION);
                    this.settings.setSendTextToDriverWhenStepIsLate(SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION);
                    this.settings.setVoiceCallDriverWhenStepIsVeryLate(SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_PRODUCTION);

                    this.settings.setSendTextToAdminWhenStepIsLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_PRODUCTION);
                    this.settings.setSendTextToAdminWhenStepIsVeryLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_PRODUCTION);

                    break;
                case DEMO:
                    this.settings.setSendTextToServiceProviderWhenOrderStarted(SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEMO);
                    this.settings.setSendEmailToServiceProviderWhenOrderStarted(SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEMO);
                    this.settings.setSendVoiceCallToServiceProviderWhenOrderStarted(SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEMO);

                    this.settings.setSendTextToCustomerWhenDriverNavigatingToTheirLocation(SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEMO);
                    this.settings.setSendEmailToCustomerWhenDriverNavigatingToTheirLocation(SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEMO);

                    this.settings.setStepLateMinutes(STEP_LATE_MINUTES_DEMO);
                    this.settings.setStepVeryLateMinutes(STEP_VERY_LATE_MINUTES_DEMO);

                    this.settings.setSendAppNotificationToDriverWhenStepIsLate(SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_DEMO);
                    this.settings.setSendTextToDriverWhenStepIsLate(SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_DEMO);
                    this.settings.setVoiceCallDriverWhenStepIsVeryLate(SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_DEMO);

                    this.settings.setSendTextToAdminWhenStepIsLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_DEMO);
                    this.settings.setSendTextToAdminWhenStepIsVeryLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_DEMO);


                    break;
                case STAGING:
                    this.settings.setSendTextToServiceProviderWhenOrderStarted(SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_STAGING);
                    this.settings.setSendEmailToServiceProviderWhenOrderStarted(SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_STAGING);
                    this.settings.setSendVoiceCallToServiceProviderWhenOrderStarted(SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_STAGING);

                    this.settings.setSendTextToCustomerWhenDriverNavigatingToTheirLocation(SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_STAGING);
                    this.settings.setSendEmailToCustomerWhenDriverNavigatingToTheirLocation(SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_STAGING);

                    this.settings.setStepLateMinutes(STEP_LATE_MINUTES_STAGING);
                    this.settings.setStepVeryLateMinutes(STEP_VERY_LATE_MINUTES_STAGING);

                    this.settings.setSendAppNotificationToDriverWhenStepIsLate(SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_STAGING);
                    this.settings.setSendTextToDriverWhenStepIsLate(SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_STAGING);
                    this.settings.setVoiceCallDriverWhenStepIsVeryLate(SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_STAGING);

                    this.settings.setSendTextToAdminWhenStepIsLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_STAGING);
                    this.settings.setSendTextToAdminWhenStepIsVeryLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_STAGING);

                    break;
                case DEVELOPMENT:
                    this.settings.setSendTextToServiceProviderWhenOrderStarted(SEND_TEXT_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT);
                    this.settings.setSendEmailToServiceProviderWhenOrderStarted(SEND_EMAIL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT);
                    this.settings.setSendVoiceCallToServiceProviderWhenOrderStarted(SEND_VOICE_CALL_TO_SERVICE_PROVIDER_WHEN_ORDER_IS_STARTED_DEVELOPMENT);

                    this.settings.setSendTextToCustomerWhenDriverNavigatingToTheirLocation(SEND_TEXT_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEVELOPMENT);
                    this.settings.setSendEmailToCustomerWhenDriverNavigatingToTheirLocation(SEND_EMAIL_TO_CUSTOMER_WHEN_DRIVER_NAVIGATING_TO_THEIR_LOCATION_DEVELOPMENT);

                    this.settings.setStepLateMinutes(STEP_LATE_MINUTES_DEVELOPMENT);
                    this.settings.setStepVeryLateMinutes(STEP_VERY_LATE_MINUTES_DEVELOPMENT);

                    this.settings.setSendAppNotificationToDriverWhenStepIsLate(SEND_APP_NOTIFICATION_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT);
                    this.settings.setSendTextToDriverWhenStepIsLate(SEND_TEXT_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT);
                    this.settings.setVoiceCallDriverWhenStepIsVeryLate(SEND_VOICE_CALL_TO_DRIVER_WHEN_STEP_IS_LATE_DEVELOPMENT);

                    this.settings.setSendTextToAdminWhenStepIsLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_LATE_DEVELOPMENT);
                    this.settings.setSendTextToAdminWhenStepIsVeryLate(SEND_TEXT_TO_ADMIN_WHEN_STEP_IS_VERY_LATE_DEVELOPMENT);

                    break;
            }
        }

        public Builder sendTextToServiceProviderWhenOrderStarted(Boolean sendTextToServiceProviderWhenOrderStarted ){
            this.settings.setSendTextToServiceProviderWhenOrderStarted(sendTextToServiceProviderWhenOrderStarted);
            return this;
        }

        public Builder sendEmailToServiceProviderWhenOrderStarted(Boolean sendEmailToServiceProviderWhenOrderStarted){
            this.settings.setSendEmailToServiceProviderWhenOrderStarted(sendEmailToServiceProviderWhenOrderStarted);
            return this;
        }

        public Builder sendVoiceCallToServiceProviderWhenOrderStarted(Boolean sendVoiceCallToServiceProviderWhenOrderStarted){
            this.settings.setSendVoiceCallToServiceProviderWhenOrderStarted(sendVoiceCallToServiceProviderWhenOrderStarted);
            return this;
        }

        public Builder sendTextToCustomerWhenDriverNavigatingToTheirLocation(Boolean sendTextToCustomerWhenDriverNavigatingToTheirLocation){
            this.settings.setSendTextToCustomerWhenDriverNavigatingToTheirLocation(sendTextToCustomerWhenDriverNavigatingToTheirLocation);
            return this;
        }

        public Builder sendEmailToCustomerWhenDriverNavigatingToTheirLocation(Boolean sendEmailToCustomerWhenDriverNavigatingToTheirLocation){
            this.settings.setSendEmailToCustomerWhenDriverNavigatingToTheirLocation(sendEmailToCustomerWhenDriverNavigatingToTheirLocation);
            return this;
        }

        public Builder stepLateMinutes(int stepLateMinutes){
            this.settings.setStepLateMinutes(stepLateMinutes);
            return this;
        }

        public Builder stepVeryLateMinutes(int stepVeryLateMinutes){
            this.settings.setStepVeryLateMinutes(stepVeryLateMinutes);
            return this;
        }

        public Builder sendAppNotificationToDriverWhenStepIsLate(Boolean sendAppNotificationToDriverWhenStepIsLate){
            this.settings.setSendAppNotificationToDriverWhenStepIsLate(sendAppNotificationToDriverWhenStepIsLate);
            return this;
        }

        public Builder sendTextToDriverWhenStepIsLate(Boolean sendTextToDriverWhenStepIsLate){
            this.settings.setSendTextToDriverWhenStepIsLate(sendTextToDriverWhenStepIsLate);
            return this;
        }

        public Builder voiceCallDriverWhenStepIsVeryLate(Boolean voiceCallDriverWhenStepIsVeryLate){
            this.settings.setVoiceCallDriverWhenStepIsVeryLate(voiceCallDriverWhenStepIsVeryLate);
            return this;
        }

        public Builder sendTextToAdminWhenStepIsLate(Boolean sendTextToAdminWhenStepIsLate){
            this.settings.setSendTextToAdminWhenStepIsLate(sendTextToAdminWhenStepIsLate);
            return this;
        }

        public Builder sendTextToAdminWhenStepIsVeryLate(Boolean sendTextToAdminWhenStepIsVeryLate){
            this.settings.setSendTextToAdminWhenStepIsVeryLate(sendTextToAdminWhenStepIsVeryLate);
            return this;
        }

        private void validate(ServiceOrderNotificationSettings settings){
            // do nothing here
        }

        public ServiceOrderNotificationSettings build(){
            ServiceOrderNotificationSettings settings = new ServiceOrderNotificationSettingsBuilder(this).getSettings();
            validate(settings);
            return settings;
        }
    }
}
