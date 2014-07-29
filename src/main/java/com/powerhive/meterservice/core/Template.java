/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.core;

import com.google.common.base.Optional;

import static java.lang.String.format;

// TODO: Auto-generated Javadoc
/**
 * Handle all out templates
 * Note there is only a minimal UI.
 *
 * @author shermes641
 */
public class Template {
    
    /** The content. */
    private final String content;
    
    /** The default name. */
    private final String defaultName;

    /**
     * Instantiates a new template.
     *
     * @param content the content
     * @param defaultName the default name
     */
    public Template(String content, String defaultName) {
        this.content = content;
        this.defaultName = defaultName;
    }
    
    /**
     * Render.
     *
     * @param name the name
     * @return the string
     */
    public String render(Optional<String> name) {
        return format(content, name.or(defaultName));
    }
}
