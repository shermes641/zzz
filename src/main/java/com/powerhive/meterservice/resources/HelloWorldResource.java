/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.powerhive.meterservice.core.Saying;
import com.powerhive.meterservice.core.Template;
import com.google.common.base.Optional;

import io.dropwizard.jersey.caching.CacheControl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

// TODO: Auto-generated Javadoc
/**
 * The Class HelloWorldResource.
 */
@Path("/hello-world")
@Produces(MediaType.APPLICATION_JSON)
/**
 * TODO remove if we don't want a hello-world endpoint
 * @author shermes641
 *
 */
public class HelloWorldResource {
    
    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloWorldResource.class);

    /** The template. */
    private final Template template;
    
    /** The counter. */
    private final AtomicLong counter;

    /**
     * Instantiates a new hello world resource.
     *
     * @param template the template
     */
    public HelloWorldResource(Template template) {
        this.template = template;
        this.counter = new AtomicLong();
    }

    /**
     * Say hello.
     *
     * @param name the name
     * @return the saying
     */
    @GET
    @Timed(name = "get-requests")
    @CacheControl(maxAge = 1, maxAgeUnit = TimeUnit.DAYS)
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        return new Saying(counter.incrementAndGet(), template.render(name));
    }

    /**
     * Receive hello.
     *
     * @param saying the saying
     */
    @POST
    public void receiveHello(@Valid Saying saying) {
        LOGGER.info("Received a saying: {}", saying);
    }
}
