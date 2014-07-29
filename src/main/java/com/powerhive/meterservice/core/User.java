/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.core;
// TODO: Auto-generated Javadoc

/**
 * Eventually used for authentication.
 *
 * @author shermes641
 */
public class User {
    
    /** The name. */
    private final String name;

    /**
     * Instantiates a new user.
     *
     * @param name the name
     */
    public User(String name) {
        this.name = name;
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }
}
