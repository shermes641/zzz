/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.quice;

import com.sun.jersey.guice.JerseyServletModule;
import com.sun.jersey.spi.container.WebApplication;

// TODO: Auto-generated Javadoc
/**
 * The Class JerseyContainerModule.
 */
public class JerseyContainerModule extends JerseyServletModule {
    
    /** The container. */
    private final GuiceContainer container;

    /**
     * Instantiates a new jersey container module.
     *
     * @param container the container
     */
    public JerseyContainerModule(final GuiceContainer container) {
        this.container = container;
    }

    /* (non-Javadoc)
     * @see com.google.inject.servlet.ServletModule#configureServlets()
     */
    @Override
    protected void configureServlets() {
        bind(GuiceContainer.class).toInstance(container);
    }

    /* (non-Javadoc)
     * @see com.sun.jersey.guice.JerseyServletModule#webApp(com.sun.jersey.guice.spi.container.servlet.GuiceContainer)
     */
    @Override
    public WebApplication webApp(com.sun.jersey.guice.spi.container.servlet.GuiceContainer guiceContainer) {
        return container.getWebApplication();
    }
}
