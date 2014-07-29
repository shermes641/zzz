/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.db;

import com.powerhive.meterservice.models.UsageData;
import com.google.common.base.Optional;
import com.google.inject.Inject;

import io.dropwizard.hibernate.AbstractDAO;

import org.hibernate.SessionFactory;

import java.util.List;

// TODO: Auto-generated Javadoc
/**
 * DAO to access the webui_usage table.
 *
 * @author shermes641
 */
public class UsageDaoImpl extends AbstractDAO<UsageData> implements UsageDAO {
    
    /**
     * Instantiates a new usage dao.
     *
     * @param factory the factory
     */
    @Inject
    public UsageDaoImpl(SessionFactory factory) {
        super(factory);
    }

    /**
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    @Override
    public Optional<UsageData> findById(Long id) {
        return Optional.fromNullable(get(id));
    }

      
    /**
     * Creates the.
     *
     * @param ud the ud
     * @return the usage data
     */
    @Override
    public UsageData create(UsageData ud) {
        return persist(ud);
    }

    /**
     * Find all.
     *
     * @return the list
     */
    @Override
    public List<UsageData> findAll() {
        return list(namedQuery("com.powerhive.meterservice.models.UsageData.findAll"));
    }
}
