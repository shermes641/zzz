/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.quice;

import java.util.Map;

import javax.servlet.ServletException;
import javax.ws.rs.core.Application;

import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Scope;
import com.google.inject.Singleton;
import com.google.inject.servlet.ServletScopes;
import com.sun.jersey.api.core.DefaultResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ComponentScope;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import com.sun.jersey.spi.container.WebApplication;
import com.sun.jersey.spi.container.servlet.ServletContainer;
import com.sun.jersey.spi.container.servlet.WebConfig;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiceContainer.
 */
@Singleton
public class GuiceContainer extends ServletContainer {
    
    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1931878850157940335L;

    /** The injector. */
    @Inject
    private Injector injector;
    
    /** The webapp. */
    private WebApplication webapp;
    
    /** The resource config. */
    private ResourceConfig resourceConfig = new DefaultResourceConfig();

    /**
     * A factory for creating ServletGuiceComponentProvider objects.
     */
    public class ServletGuiceComponentProviderFactory extends GuiceComponentProviderFactory {
        
        /**
         * Instantiates a new servlet guice component provider factory.
         *
         * @param config the config
         * @param injector the injector
         */
        public ServletGuiceComponentProviderFactory(ResourceConfig config, Injector injector) {
            super(config, injector);
        }
        
        /* (non-Javadoc)
         * @see com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory#createScopeMap()
         */
        @Override
        public Map<Scope, ComponentScope> createScopeMap() {
            Map<Scope, ComponentScope> m = super.createScopeMap();

            m.put(ServletScopes.REQUEST, ComponentScope.PerRequest);
            return m;
        }
    }

    /**
     * Instantiates a new guice container.
     */
    public GuiceContainer() {
    }
		
    /**
     * Instantiates a new guice container.
     *
     * @param app the app
     */
    public GuiceContainer(Application app) {
      super(app);
    }
		
    /**
     * Instantiates a new guice container.
     *
     * @param app the app
     */
    public GuiceContainer(Class<? extends Application> app) {
      super(app);
    }
    
    /**
     * Sets the resource config.
     *
     * @param resourceConfig the new resource config
     */
    public void setResourceConfig(ResourceConfig resourceConfig) {
	    this.resourceConfig = resourceConfig;
    }

    /* (non-Javadoc)
     * @see com.sun.jersey.spi.container.servlet.ServletContainer#getDefaultResourceConfig(java.util.Map, com.sun.jersey.spi.container.servlet.WebConfig)
     */
    @Override
    protected ResourceConfig getDefaultResourceConfig(Map<String, Object> props, WebConfig webConfig) throws ServletException {
    	return resourceConfig;
    }

    /* (non-Javadoc)
     * @see com.sun.jersey.spi.container.servlet.ServletContainer#initiate(com.sun.jersey.api.core.ResourceConfig, com.sun.jersey.spi.container.WebApplication)
     */
    @Override
    protected void initiate(ResourceConfig config, WebApplication webapp) {
        this.webapp = webapp;
        webapp.initiate(config, new ServletGuiceComponentProviderFactory(config, injector));
    }

    /**
     * Gets the web application.
     *
     * @return the web application
     */
    public WebApplication getWebApplication() {
        return webapp;
    }
}