/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.Length;

// TODO: Auto-generated Javadoc
/**
 * Hello World needs this
 * TODO rip it out if not wanted.
 *
 * @author shermes641
 */
public class Saying {
    
    /** The id. */
    private long id;

    /** The content. */
    @Length(max = 3)
    private String content;

    /**
     * Instantiates a new saying.
     */
    public Saying() {
        // Jackson deserialization
    }

    /**
     * Instantiates a new saying.
     *
     * @param id the id
     * @param content the content
     */
    public Saying(long id, String content) {
        this.id = id;
        this.content = content;
    }

    /**
     * Gets the id.
     *
     * @return the id
     */
    @JsonProperty
    public long getId() {
        return id;
    }

    /**
     * Gets the content.
     *
     * @return the content
     */
    @JsonProperty
    public String getContent() {
        return content;
    }
}
