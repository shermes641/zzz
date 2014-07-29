/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.core.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// TODO: Auto-generated Javadoc
/**
 * The Interface Every.
 * 
 * @author shermes641
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Every {
    
    /**
     * Value.
     *
     * @return the string
     */
    String value();
}
