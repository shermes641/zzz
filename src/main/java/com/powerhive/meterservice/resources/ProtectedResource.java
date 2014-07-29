/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.resources;

import com.powerhive.meterservice.core.User;

import io.dropwizard.auth.Auth;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// TODO: Auto-generated Javadoc
/**
 * Example authentification before allowing access to a resource.
 *
 * @author shermes641
 */
@Path("/protected")
@Produces(MediaType.TEXT_PLAIN)
public class ProtectedResource {
    
    /**
     * Show secret.
     *
     * @param user the user
     * @return the string
     */
    @GET
    public String showSecret(@Auth User user) {
        return String.format("Hey there, %s. You know the secret!", user.getName());
    }
}
