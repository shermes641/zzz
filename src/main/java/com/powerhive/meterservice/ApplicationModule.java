/*
 * Queen-meter-service
 */
package com.powerhive.meterservice;

import java.net.URI;
import java.net.URISyntaxException;

import io.dropwizard.Configuration;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;

import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.Protocol;

// TODO: Auto-generated Javadoc
/**
 * The Class ApplicationModule.
 */
public class ApplicationModule extends AbstractModule
{
	
	/* (non-Javadoc)
	 * @see com.google.inject.AbstractModule#configure()
	 */
	@Override
    protected void configure()
    {

    }

    /**
     * Configuration.
     *
     * @param configuration the configuration
     * @return the meter service configuration
     */
    @Provides
    public MeterServiceConfiguration configuration(Configuration configuration)
    {
        return (MeterServiceConfiguration) configuration;
    }

	/**
	 * Provide jedis pool.
	 *
	 * @param applicationConfiguration the application configuration
	 * @return the jedis pool
	 */
	@Provides
	public JedisPool provideJedisPool(MeterServiceConfiguration applicationConfiguration)
	{
		RedisConfiguration redisConfig = applicationConfiguration.getRedis();
		if (redisConfig.getHostname().equalsIgnoreCase("HEROKU")) {
		    try {
		        URI redisUri = new URI(System.getenv("REDISGREEN_URL"));
		        return new JedisPool(new JedisPoolConfig(),
		                redisUri.getHost(),
		                redisUri.getPort(),
		                Protocol.DEFAULT_TIMEOUT,
		                redisUri.getUserInfo().split(":",2)[1]);
		    } catch (URISyntaxException e) {
		        // URI couldn't be parsed.
		    }
		} else
		    return new JedisPool(
				new JedisPoolConfig(),
				redisConfig.getHostname(),
				redisConfig.getPort() );
		return null;
	}
}
