/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.powerhive.meterservice.jobs.core.JobsBundle;

import io.dropwizard.setup.Environment;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiceJobsBundle.
 *
 * @author shermes641
 */
public class GuiceJobsBundle extends JobsBundle {
    
    /** The injector. */
    Injector injector;

    /**
     * Instantiates a new guice jobs bundle.
     *
     * @param injector the injector
     */
    public GuiceJobsBundle(Injector injector) {
        this("", injector);
    }

    /**
     * Instantiates a new guice jobs bundle.
     *
     * @param scanUrl the scan url
     * @param injector the injector
     */
    @Inject
    public GuiceJobsBundle(String scanUrl, Injector injector) {
        super(scanUrl);
        this.injector = injector;
    }

    /* (non-Javadoc)
     * @see com.powerhive.meterservice.jobs.core.JobsBundle#run(io.dropwizard.setup.Environment)
     */
    @Override
    public void run(Environment environment) {
        environment.lifecycle().manage(new GuiceJobManager(scanURL, injector));
    }

}
