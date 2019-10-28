package com.example.featuretoggle.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FeatureManager provides the ability evaluate a feature toggle.
 * 
 * It applies the various strategies by their order of precedence for the process of evaluation.
 * 
 * 
 * @see ActivationStrategy
 * @author kp7466
 *
 */
public class FeatureManager {
	
	/**
	 * Singleton
	 */
	private FeatureManager() {}
	
	private static final Logger log = LoggerFactory.getLogger(FeatureManager.class);
	
	public static boolean isEnabled(Feature feature) {
		ActivationStrategy strategy = ActivationStrategyProvider.getApplicableStrategy(feature);
		
		if(strategy.isEnabled(feature))
			log.info(" Feature ["+feature.key()+"] is executed");
		
		return strategy.isEnabled(feature);
	}
	
	public static boolean havingValue(Feature feature, String value) {
		ActivationStrategy strategy = ActivationStrategyProvider.getApplicableStrategy(feature);
		return strategy.havingValue(feature, value);
	}
}