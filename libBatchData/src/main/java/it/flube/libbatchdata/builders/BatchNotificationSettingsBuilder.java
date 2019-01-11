/*
 * Copyright (c) 2019. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;

import it.flube.libbatchdata.constants.TargetEnvironmentConstants;
import it.flube.libbatchdata.entities.BatchNotificationSettings;

import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDemo.VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEMO;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsDevelopment.VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEVELOPMENT;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsProduction.VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_PRODUCTION;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_STAGING;
import static it.flube.libbatchdata.constants.EnvironmentConstantsStaging.VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_STAGING;

/**
 * Created on 1/8/2019
 * Project : Driver
 */
public class BatchNotificationSettingsBuilder {
    private BatchNotificationSettings settings;

    private BatchNotificationSettingsBuilder(Builder builder){
        this.settings = builder.settings;
    }

    private BatchNotificationSettings getSettings(){
        return this.settings;
    }

    public static class Builder {
        private BatchNotificationSettings settings;

        public Builder(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            initializeStuff(targetEnvironment);
        }

        private void initializeStuff(TargetEnvironmentConstants.TargetEnvironment targetEnvironment){
            this.settings = new BatchNotificationSettings();

            switch (targetEnvironment){
                case PRODUCTION:
                    this.settings.setSendEmailToCustomerWithOrderConfirmation(SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_PRODUCTION);

                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_PRODUCTION);
                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_PRODUCTION);

                    this.settings.setSendTextToAdminWhenOfferExpires(SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_PRODUCTION);

                    this.settings.setUpcomingScheduledBatchWarningMinutes(UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_PRODUCTION);
                    this.settings.setSendAppNotificationToDriverUpcomingScheduledBatch(SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_PRODUCTION);
                    this.settings.setSendTextToDriverUpcomingScheduledBatch(SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_PRODUCTION);

                    this.settings.setLateWarningTextScheduledBatchSeconds(LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_PRODUCTION);
                    this.settings.setLateWarningVoiceCallScheduledBatchSeconds(LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_PRODUCTION);
                    this.settings.setSendTextToDriverLateStartWarningScheduledBatch(SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_PRODUCTION);
                    this.settings.setVoiceCallToDriverLateStartWarningScheduledBatch(VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_PRODUCTION);

                    this.settings.setSendTextToAdminWhenScheduledBatchRemovedDueToNoStart(SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_PRODUCTION);

