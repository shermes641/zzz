/*
 * Queen-meter-service
 */
package com.powerhive.meterservice;

import io.dropwizard.Configuration;
import io.dropwizard.db.DataSourceFactory;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.powerhive.meterservice.core.Template;

// TODO: Auto-generated Javadoc
/**
 * Read in configuration settings.
 *
 * @author shermes641
 */
public class MeterServiceConfiguration extends Configuration {
    
    /** The redis. */
    @Valid
    @NotNull
    @JsonProperty
    private RedisConfiguration redis = new RedisConfiguration();

    /**
     * Gets the redis.
     *
     * @return the redis
     */
    public RedisConfiguration getRedis()
    {
        return redis;
    }
    
    /** The template. */
    @NotEmpty
    private String template;

    /** The default name. */
    @NotEmpty
    private String defaultName = "Stranger";

    /** The database. */
    @Valid
    @NotNull
    private DataSourceFactory database = new DataSourceFactory();
    
    /**
     * Gets the template.
     *
     * @return the template
     */
    @JsonProperty
    public String getTemplate() {
        return template;
    }

    /**
     * Sets the template.
     *
     * @param template the new template
     */
    @JsonProperty
    public void setTemplate(String template) {
        this.template = template;
    }

    /**
     * Gets the default name.
     *
     * @return the default name
     */
    @JsonProperty
    public String getDefaultName() {
        return defaultName;
    }

    /**
     * Sets the default name.
     *
     * @param defaultName the new default name
     */
    @JsonProperty
    public void setDefaultName(String defaultName) {
        this.defaultName = defaultName;
    }

    /**
     * Builds the template.
     *
     * @return the template
     */
    public Template buildTemplate() {
        return new Template(template, defaultName);
    }

    /**
     * Gets the data source factory.
     *
     * @return the data source factory
     */
    @JsonProperty("database")
    public DataSourceFactory getDataSourceFactory() {
        return database;
    }

    /**
     * Sets the data source factory.
     *
     * @param dataSourceFactory the new data source factory
     */
    @JsonProperty("database")
    public void setDataSourceFactory(DataSourceFactory dataSourceFactory) {
        this.database = dataSourceFactory;
    }
}
