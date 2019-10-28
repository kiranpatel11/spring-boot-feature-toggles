package com.example.featuretoggle;

import java.util.logging.Logger;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.logging.LoggingFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.example.featuretoggle.web.jaxrs.FeatureToggleFilterBinder;
import com.example.featuretoggle.web.jaxrs.resource.bar.BarResourceImpl;
import com.example.featuretoggle.web.jaxrs.resource.baz.BazResourceImpl;
import com.example.featuretoggle.web.jaxrs.resource.foo.FooResourceImpl;

@Profile("test")
@Component
@ApplicationPath("/")
public class JerseyConfig extends ResourceConfig{
	
	private static final Logger log = Logger.getLogger(LoggingFeature.class.getName());
	
	@Autowired
    public JerseyConfig() {		
		register(FooResourceImpl.class);
		register(BarResourceImpl.class);
		register(BazResourceImpl.class);
		register(FeatureToggleFilterBinder.class);
        register(new LoggingFeature(log, LoggingFeature.DEFAULT_VERBOSITY));
    }
}
