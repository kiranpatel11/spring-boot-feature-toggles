package com.example.featuretoggle.core;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
/**
 * SystemPropertyActivationStrategy loads the feature toggle properties based on environment properties. 
 * This is usually the default configuration for those properties, 
 * hence the {@link Order} annotation should have higher value(lower precedence).
 * 
 * @author kp7466
 *
 */
@Order(10)
@Component
@EnableConfigurationProperties(FeatureProperties.class)
public class SystemPropertyActivationStrategy implements ActivationStrategy {

	private FeatureProperties featureProperties;
	
	@Autowired
	public SystemPropertyActivationStrategy(FeatureProperties featureProperties) {
		super();
		this.featureProperties = featureProperties;
	}

	@Override
	public boolean isApplicable(Feature feature) {
		return true; 
	}
	
	@Override
	public boolean isEnabled(Feature feature) {
		String value = featureProperties.getProperty(feature.key());
		return Boolean.parseBoolean(value);
	}

	@Override
	public boolean havingValue(Feature feature, String value) {
		String configuredValue = featureProperties.getProperty(feature.key());		
		return value.equalsIgnoreCase(configuredValue);
	}

}

@ConfigurationProperties(prefix = "toggles")
class FeatureProperties {
		
	private Map<String, String> features = new HashMap<>();
	
	public Map<String, String> getFeatures() {
		return features;
	}

	public String getProperty(String key) {
		return features.get(key);
	}		
}
