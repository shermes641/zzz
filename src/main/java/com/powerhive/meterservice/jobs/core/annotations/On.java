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
 * The Interface On.
 *
 * @author shermes641
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface On {
    
    /**
     * Value.
     *
     * @return the string
     */
    String value();
}
