/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.core;

import com.codahale.metrics.SharedMetricRegistries;

import io.dropwizard.Bundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

// TODO: Auto-generated Javadoc
/**
 * The Class JobsBundle.
 *
 * @author shermes641
 */
public class JobsBundle implements Bundle {

    /** The scan url. */
    protected String scanURL = null;

    /* (non-Javadoc)
     * @see io.dropwizard.Bundle#initialize(io.dropwizard.setup.Bootstrap)
     */
    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        // add shared metrics registry to be used by Jobs, since defaultRegistry
        // has been removed
        SharedMetricRegistries.add(Job.DROPWIZARD_JOBS_KEY,
                bootstrap.getMetricRegistry());
    }

    /**
     * Instantiates a new jobs bundle.
     */
    public JobsBundle() {
    }

    /**
     * Instantiates a new jobs bundle.
     *
     * @param scanURL the scan url
     */
    public JobsBundle(String scanURL) {
        this.scanURL = scanURL;
    }

    /* (non-Javadoc)
     * @see io.dropwizard.Bundle#run(io.dropwizard.setup.Environment)
     */
    @Override
    public void run(Environment environment) {
        JobManager jobManager = new JobManager(scanURL);
        environment.lifecycle().manage(jobManager);
    }

}
