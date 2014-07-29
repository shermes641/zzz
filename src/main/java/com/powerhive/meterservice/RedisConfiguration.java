/*
 * Queen-meter-service
 */
package com.powerhive.meterservice;


import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

// TODO: Auto-generated Javadoc
/**
 * The Class RedisConfiguration.
 */
public class RedisConfiguration
{
	
	/** The hostname. */
	@NotEmpty
    @JsonProperty
	private String hostname;

	/** The port. */
	@Min(1)
    @Max(65535)
    @JsonProperty
	private Integer port;

	/**
	 * Gets the hostname.
	 *
	 * @return the hostname
	 */
	public String getHostname()
	{
		return hostname;
	}

	/**
	 * Sets the hostname.
	 *
	 * @param hostname the new hostname
	 */
	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	/**
	 * Gets the port.
	 *
	 * @return the port
	 */
	public Integer getPort()
	{
		return port;
	}

	/**
	 * Sets the port.
	 *
	 * @param port the new port
	 */
	public void setPort(Integer port)
	{
		this.port = port;
	}

}
