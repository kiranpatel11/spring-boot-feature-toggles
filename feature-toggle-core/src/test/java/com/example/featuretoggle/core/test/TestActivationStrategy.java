package com.example.featuretoggle.core.test;

import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.featuretoggle.core.ActivationStrategy;
import com.example.featuretoggle.core.Feature;

/**
 * Dummy {@link ActivationStrategy} for unit testing
 * 
 * @author kp7466
 *
 */
@Profile("test")
@Order(1)
@Component
public class TestActivationStrategy implements ActivationStrategy {

	@Override
	public boolean isApplicable(Feature feature) {
		return false;
	}

	@Override
	public boolean isEnabled(Feature feature) {
		return false;
	}

	@Override
	public boolean havingValue(Feature feature, String value) {
		return false;
	}
}
