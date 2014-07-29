/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.guice;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.powerhive.meterservice.jobs.core.JobManager;

import org.quartz.impl.StdSchedulerFactory;
import org.reflections.Reflections;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiceJobManager.
 *
 * @author shermes641
 */
public class GuiceJobManager extends JobManager {

    /** The job factory. */
    protected GuiceJobFactory jobFactory;

    /**
     * Instantiates a new guice job manager.
     *
     * @param scanUrl the scan url
     * @param injector the injector
     */
    @Inject
    public GuiceJobManager(String scanUrl, Injector injector) {
        reflections = new Reflections(scanUrl);
        jobFactory = new GuiceJobFactory(injector);
    }

    /**
     * Instantiates a new guice job manager.
     *
     * @param injector the injector
     */
    public GuiceJobManager(Injector injector) {
        this("", injector);
    }

    /* (non-Javadoc)
     * @see com.powerhive.meterservice.jobs.core.JobManager#start()
     */
    @Override
    public void start() throws Exception {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.setJobFactory(jobFactory);
        scheduler.start();

        scheduleAllJobs();
    }

}
