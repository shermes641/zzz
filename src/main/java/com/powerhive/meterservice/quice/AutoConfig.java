/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.quice;

import io.dropwizard.Bundle;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import com.google.common.base.Preconditions;
import com.google.inject.Injector;
import com.sun.jersey.spi.inject.InjectableProvider;
import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.Path;
import javax.ws.rs.ext.Provider;
import java.util.Set;

// TODO: Auto-generated Javadoc
/**
 * The Class AutoConfig.
 */
public class AutoConfig {

	/** The logger. */
	final Logger logger = LoggerFactory.getLogger(AutoConfig.class);

	/** The reflections. */
	private Reflections reflections;

	/**
	 * Instantiates a new auto config.
	 *
	 * @param basePackages the base packages
	 */
	public AutoConfig(String... basePackages) {
		Preconditions.checkArgument(basePackages.length > 0);
		
		ConfigurationBuilder cfgBldr = new ConfigurationBuilder();
		FilterBuilder filterBuilder = new FilterBuilder();
		for (String basePkg : basePackages) {
			cfgBldr.addUrls(ClasspathHelper.forPackage(basePkg));
			filterBuilder.include(FilterBuilder.prefix(basePkg));
		}

		cfgBldr.filterInputsBy(filterBuilder).setScanners(
				new SubTypesScanner(), new TypeAnnotationsScanner());
		this.reflections = new Reflections(cfgBldr);
	}

	/**
	 * Run.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	public void run(Environment environment, Injector injector) {
		addHealthChecks(environment, injector);
		addProviders(environment, injector);
		addInjectableProviders(environment, injector);
		addResources(environment, injector);
		addTasks(environment, injector);
		addManaged(environment, injector);
	}

	/**
	 * Initialize.
	 *
	 * @param bootstrap the bootstrap
	 * @param injector the injector
	 */
	public void initialize(Bootstrap<?> bootstrap, Injector injector) {
		addBundles(bootstrap, injector);
	}

	/**
	 * Adds the managed.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	private void addManaged(Environment environment, Injector injector) {
		Set<Class<? extends Managed>> managedClasses = reflections
				.getSubTypesOf(Managed.class);
		for (Class<? extends Managed> managed : managedClasses) {
			environment.lifecycle().manage(injector.getInstance(managed));
			logger.info("Added managed: {}", managed);
		}
	}

	/**
	 * Adds the tasks.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	private void addTasks(Environment environment, Injector injector) {
		Set<Class<? extends Task>> taskClasses = reflections
				.getSubTypesOf(Task.class);
		for (Class<? extends Task> task : taskClasses) {
			environment.admin().addTask(injector.getInstance(task));
			logger.info("Added task: {}", task);
		}
	}

	/**
	 * Adds the health checks.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	private void addHealthChecks(Environment environment, Injector injector) {
		Set<Class<? extends InjectableHealthCheck>> healthCheckClasses = reflections
				.getSubTypesOf(InjectableHealthCheck.class);
		for (Class<? extends InjectableHealthCheck> healthCheck : healthCheckClasses) {
            InjectableHealthCheck instance = injector.getInstance(healthCheck);
            environment.healthChecks().register(instance.getName(), instance);
			logger.info("Added injectableHealthCheck: {}", healthCheck);
		}
	}

	/**
	 * Adds the injectable providers.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	@SuppressWarnings("rawtypes")
	private void addInjectableProviders(Environment environment,
			Injector injector) {
		Set<Class<? extends InjectableProvider>> injectableProviders = reflections
				.getSubTypesOf(InjectableProvider.class);
		for (Class<? extends InjectableProvider> injectableProvider : injectableProviders) {
			environment.jersey().register(injectableProvider);
			logger.info("Added injectableProvider: {}", injectableProvider);
		}
	}

	/**
	 * Adds the providers.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	private void addProviders(Environment environment, Injector injector) {
		Set<Class<?>> providerClasses = reflections
				.getTypesAnnotatedWith(Provider.class);
		for (Class<?> provider : providerClasses) {
			environment.jersey().register(provider);
			logger.info("Added provider class: {}", provider);
		}
	}

	/**
	 * Adds the resources.
	 *
	 * @param environment the environment
	 * @param injector the injector
	 */
	private void addResources(Environment environment, Injector injector) {
		Set<Class<?>> resourceClasses = reflections
				.getTypesAnnotatedWith(Path.class);
		for (Class<?> resource : resourceClasses) {
			environment.jersey().register(resource);
			logger.info("Added resource class: {}", resource);
		}
	}

	/**
	 * Adds the bundles.
	 *
	 * @param bootstrap the bootstrap
	 * @param injector the injector
	 */
	private void addBundles(Bootstrap<?> bootstrap, Injector injector) {
		Set<Class<? extends Bundle>> bundleClasses = reflections
				.getSubTypesOf(Bundle.class);
		for (Class<? extends Bundle> bundle : bundleClasses) {
			bootstrap.addBundle(injector.getInstance(bundle));
			logger.info("Added bundle class {} during bootstrap", bundle);
		}
	}
}
