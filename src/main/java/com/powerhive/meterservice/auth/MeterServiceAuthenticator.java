/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.auth;

import com.powerhive.meterservice.core.User;
import com.google.common.base.Optional;

import io.dropwizard.auth.AuthenticationException;
import io.dropwizard.auth.Authenticator;
import io.dropwizard.auth.basic.BasicCredentials;

// TODO: Auto-generated Javadoc
/**
 * Basic username password authentication.
 *
 * @author shermes641
 */
public class MeterServiceAuthenticator implements Authenticator<BasicCredentials, User> {
    
    /* (non-Javadoc)
     * @see io.dropwizard.auth.Authenticator#authenticate(java.lang.Object)
     */
    @Override
    /**
     * Used to protect a resouce.
     * Currently not checking DB for valid user
     * Accepts any username and passord of "secret"
     */
    public Optional<User> authenticate(BasicCredentials credentials) throws AuthenticationException {
        if ("secret".equals(credentials.getPassword())) {
            return Optional.of(new User(credentials.getUsername()));
        }
        return Optional.absent();
    }
}
