/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.quice;

import com.codahale.metrics.health.HealthCheck;

// TODO: Auto-generated Javadoc
/**
 * The Class InjectableHealthCheck.
 */
public abstract class InjectableHealthCheck extends HealthCheck {
    
    /**
     * Gets the name.
     *
     * @return the name
     */
    public abstract String getName();
}
