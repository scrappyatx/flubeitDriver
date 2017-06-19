/*
 * Copyright (c) 2017. scrapdoodle, LLC.  All Rights Reserved
 */

package it.flube.driver.modelLayer.useCases.driver.signIn;

/**
 * Created on 6/6/2017
 * Project : Driver
 */

public interface UseCaseSignInFromNetworkCallback {

    void signInFromNetworkSuccess();

    void signInFromNetworkFailure(String failureMessage);

    void signInFromNetworkAuthenticationFailure(String failureMessage);
}
