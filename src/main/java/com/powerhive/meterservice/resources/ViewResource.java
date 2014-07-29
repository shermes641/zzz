/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.resources;

import com.google.common.base.Charsets;
import io.dropwizard.views.View;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

// TODO: Auto-generated Javadoc
/**
 * Example to access custom templates
 * TODO remove if not needed.
 *
 * @author shermes641
 */
@Path("/views")
public class ViewResource {

    /**
     * Mustache ut f8.
     *
     * @return the view
     */
    @GET
    @Produces("text/html;charset=UTF-8")
    @Path("/utf8.mustache")
    public View mustacheUTF8() {
        return new View("/views/mustache/utf8.mustache", Charsets.UTF_8) {
        };
    }

    /**
     * Mustache is o88591.
     *
     * @return the view
     */
    @GET
    @Produces("text/html;charset=ISO-8859-1")
    @Path("/iso88591.mustache")
    public View mustacheISO88591() {
        return new View("/views/mustache/iso88591.mustache", Charsets.ISO_8859_1) {
        };
    }
}
