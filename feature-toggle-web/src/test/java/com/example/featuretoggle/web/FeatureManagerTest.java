package com.example.featuretoggle.web;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.featuretoggle.TestApp;
import com.example.featuretoggle.core.Feature;
import com.example.featuretoggle.core.FeatureManager;
import com.example.featuretoggle.web.RequestContextActivationStrategy;

import io.opentracing.Scope;
import io.opentracing.Tracer;
/**
 * Test FeatureManager with {@link RequestContextActivationStrategy}
 * 
 * 
 * @author kp7466
 *
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes=TestApp.class)
public class FeatureManagerTest {
	
	@Autowired
	private Tracer tracer;
	
	@Test
	public void testRequestContextActivationStrategyIsEnabled() {		

		//Assert True for override scenario.
		try (Scope scope = tracer.buildSpan("span1").startActive(true)) {			
			tracer.activeSpan().setBaggageItem("feature-bar-details", "true");
			
			Feature feature = new Feature("bar-details");
			assertTrue(FeatureManager.isEnabled(feature));
		}				
		
		//Assert false for default/non-override scenario.
		try (Scope scope = tracer.buildSpan("span2").startActive(true)) {			
			Feature feature = new Feature("bar-details");
			assertFalse(FeatureManager.isEnabled(feature));
		}				

		//Assert True for override scenario.
		try (Scope scope = tracer.buildSpan("span3").startActive(true)) {			
			tracer.activeSpan().setBaggageItem("feature-baz-mode", "details");
			
			Feature feature = new Feature("baz-mode");
			assertFalse(FeatureManager.havingValue(feature, "summary"));
			
			assertTrue(FeatureManager.havingValue(feature, "details"));
		}				
		
	}
	
}
