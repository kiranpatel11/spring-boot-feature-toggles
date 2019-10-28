package com.example.featuretoggle.web.jaxrs.resource.bar;

import com.example.featuretoggle.core.Feature;
import com.example.featuretoggle.core.FeatureManager;
/**
 * 
 * Enhances Bar object based on feature toggle.
 * 
 * @author kp7466
 *
 */
public class BarDetailsStrategy {
	
	static Bar barDetails(Bar bar) {
		Feature barDetails = new Feature("bar-details");		
		if(FeatureManager.isEnabled(barDetails)) {
			bar.setDetails(new Details("details of " + bar.getId()));
		}
		return bar;
	}
}
