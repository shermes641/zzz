/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.resources;

import com.codahale.metrics.annotation.Timed;
import com.powerhive.meterservice.db.UsageDAO;
import com.powerhive.meterservice.models.UsageData;

import io.dropwizard.hibernate.UnitOfWork;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * Endpoint to insert records into webui_usage table, or select all records.
 *  TODO add limit param for findAll
 *  TODO make protected ?
 * 
 * @author shermes641
 *
 */
@Path("/usagedata")
@Produces(MediaType.APPLICATION_JSON)
public class UsageDataResource {

    /** The dao. */
    private final UsageDAO dao;

    /**
     * Instantiates a new usage data resource.
     *
     * @param dao the dao
     */
    public UsageDataResource(UsageDAO dao) {
        this.dao = dao;
    }

    /**
     * Creates the usage data.
     *
     * @param ud the ud
     * @return the usage data
     */
    @POST
    @Timed
    @UnitOfWork
    public UsageData createUsageData(UsageData[] ud) {
        for (int i = 0; i < ud.length; i++)
            dao.create(ud[i]);
        return ud[0];
    }

    /**
     * List all usage.
     *
     * @return the list
     */
    @GET
    @UnitOfWork
    public List<UsageData> listAllUsage() {
        return dao.findAll();
    }

}
