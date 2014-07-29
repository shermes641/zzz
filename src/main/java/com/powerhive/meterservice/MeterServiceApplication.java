/*
 * Queen-meter-service
 */
package com.powerhive.meterservice;

import com.powerhive.meterservice.auth.MeterServiceAuthenticator;
import com.powerhive.meterservice.cli.RenderCommand;
import com.powerhive.meterservice.core.Template;
import com.powerhive.meterservice.db.UsageDaoImpl;
import com.powerhive.meterservice.health.RedisHealthCheck;
import com.powerhive.meterservice.health.TemplateHealthCheck;
import com.powerhive.meterservice.models.UsageData;
import com.powerhive.meterservice.quice.GuiceBundle;
import com.powerhive.meterservice.resources.HelloWorldResource;
import com.powerhive.meterservice.resources.ProtectedResource;
import com.powerhive.meterservice.resources.UdResource;
import com.powerhive.meterservice.resources.UsageDataResource;
import com.powerhive.meterservice.resources.ViewResource;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.auth.basic.BasicAuthProvider;
import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;
import io.dropwizard.migrations.MigrationsBundle;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

// TODO: Auto-generated Javadoc
/**
 * This is where we start
 * Docs http://dropwizard.readthedocs.org/en/latest/manual/
 * @author shermes641
 *
 */
public class MeterServiceApplication extends Application<MeterServiceConfiguration> {
    
    /**
     * The main method.
     *
     * @param args the arguments
     * @throws Exception the exception
     */
    public static void main(String[] args) throws Exception {
        new MeterServiceApplication().run(args);
    }
    
    /** The redis. */
    public static ApplicationModule redis = null;

    /* (non-Javadoc)
     * @see io.dropwizard.Application#getName()
     */
    @Override
    /**
     * If we are alive 
     */
    public String getName() {
        return "queen-meter-service";
    }

    /** The hibernate bundle. */
    public static HibernateBundle<MeterServiceConfiguration> hibernateBundle = new HibernateBundle<MeterServiceConfiguration>(UsageData.class) {
        @Override
        public DataSourceFactory getDataSourceFactory(MeterServiceConfiguration configuration) {
            return configuration.getDataSourceFactory();
        }
    };

    /* (non-Javadoc)
     * @see io.dropwizard.Application#initialize(io.dropwizard.setup.Bootstrap)
     */
    @Override
    /**
     * Add all our commands and bundles
     */
    public void initialize(Bootstrap<MeterServiceConfiguration> bootstrap) {
        bootstrap.addCommand(new RenderCommand());
        
        bootstrap.addBundle(new AssetsBundle());
        
        bootstrap.addBundle(new MigrationsBundle<MeterServiceConfiguration>() {
            @Override
            public DataSourceFactory getDataSourceFactory(MeterServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        });        
     
        bootstrap.addBundle(GuiceBundle.newBuilder()
            .addModule(new ApplicationModule())
            .enableAutoConfig(getClass().getPackage().getName())
            .build()
        );

        bootstrap.addBundle(hibernateBundle);
        
        bootstrap.addBundle(new ViewBundle());
        
        //bootstrap.addBundle(new JobsBundle("com.powerhive.meterservice.jobs"));
    }

    /* (non-Javadoc)
     * @see io.dropwizard.Application#run(io.dropwizard.Configuration, io.dropwizard.setup.Environment)
     */
    @Override
    /**
     * Register all our resources 
     */
    public void run(MeterServiceConfiguration configuration, Environment environment) throws ClassNotFoundException {
        
        final Template template = configuration.buildTemplate();


        final UsageDaoImpl usageDAO = new UsageDaoImpl(hibernateBundle.getSessionFactory());
        environment.jersey().register(new UsageDataResource(usageDAO));
        environment.jersey().register(new UdResource(usageDAO));
               
        environment.healthChecks().register("template", new TemplateHealthCheck(template));
        
        redis = new ApplicationModule();
        environment.healthChecks().register("REDIS", new RedisHealthCheck(redis.provideJedisPool(configuration)));
        
        environment.jersey().register(new BasicAuthProvider<>(new MeterServiceAuthenticator(), "SUPER SECRET STUFF"));
        environment.jersey().register(new HelloWorldResource(template));
        environment.jersey().register(new ViewResource());
        environment.jersey().register(new ProtectedResource());
    }
}

