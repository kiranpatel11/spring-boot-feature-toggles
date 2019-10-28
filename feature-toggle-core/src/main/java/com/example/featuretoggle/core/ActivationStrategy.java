package com.example.featuretoggle.core;

import org.springframework.core.annotation.Order;

/**
 * ActivationStrategy to be applied for the {@link Feature}.
 * Please use the {@link Order} annotation on the implementation instances to define their order of precedence. 
 * 
 * @see FeatureManager for how ActivationStrategies are applied.
 * @author kp7466
 *
 */
public interface ActivationStrategy {
	
	/**
	 * Determines the applicability of this strategy for the given feature.
	 * 
	 * @param feature
	 * @return
	 */
	public boolean isApplicable(Feature feature);
	
	/**
	 * Determines if the feature is enabled or not, based on this strategy.
	 * 
	 * @param feature
	 * @return
	 */
	public boolean isEnabled(Feature feature);

	/**
	 * Determines if the feature is expected to have a particular value or not, based on this strategy 
	 * 
	 * @param feature
	 * @param value
	 * @return
	 */
	public boolean havingValue(Feature feature, String value);
}
