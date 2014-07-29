/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.joda.JodaModule;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// TODO: Auto-generated Javadoc
/**
 * The Class JsonUtil.
 */
public class JsonUtil
{
	
	/** The Constant logger. */
	private static final Logger logger = LoggerFactory.getLogger(JsonUtil.class);
	
	/** The Constant objectMapper. */
	private static final ObjectMapper objectMapper = new ObjectMapper().registerModule(new JodaModule());


	/**
	 * To json.
	 *
	 * @param o the o
	 * @return the string
	 */
	public static final String toJson(Object o)
	{
		try
		{
			return objectMapper.writeValueAsString(o);
		}
		catch (JsonProcessingException e)
		{
			logger.error("Json Processing was bad", logger);
		}

		return null;
	}
	
    /**
     * From json.
     *
     * @param ss the ss
     * @param cl the cl
     * @return the object
     */
    @SuppressWarnings("unchecked")
    public static final Object fromJson(String ss, Class cl) {
        if (ss == null || cl == null)
            return null;
	    try
        {
	        return objectMapper.readValue(ss,cl);
        }    catch (JsonProcessingException  e)
	        {
	            logger.error("Json Processing was bad", logger);
	        } catch (IOException e) {
	            logger.error("Json Processing was bad", logger);
        } 

	        return null;    
	}
}
