/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.models;

import java.math.BigDecimal;

import javax.persistence.*;

import org.joda.time.DateTime;


// TODO: Auto-generated Javadoc
/**
 * The Class UsageData.
 */
@Entity
@Table(name = "webui_usage")
@NamedQueries({
    @NamedQuery(
        name = "com.powerhive.meterservice.models.UsageData.findAll",
        query = "SELECT ud FROM UsageData ud"
    ),
    @NamedQuery(
        name = "com.powerhive.meterservice.models.UsageData.findById",
        query = "SELECT ud FROM UsageData ud WHERE ud.id = :id"
    )
})

/**
 * The table where usage data from the queens end up
 * @author shermes641
 * 
 *
 */
public class UsageData {
    
    /** The id. */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    
    /** The circuit_id. */
    @Column(name = "circuit_id", nullable = false)
    public long circuit_id;     
    
    /** The queen_id. */
    @Column(name = "queen_id", nullable = false)
    public long queen_id;                 
    
    /** The day_wh_lifetime. */
    @Column(name = "\"dayWhLifetime\"", nullable = false)
    public double day_wh_lifetime;            // double precision NOT NULL, -- Total watt hours used  as of last sync...
    
    /** The night_wh_lifetime. */
    @Column(name = "\"nightWhLifetime\"", nullable = false)
    public double night_wh_lifetime;              // double precision NOT NULL, -- Total Night watt hours used  as of last sync...
    
    /** The day_wh_today. */
    @Column(name = "\"dayWhToday\"", nullable = false)
    public double day_wh_today;        
    
    /** The night_wh_today. */
    @Column(name = "\"nightWhToday\"", nullable = false)
    public double night_wh_today; 
        
    /** The total_wh_today. */
    @Column(name = "\"totalWhToday\"", nullable = false)
    public double total_wh_today;
    
    /** The credit_balance. */
    @Column(name = "\"creditBalance\"", nullable = true)
    public BigDecimal credit_balance;       

    /** The data_date_time. */
    @Column(name = "\"dataDateTime\"", nullable = false)
    public DateTime data_date_time;         //timestamp without time zone NOT NULL, -- Timstamp of usage data
    
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public long getId() {
        return id;
    }

    /**
     * Sets the id.
     *
     * @param id the new id
     */
    public void setId(long id) {
        this.id = id;
    }
}
