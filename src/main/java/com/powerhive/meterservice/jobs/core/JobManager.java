/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.jobs.core;

import io.dropwizard.lifecycle.Managed;

import java.lang.annotation.Annotation;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.JobBuilder;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Sets;
import com.powerhive.meterservice.jobs.core.annotations.*;
import com.powerhive.meterservice.jobs.core.parser.TimeParserUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class JobManager.
 *
 * @author shermes641
 */
public class JobManager implements Managed {

    /** The Constant log. */
    private static final Logger log = LoggerFactory.getLogger(JobManager.class);
    
    /** The reflections. */
    protected Reflections reflections = null;
    
    /** The scheduler. */
    protected Scheduler scheduler;

    /**
     * Instantiates a new job manager.
     */
    public JobManager() {
        this("");
    }

    /**
     * Instantiates a new job manager.
     *
     * @param scanUrl the scan url
     */
    public JobManager(String scanUrl) {
        reflections = new Reflections(scanUrl);
    }

    /* (non-Javadoc)
     * @see io.dropwizard.lifecycle.Managed#start()
     */
    @Override
    public void start() throws Exception {
        scheduler = StdSchedulerFactory.getDefaultScheduler();
        scheduler.start();
        scheduleAllJobs();
    }

    /* (non-Javadoc)
     * @see io.dropwizard.lifecycle.Managed#stop()
     */
    @Override
    public void stop() throws Exception {
        scheduleAllJobsOnApplicationStop();

        // this is enough to put the job into the queue, otherwise the jobs wont
        // be executed
        // anyone got a better solution?
        Thread.sleep(100);

        scheduler.shutdown(true);
    }

    /**
     * Schedule all jobs.
     *
     * @throws SchedulerException the scheduler exception
     */
    protected void scheduleAllJobs() throws SchedulerException {
        scheduleAllJobsOnApplicationStart();
        scheduleAllJobsWithEveryAnnotation();
        scheduleAllJobsWithOnAnnotation();
    }

    /**
     * Schedule all jobs on application stop.
     *
     * @throws SchedulerException the scheduler exception
     */
    protected void scheduleAllJobsOnApplicationStop() throws SchedulerException {
        List<Class<? extends Job>> stopJobClasses = getJobClasses(OnApplicationStop.class);
        for (Class<? extends Job> clazz : stopJobClasses) {
            JobBuilder jobDetail = JobBuilder.newJob(clazz);
            scheduler.scheduleJob(jobDetail.build(), executeNowTrigger());
        }
    }

    /**
     * Gets the job classes.
     *
     * @param annotation the annotation
     * @return the job classes
     */
    protected List<Class<? extends Job>> getJobClasses(Class<? extends Annotation> annotation) {
        Set<Class<? extends Job>> jobs = reflections.getSubTypesOf(Job.class);
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(annotation);

        return Sets.intersection(new HashSet<Class<? extends Job>>(jobs), annotatedClasses).immutableCopy().asList();
    }

    /**
     * Schedule all jobs with on annotation.
     *
     * @throws SchedulerException the scheduler exception
     */
    protected void scheduleAllJobsWithOnAnnotation() throws SchedulerException {
        List<Class<? extends Job>> onJobClasses = getJobClasses(On.class);
        log.info("Jobs with @On annotation: " + onJobClasses);

        for (Class<? extends org.quartz.Job> clazz : onJobClasses) {
            On annotation = clazz.getAnnotation(On.class);

            CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(annotation.value());
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();
            JobBuilder jobBuilder = JobBuilder.newJob(clazz);
            scheduler.scheduleJob(jobBuilder.build(), trigger);
        }
    }

    /**
     * Schedule all jobs with every annotation.
     *
     * @throws SchedulerException the scheduler exception
     */
    protected void scheduleAllJobsWithEveryAnnotation() throws SchedulerException {
        List<Class<? extends Job>> everyJobClasses = getJobClasses(Every.class);
        log.info("Jobs with @Every annotation: " + everyJobClasses);

        for (Class<? extends org.quartz.Job> clazz : everyJobClasses) {
            Every annotation = clazz.getAnnotation(Every.class);
            int secondDelay = TimeParserUtil.parseDuration(annotation.value());
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule()
                    .withIntervalInSeconds(secondDelay).repeatForever();
            Trigger trigger = TriggerBuilder.newTrigger().withSchedule(scheduleBuilder).build();

            JobBuilder jobBuilder = JobBuilder.newJob(clazz);
            scheduler.scheduleJob(jobBuilder.build(), trigger);
        }
    }

    /**
     * Schedule all jobs on application start.
     *
     * @throws SchedulerException the scheduler exception
     */
    protected void scheduleAllJobsOnApplicationStart() throws SchedulerException {
        List<Class<? extends Job>> startJobClasses = getJobClasses(OnApplicationStart.class);
        log.info("Jobs to run on application start: " + startJobClasses);
        for (Class<? extends org.quartz.Job> clazz : startJobClasses) {
            JobBuilder jobBuilder = JobBuilder.newJob(clazz);
            scheduler.scheduleJob(jobBuilder.build(), executeNowTrigger());
        }
    }

    /**
     * Execute now trigger.
     *
     * @return the trigger
     */
    protected Trigger executeNowTrigger() {
        return TriggerBuilder.newTrigger().startNow().build();
    }
}
