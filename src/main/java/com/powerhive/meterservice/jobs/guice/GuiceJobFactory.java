/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.quartz.spi.TriggerFiredBundle;

// TODO: Auto-generated Javadoc
/**
 * A factory for creating GuiceJob objects.
 *
 * @author shermes641
 */
public class GuiceJobFactory implements JobFactory {

    /** The injector. */
    private Injector injector;

    /**
     * Instantiates a new guice job factory.
     *
     * @param injector the injector
     */
    @Inject
    public GuiceJobFactory(Injector injector) {
        this.injector = injector;
    }

    /* (non-Javadoc)
     * @see org.quartz.spi.JobFactory#newJob(org.quartz.spi.TriggerFiredBundle, org.quartz.Scheduler)
     */
    @Override
    public Job newJob(TriggerFiredBundle triggerFiredBundle, Scheduler scheduler) throws SchedulerException {
        JobDetail jobDetail = triggerFiredBundle.getJobDetail();
        Class<? extends Job> jobClass = jobDetail.getJobClass();
        return injector.getInstance(jobClass);
    }
}
