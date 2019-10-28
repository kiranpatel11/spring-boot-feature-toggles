package com.example.featuretoggle.web.jaxrs;

import java.lang.reflect.Method;

import javax.ws.rs.Priorities;
import javax.ws.rs.container.DynamicFeature;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.FeatureContext;
import javax.ws.rs.ext.Provider;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import com.example.featuretoggle.web.FeatureToggle;

/**
 * Register the {@link FeatureToggleFilter} for ResourceHandler (class or method) 
 * if JAXRS Resource class or method is annotated with {@link FeatureToggle} annotation.
 * 
 * @author kp7466
 *
 */
@ConditionalOnProperty(value="my.feature.toggle.enabled", matchIfMissing=true)
@Provider
public class FeatureToggleFilterBinder implements DynamicFeature {
	
	@Override
	public void configure(ResourceInfo resourceInfo, FeatureContext context) {
		Class<?> clazz = resourceInfo.getResourceClass();
        Method method = resourceInfo.getResourceMethod();

        if (method.isAnnotationPresent(FeatureToggle.class)) {
        	FeatureToggle feature = method.getAnnotation(FeatureToggle.class);
        	context.register(new FeatureToggleFilter(feature), Priorities.HEADER_DECORATOR);
        } else if (clazz.isAnnotationPresent(FeatureToggle.class)) {
        	FeatureToggle feature = clazz.getAnnotation(FeatureToggle.class);
        	context.register(new FeatureToggleFilter(feature), Priorities.HEADER_DECORATOR);
       } 		
	}

}
