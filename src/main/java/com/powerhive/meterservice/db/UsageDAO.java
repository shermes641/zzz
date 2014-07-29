/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.db;

import com.powerhive.meterservice.models.UsageData;
import com.google.common.base.Optional;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * DAO to access the webui_usage table.
 *
 * @author shermes641
 */
public interface UsageDAO {
    
    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    public Optional<UsageData> findById(Long id);

      
    /**
     * Creates the.
     *
     * @param ud the ud
     * @return the usage data
     */
    public UsageData create(UsageData ud);

    /**
     * Find all.
     *
     * @return the list
     */
    public List<UsageData> findAll();
}
