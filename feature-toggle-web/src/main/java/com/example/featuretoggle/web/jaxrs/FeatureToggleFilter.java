package com.example.featuretoggle.web.jaxrs;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.example.featuretoggle.core.Feature;
import com.example.featuretoggle.core.FeatureManager;
import com.example.featuretoggle.web.FeatureToggle;

/**
 * Filter to dynamically route the request based on featuretoggle. 
 * It
 * 
 * @author kp7466
 *
 */
public class FeatureToggleFilter implements ContainerRequestFilter {
	
	private static final Logger log = LoggerFactory.getLogger(FeatureToggleFilter.class);
	
	private FeatureToggle featureToggle;

	public FeatureToggleFilter(FeatureToggle featureToggle) {
		super();
		this.featureToggle = featureToggle;
	}
	
	@Override
	public void filter(ContainerRequestContext reqContext) throws IOException {
		
		if (!isFeatureEnabled(featureToggle))  {
			log.debug("Request aborted by filter as feature is disabled" , featureToggle.feature());
			reqContext.abortWith(Response.status(Status.NOT_FOUND).build());
	   }
	}	
	
	private boolean isFeatureEnabled(FeatureToggle annotation) {
		
		boolean state = annotation.matchIfMissing();		
		Feature feature = new Feature(annotation.feature());
		
	    if((!annotation.havingValue().isEmpty()) && 
			    FeatureManager.havingValue(feature, annotation.havingValue())) {
	    	state = true;
		}
	    
	    if(FeatureManager.isEnabled(feature)) {
	    	state = true;
		}
	    
	    return state;
	  }
}
