/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.resources;

import com.powerhive.meterservice.db.UsageDAO;
import com.powerhive.meterservice.models.UsageData;
import com.powerhive.meterservice.views.UsageDataView;
import com.google.common.base.Optional;
import com.sun.jersey.api.NotFoundException;

import io.dropwizard.hibernate.UnitOfWork;
import io.dropwizard.jersey.params.LongParam;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

// TODO: Auto-generated Javadoc
/**
 * Get record from webui_usage table by id
 * Renders using a mustache based template.
 *
 * @author shermes641
 */
@Path("/usagedata/{usageId}")
@Produces(MediaType.APPLICATION_JSON)
public class UdResource {

    /** The usage dao. */
    private final UsageDAO usageDAO;

    /**
     * Instantiates a new ud resource.
     *
     * @param usageDAO the usage dao
     */
    public UdResource(UsageDAO usageDAO) {
        this.usageDAO = usageDAO;
    }

    /**
     * Gets the usage data.
     *
     * @param usageId the usage id
     * @return the usage data
     */
    @GET
    @UnitOfWork
    public UsageData getUsageData(@PathParam("usageId") LongParam usageId) {
        return findSafely(usageId.get());
    }

	/**
	 * Find safely.
	 *
	 * @param usageId the usage id
	 * @return the usage data
	 */
	private UsageData findSafely(long usageId) {
		final Optional<UsageData> ud = usageDAO.findById(usageId);
        if (!ud.isPresent()) {
            throw new NotFoundException("No such usage data.");
        }
		return ud.get();
	}

    
    /**
     * Gets the usage view mustache.
     *
     * @param usageId the usage id
     * @return the usage view mustache
     */
    @GET
    @Path("/view_mustache")
    @UnitOfWork
    @Produces(MediaType.TEXT_HTML)
    public UsageDataView getUsageViewMustache(@PathParam("usageId") LongParam usageId) {
    	return new UsageDataView(UsageDataView.Template.MUSTACHE, findSafely(usageId.get()));    
    }
}
