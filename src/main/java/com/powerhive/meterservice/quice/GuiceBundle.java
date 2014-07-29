/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.quice;

import io.dropwizard.Configuration;
import io.dropwizard.ConfiguredBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Stage;
import com.google.inject.servlet.GuiceFilter;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.spi.container.servlet.ServletContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * The Class GuiceBundle.
 *
 * @param <T> the generic type
 */
public class GuiceBundle<T extends Configuration> implements ConfiguredBundle<T> {

    /** The logger. */
    final Logger logger = LoggerFactory.getLogger(GuiceBundle.class);

    /** The auto config. */
    private final AutoConfig autoConfig;
    
    /** The modules. */
    private final List<Module> modules;
    
    /** The injector. */
    private Injector injector;
    
    /** The dropwizard environment module. */
    @SuppressWarnings("rawtypes")
    private DropwizardEnvironmentModule dropwizardEnvironmentModule;
    
    /** The configuration class. */
    private Optional<Class<T>> configurationClass;
    
    /** The container. */
    private GuiceContainer container;
    
    /** The stage. */
    private Stage stage;


    /**
     * The Class Builder.
     *
     * @param <T> the generic type
     */
    public static class Builder<T extends Configuration> {
        
        /** The auto config. */
        private AutoConfig autoConfig;
        
        /** The modules. */
        private List<Module> modules = Lists.newArrayList();
        
        /** The configuration class. */
        private Optional<Class<T>> configurationClass = Optional.<Class<T>>absent();

        /**
         * Adds the module.
         *
         * @param module the module
         * @return the builder
         */
        public Builder<T> addModule(Module module) {
            Preconditions.checkNotNull(module);
            modules.add(module);
            return this;
        }

        /**
         * Sets the config class.
         *
         * @param clazz the clazz
         * @return the builder
         */
        public Builder<T> setConfigClass(Class<T> clazz) {
            configurationClass = Optional.of(clazz);
            return this;
        }

        /**
         * Enable auto config.
         *
         * @param basePackages the base packages
         * @return the builder
         */
        public Builder<T> enableAutoConfig(String... basePackages) {
            Preconditions.checkNotNull(basePackages.length > 0);
            Preconditions.checkArgument(autoConfig == null, "autoConfig already enabled!");
            autoConfig = new AutoConfig(basePackages);
            return this;
        }

        /**
         * Builds the.
         *
         * @return the guice bundle
         */
        public GuiceBundle<T> build() {
            return build(Stage.PRODUCTION);
        }

        /**
         * Builds the.
         *
         * @param s the s
         * @return the guice bundle
         */
        public GuiceBundle<T> build(Stage s) {
            return new GuiceBundle<T>(s, autoConfig, modules, configurationClass);
        }

    }

    /**
     * New builder.
     *
     * @param <T> the generic type
     * @return the builder
     */
    public static <T extends Configuration> Builder<T> newBuilder() {
        return new Builder<T>();
    }

    /**
     * Instantiates a new guice bundle.
     *
     * @param stage the stage
     * @param autoConfig the auto config
     * @param modules the modules
     * @param configurationClass the configuration class
     */
    private GuiceBundle(Stage stage, AutoConfig autoConfig, List<Module> modules, Optional<Class<T>> configurationClass) {
        Preconditions.checkNotNull(modules);
        Preconditions.checkArgument(!modules.isEmpty());
        Preconditions.checkNotNull(stage);
        this.modules = modules;
        this.autoConfig = autoConfig;
        this.configurationClass = configurationClass;
        this.stage = stage;
    }

    /* (non-Javadoc)
     * @see io.dropwizard.ConfiguredBundle#initialize(io.dropwizard.setup.Bootstrap)
     */
    @Override
    public void initialize(Bootstrap<?> bootstrap) {
        container = new GuiceContainer();
        JerseyContainerModule jerseyContainerModule = new JerseyContainerModule(container);
        if (configurationClass.isPresent()) {
            dropwizardEnvironmentModule = new DropwizardEnvironmentModule<T>(configurationClass.get());
        } else {
            dropwizardEnvironmentModule = new DropwizardEnvironmentModule<Configuration>(Configuration.class);
        }
        modules.add(jerseyContainerModule);
        modules.add(dropwizardEnvironmentModule);

        initInjector();

        if (autoConfig != null) {
            autoConfig.initialize(bootstrap, injector);
        }
    }

    /**
     * Inits the injector.
     */
    private void initInjector() {
        try {
	        injector = Guice.createInjector(this.stage, modules);
        } catch(Exception ie) {
		    logger.error("Exception occurred when creating Guice Injector - exiting", ie);
		    System.exit(1);
	    }
    }

    /* (non-Javadoc)
     * @see io.dropwizard.ConfiguredBundle#run(java.lang.Object, io.dropwizard.setup.Environment)
     */
    @Override
    public void run(final T configuration, final Environment environment) {
        container.setResourceConfig(environment.jersey().getResourceConfig());
        environment.jersey().replace(new Function<ResourceConfig, ServletContainer>() {
            @Nullable
            @Override
            public ServletContainer apply(ResourceConfig resourceConfig) {
                return container;
            }
        });
        environment.servlets().addFilter("Guice Filter", GuiceFilter.class)
                .addMappingForUrlPatterns(null, false, environment.getApplicationContext().getContextPath() + "*");
        setEnvironment(configuration, environment);

        if (autoConfig != null) {
            autoConfig.run(environment, injector);
        }
    }

    /**
     * Sets the environment.
     *
     * @param configuration the configuration
     * @param environment the environment
     */
    @SuppressWarnings("unchecked")
    private void setEnvironment(final T configuration, final Environment environment) {
        dropwizardEnvironmentModule.setEnvironmentData(configuration, environment);
    }

    /**
     * Gets the injector.
     *
     * @return the injector
     */
    public Injector getInjector() {
        return injector;
    }
}
