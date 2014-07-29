/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.health;

import com.codahale.metrics.health.HealthCheck;
import com.powerhive.meterservice.core.Template;
import com.google.common.base.Optional;

// TODO: Auto-generated Javadoc
/**
 * Minimal template for health check resource.
 *
 * @author shermes641
 */
public class TemplateHealthCheck extends HealthCheck {
    
    /** The template. */
    private final Template template;

    /**
     * Instantiates a new template health check.
     *
     * @param template the template
     */
    public TemplateHealthCheck(Template template) {
        this.template = template;
    }

    /* (non-Javadoc)
     * @see com.codahale.metrics.health.HealthCheck#check()
     */
    @Override
    protected Result check() throws Exception {
        template.render(Optional.of("woo"));
        template.render(Optional.<String>absent());
        return Result.healthy();
    }
}
