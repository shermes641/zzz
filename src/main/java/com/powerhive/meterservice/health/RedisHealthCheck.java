/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.health;

import com.codahale.metrics.health.HealthCheck;
import com.google.inject.Inject;

import redis.clients.jedis.JedisPool;

// TODO: Auto-generated Javadoc
/**
 * The Class RedisHealthCheck.
 *
 * @author shermes641
 */
public class RedisHealthCheck extends HealthCheck
{
	
	/** The Constant ECHO. */
	private static final String ECHO = "checking";
	
	/** The jedis pool. */
	private final JedisPool jedisPool;

	/**
	 * Instantiates a new redis health check.
	 *
	 * @param jedisPool the jedis pool
	 */
	@Inject
    public RedisHealthCheck( JedisPool jedisPool )
    {
        super();
        this.jedisPool = jedisPool;
    }

	/**
	 * Pass / Fail result.
	 *
	 * @return the result
	 * @throws Exception the exception
	 */
	@Override
	protected Result check() throws Exception
	{
		if ( ECHO.equalsIgnoreCase(jedisPool.getResource().echo(ECHO)) )
		{
			return HealthCheck.Result.healthy();
		}
		else
		{
			return HealthCheck.Result.unhealthy("Something was wrong");
		}
	}
}
