/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.core;

import static com.codahale.metrics.MetricRegistry.name;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.codahale.metrics.MetricRegistry;
import com.codahale.metrics.SharedMetricRegistries;
import com.codahale.metrics.Timer;
import com.codahale.metrics.Timer.Context;

// TODO: Auto-generated Javadoc
/**
 * The Class Job.
 *
 * @author shermes641
 */
public abstract class Job implements org.quartz.Job {
    
    /** The Constant DROPWIZARD_JOBS_KEY. */
    public static final String DROPWIZARD_JOBS_KEY = "dropwizard-jobs";

    /** The timer. */
    private final Timer timer;

    /**
     * Instantiates a new job.
     */
    public Job() {
        // get the metrics registry which was shared during bundle instantiation
        this(SharedMetricRegistries.getOrCreate(DROPWIZARD_JOBS_KEY));
    }

    /**
     * Instantiates a new job.
     *
     * @param metricRegistry the metric registry
     */
    public Job(MetricRegistry metricRegistry) {
        timer = metricRegistry.timer(name(getClass(), getClass().getName()));
    }

    /* (non-Javadoc)
     * @see org.quartz.Job#execute(org.quartz.JobExecutionContext)
     */
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        Context timerContext = timer.time();
        try {
            doJob();
        } finally {
            timerContext.stop();
        }
    }

    /**
     * Do job.
     */
    public abstract void doJob();
}
