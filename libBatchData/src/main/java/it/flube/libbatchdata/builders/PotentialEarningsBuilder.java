/*
 * Copyright (c) 2018. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.libbatchdata.builders;


import it.flube.libbatchdata.entities.PotentialEarnings;

/**
 * Created on 9/4/2017
 * Project : Driver
 */

public class PotentialEarningsBuilder {
    private PotentialEarnings potentialEarnings;

    private PotentialEarningsBuilder(Builder builder){
        this.potentialEarnings = builder.potentialEarnings;
    }

    private PotentialEarnings getPotentialEarnings(){
        return potentialEarnings;
    }
    public static class Builder {
        private PotentialEarnings potentialEarnings;

        public Builder(){
            potentialEarnings = new PotentialEarnings();
        }

        public Builder earningsType(PotentialEarnings.EarningsType earningsType) {
            this.potentialEarnings.setEarningsType(earningsType);
            return this;
        }

        public Builder plusTips(Boolean plusTips) {
            this.potentialEarnings.setPlusTips(plusTips);
            return this;
        }

        public Builder payRateInCents(Integer payRateInCents) {
            this.potentialEarnings.setPayRateInCents(payRateInCents);
            return this;
        }

        private void validate(PotentialEarnings potentialEarnings) {
            // required PRESENT (must not be null)
            if (potentialEarnings.getEarningsType() == null) {
                throw new IllegalStateException("earningsType is null");
            }
        }

        public PotentialEarnings build(){
            PotentialEarnings potentialEarnings = new PotentialEarningsBuilder(this).getPotentialEarnings();
            validate(potentialEarnings);
            return potentialEarnings;
        }
    }
}
