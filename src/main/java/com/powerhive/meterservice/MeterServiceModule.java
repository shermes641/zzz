/*
 * 
 */
package com.powerhive.meterservice;

import io.dropwizard.db.DataSourceFactory;
import io.dropwizard.hibernate.HibernateBundle;

import org.hibernate.SessionFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.powerhive.meterservice.models.UsageData;

// TODO: Auto-generated Javadoc
/**
 * The Class MeterServiceModule.
 */
public class MeterServiceModule extends AbstractModule {

    /**
     * Provide session factory.
     *
     * @param configuration the configuration
     * @return the session factory
     */
    @Singleton
    @Provides
    public SessionFactory provideSessionFactory(MeterServiceConfiguration configuration) {
        HibernateBundle<MeterServiceConfiguration> hibernate = new HibernateBundle<MeterServiceConfiguration>(UsageData.class) {
            @Override
            public DataSourceFactory getDataSourceFactory(MeterServiceConfiguration configuration) {
                return configuration.getDataSourceFactory();
            }
        };
        return hibernate.getSessionFactory();
    }

    /* (non-Javadoc)
     * @see com.google.inject.AbstractModule#configure()
     */
    @Override
    protected void configure() {
        // TODO Auto-generated method stub
        
    }
}