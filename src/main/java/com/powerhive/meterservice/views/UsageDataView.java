/*
 * Queen-meter-service
 */
package com.powerhive.meterservice.views;

import com.powerhive.meterservice.models.UsageData;

import io.dropwizard.views.View;

// TODO: Auto-generated Javadoc
/**
 * Display UsageData record in mustache template.
 *
 * @author shermes641
 */
public class UsageDataView extends View {
    
    /** The ud. */
    private final UsageData ud;
    
    /**
     * The Enum Template.
     */
    public enum Template{
    	
	    /** The mustache. */
	    MUSTACHE("mustache/ud.mustache");
    	
    	/** The template name. */
	    private String templateName;
    	
	    /**
	     * Instantiates a new template.
	     *
	     * @param templateName the template name
	     */
	    private Template(String templateName){
    		this.templateName = templateName;
    	}
    	
    	/**
	     * Gets the template name.
	     *
	     * @return the template name
	     */
	    public String getTemplateName(){
    		return templateName;
    	}
    }

    /**
     * Instantiates a new usage data view.
     *
     * @param template the template
     * @param ud the ud
     */
    public UsageDataView(UsageDataView.Template template, UsageData ud) {
        super(template.getTemplateName());
        this.ud = ud;
    }

    /**
     * Gets the ud.
     *
     * @return the ud
     */
    public UsageData getUd() {
        return ud;
    }
}