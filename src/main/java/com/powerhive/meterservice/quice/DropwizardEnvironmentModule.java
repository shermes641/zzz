/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.quice;

import io.dropwizard.Configuration;
import io.dropwizard.setup.Environment;
import com.google.inject.AbstractModule;
import com.google.inject.Provider;
import com.google.inject.Provides;
import com.google.inject.ProvisionException;

// TODO: Auto-generated Javadoc
/**
 * The Class DropwizardEnvironmentModule.
 *
 * @param <T> the generic type
 */
public class DropwizardEnvironmentModule<T extends Configuration> extends AbstractModule {
	
	/** The Constant ILLEGAL_DROPWIZARD_MODULE_STATE. */
	private static final String ILLEGAL_DROPWIZARD_MODULE_STATE = "The dropwizard environment has not yet been set. This is likely caused by trying to access the dropwizard environment during the bootstrap phase.";
	
	/** The configuration. */
	private T configuration;
	
	/** The environment. */
	private Environment environment;
	
	/** The configuration class. */
	private Class<? super T> configurationClass;

	/**
	 * Instantiates a new dropwizard environment module.
	 *
	 * @param configurationClass the configuration class
	 */
	public DropwizardEnvironmentModule(Class<T> configurationClass) {
		this.configurationClass = configurationClass;
	}

	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
	protected void configure() {
		Provider<T> provider = new CustomConfigurationProvider();
		bind(configurationClass).toProvider(provider);
		if (configurationClass != Configuration.class) {
			bind(Configuration.class).toProvider(provider);
		}
	}

	/**
	 * Sets the environment data.
	 *
	 * @param configuration the configuration
	 * @param environment the environment
	 */
	public void setEnvironmentData(T configuration, Environment environment) {
		this.configuration = configuration;
		this.environment = environment;
	}

	/**
	 * Provides environment.
	 *
	 * @return the environment
	 */
	@Provides
	public Environment providesEnvironment() {
		if (environment == null) {
			throw new ProvisionException(ILLEGAL_DROPWIZARD_MODULE_STATE);
		}
		return environment;
	}

	/**
	 * The Class CustomConfigurationProvider.
	 */
	private class CustomConfigurationProvider implements Provider<T> {
		
		/* (non-Javadoc)
		 * @see com.google.inject.Provider#get()
		 */
		@Override
		public T get() {
			if (configuration == null) {
				throw new ProvisionException(ILLEGAL_DROPWIZARD_MODULE_STATE);
			}
			return configuration;
		}
	}
}
