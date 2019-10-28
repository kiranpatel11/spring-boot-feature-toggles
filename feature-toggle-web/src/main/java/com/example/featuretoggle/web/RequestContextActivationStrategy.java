package com.example.featuretoggle.web;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.example.featuretoggle.core.ActivationStrategy;
import com.example.featuretoggle.core.Feature;

import io.opentracing.Tracer;

/**
 * RequestContextActivationStrategy loads the feature toggle properties 
 * from Requestcontext. 
 * As the name suggests, it only applicable to the particular request 
 * and usually used to override the default configuration defined by env variables.
 * 
 * The implementation uses the opentracing {@link Tracer} to get hold of the current context.
 * 
 * @author kp7466
 *
 */
@Order(1)
@Component
public class RequestContextActivationStrategy implements ActivationStrategy {
	private static final Logger logger = LoggerFactory.getLogger(RequestContextActivationStrategy.class);
	
	private static final String HEADER_PREFIX = "feature-";
	private static final String HEADER_PREFIX_ON = "feature-on";
	private static final String HEADER_PREFIX_OFF = "feature-off";
	private static final String BOOLEAN_STRING_TRUE= "true";
	private static final String BOOLEAN_STRING_FALSE= "false";
	
	//Added for key-value pair features
	private static final String HEADER_FEATURE_KEY_VALUE = "feature-kv";
	
	/**
	 * Tracer to get the feature
	 */
	private Tracer tracer;
	
	/**
	 * Constructor
	 * @param tracer
	 */
	@Autowired
	public RequestContextActivationStrategy(Tracer tracer) {
		super();
		this.tracer = tracer;
	}
	
	@Override
	public boolean isApplicable(Feature feature) {
		String value = getValueInRequestContext(feature);
		return (value == null) ? false : true; 
	}

	@Override
	public boolean isEnabled(Feature feature) {
		String value = getValueInRequestContext(feature);
		return Boolean.parseBoolean(value);
	}

	@Override
	public boolean havingValue(Feature feature, String value) {
		String configuredValue = getValueInRequestContext(feature);
		return value.equalsIgnoreCase(configuredValue);
	}
	
	String getValueInRequestContext(Feature feature) {
		String value = tracer.activeSpan().getBaggageItem(HEADER_PREFIX + feature.key());
		if(value == null) {
			if(isSpanContainsFeature(HEADER_PREFIX_ON, feature.key()))
				value = BOOLEAN_STRING_TRUE;
			if(isSpanContainsFeature(HEADER_PREFIX_OFF, feature.key()))
				value = BOOLEAN_STRING_FALSE;
			//Code snippet added for feature toggle key value pair for eg: feature-kv=feature1=false&feature2=true
			String headerFeatureKeyValue = tracer.activeSpan().getBaggageItem(HEADER_FEATURE_KEY_VALUE); 
			if (!StringUtils.isEmpty(headerFeatureKeyValue)) { 
				try { 
					Map<String, String> featureMap = Arrays.stream(headerFeatureKeyValue.split("&")) 
					    .map(entry -> entry.split("=")) 
					    .collect(Collectors.toMap( 
					        entry -> entry[0],  //key 
					        entry -> entry[1]   //value 
					    )); 
					value = featureMap.get(feature.key()); 
				} catch (Exception ex) { 
					logger.warn("Unable to parse the given Header Feature Key Value Map : {} due to Exception : {}", headerFeatureKeyValue, ex.getMessage());
					value = BOOLEAN_STRING_FALSE; 
				} 
			}  
		}
		return value;
	}

	private boolean isSpanContainsFeature(String headerPrefix, String key) {
		 return (tracer.activeSpan().getBaggageItem(headerPrefix) != null && 
				tracer.activeSpan().getBaggageItem(headerPrefix).contains(key));
	}
}