                    break;
                case DEMO:
                    this.settings.setSendEmailToCustomerWithOrderConfirmation(SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_DEMO);

                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_DEMO);
                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_DEMO);

                    this.settings.setSendTextToAdminWhenOfferExpires(SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_DEMO);

                    this.settings.setUpcomingScheduledBatchWarningMinutes(UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_DEMO);
                    this.settings.setSendAppNotificationToDriverUpcomingScheduledBatch(SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEMO);
                    this.settings.setSendTextToDriverUpcomingScheduledBatch(SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEMO);

                    this.settings.setLateWarningTextScheduledBatchSeconds(LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_DEMO);
                    this.settings.setLateWarningVoiceCallScheduledBatchSeconds(LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_DEMO);
                    this.settings.setSendTextToDriverLateStartWarningScheduledBatch(SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEMO);
                    this.settings.setVoiceCallToDriverLateStartWarningScheduledBatch(VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEMO);

                    this.settings.setSendTextToAdminWhenScheduledBatchRemovedDueToNoStart(SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_DEMO);

                    break;
                case STAGING:
                    this.settings.setSendEmailToCustomerWithOrderConfirmation(SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_STAGING);

                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_STAGING);
                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_STAGING);

                    this.settings.setSendTextToAdminWhenOfferExpires(SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_STAGING);

                    this.settings.setUpcomingScheduledBatchWarningMinutes(UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_STAGING);
                    this.settings.setSendAppNotificationToDriverUpcomingScheduledBatch(SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_STAGING);
                    this.settings.setSendTextToDriverUpcomingScheduledBatch(SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_STAGING);

                    this.settings.setLateWarningTextScheduledBatchSeconds(LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_STAGING);
                    this.settings.setLateWarningVoiceCallScheduledBatchSeconds(LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_STAGING);
                    this.settings.setSendTextToDriverLateStartWarningScheduledBatch(SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_STAGING);
                    this.settings.setVoiceCallToDriverLateStartWarningScheduledBatch(VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_STAGING);

                    this.settings.setSendTextToAdminWhenScheduledBatchRemovedDueToNoStart(SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_STAGING);

                    break;
                case DEVELOPMENT:
                    this.settings.setSendEmailToCustomerWithOrderConfirmation(SEND_EMAIL_TO_CUSTOMER_WITH_ORDER_CONFIRMATION_DEVELOPMENT);

                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PUBLIC_OFFER_DEVELOPMENT);
                    this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(SEND_TEXT_TO_DRIVER_WHEN_BATCH_IS_MADE_AVAILABLE_AS_PERSONAL_OFFER_DEVELOPMENT);

                    this.settings.setSendTextToAdminWhenOfferExpires(SEND_TEXT_TO_ADMIN_WHEN_OFFER_EXPIRES_DEVELOPMENT);

                    this.settings.setUpcomingScheduledBatchWarningMinutes(UPCOMING_SCHEDULED_BATCH_WARNING_MINUTES_DEVELOPMENT);
                    this.settings.setSendAppNotificationToDriverUpcomingScheduledBatch(SEND_APP_NOTIFICATION_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEVELOPMENT);
                    this.settings.setSendTextToDriverUpcomingScheduledBatch(SEND_TEXT_TO_DRIVER_UPCOMING_SCHEDULED_BATCH_DEVELOPMENT);

                    this.settings.setLateWarningTextScheduledBatchSeconds(LATE_WARNING_TEXT_SCHEDULED_BATCH_SECONDS_DEVELOPMENT);
                    this.settings.setLateWarningVoiceCallScheduledBatchSeconds(LATE_WARNING_VOICE_CALL_SCHEDULED_BATCH_SECONDS_DEVELOPMENT);
                    this.settings.setSendTextToDriverLateStartWarningScheduledBatch(SEND_TEXT_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEVELOPMENT);
                    this.settings.setVoiceCallToDriverLateStartWarningScheduledBatch(VOICE_CALL_TO_DRIVER_LATE_START_WARNING_SCHEDULED_BATCH_DEVELOPMENT);

                    this.settings.setSendTextToAdminWhenScheduledBatchRemovedDueToNoStart(SEND_TEXT_TO_ADMIN_WHEN_SCHEDULED_BATCH_REMOVED_DUE_TO_NO_START_DEVELOPMENT);

                    break;
            }
        }

        public Builder sendEmailToCustomerWithOrderConfirmation(Boolean sendEmailToCustomerWithOrderConfirmation){
            this.settings.setSendEmailToCustomerWithOrderConfirmation(sendEmailToCustomerWithOrderConfirmation);
            return this;
        }

        public Builder sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(Boolean sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer){
            this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer(sendTextToDriverWhenBatchIsMadeAvailableAsPublicOffer);
            return this;
        }

        public Builder sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(Boolean sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer){
            this.settings.setSendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer(sendTextToDriverWhenBatchIsMadeAvailableAsPersonalOffer);
            return this;
        }

        public Builder sendTextToAdminWhenOfferExpires(Boolean sendTextToAdminWhenOfferExpires){
            this.settings.setSendTextToAdminWhenOfferExpires(sendTextToAdminWhenOfferExpires);
            return this;
        }

        public Builder upcomingScheduledBatchWarningMinutes(int upcomingScheduledBatchWarningMinutes){
            this.settings.setUpcomingScheduledBatchWarningMinutes(upcomingScheduledBatchWarningMinutes);
            return this;
        }

        public Builder sendAppNotificationToDriverUpcomingScheduledBatch(Boolean sendAppNotificationToDriverUpcomingScheduledBatch){
            this.settings.setSendAppNotificationToDriverUpcomingScheduledBatch(sendAppNotificationToDriverUpcomingScheduledBatch);
            return this;
        }

        public Builder sendTextToDriverUpcomingScheduledBatch(Boolean sendTextToDriverUpcomingScheduledBatch){
            this.settings.setSendTextToDriverUpcomingScheduledBatch(sendTextToDriverUpcomingScheduledBatch);
            return this;
        }

        public Builder lateWarningTextScheduledBatchSeconds(int lateWarningTextScheduledBatchSeconds){
            this.settings.setLateWarningTextScheduledBatchSeconds(lateWarningTextScheduledBatchSeconds);
            return this;
        }

        public Builder lateWarningVoiceCallScheduledBatchSeconds(int lateWarningVoiceCallScheduledBatchSeconds){
            this.settings.setLateWarningVoiceCallScheduledBatchSeconds(lateWarningVoiceCallScheduledBatchSeconds);
            return this;
        }

        public Builder sendTextToDriverLateStartWarningScheduledBatch(Boolean sendTextToDriverLateStartWarningScheduledBatch){
            this.settings.setSendTextToDriverLateStartWarningScheduledBatch(sendTextToDriverLateStartWarningScheduledBatch);
            return this;
        }

        public Builder voiceCallToDriverLateStartWarningScheduledBatch(Boolean voiceCallToDriverLateStartWarningScheduledBatch){
            this.settings.setVoiceCallToDriverLateStartWarningScheduledBatch(voiceCallToDriverLateStartWarningScheduledBatch);
            return this;
        }

        public Builder sendTextToAdminWhenScheduledBatchRemovedDueToNoStart(Boolean sendTextToAdminWhenScheduledBatchRemovedDueToNoStart){
            this.settings.setSendTextToAdminWhenScheduledBatchRemovedDueToNoStart(sendTextToAdminWhenScheduledBatchRemovedDueToNoStart);
            return this;
        }

        private void validate(BatchNotificationSettings settings){
            //do nothing
        }

        public BatchNotificationSettings build(){
            BatchNotificationSettings settings = new BatchNotificationSettingsBuilder(this).getSettings();
            validate(settings);
            return settings;
        }
    }
}
