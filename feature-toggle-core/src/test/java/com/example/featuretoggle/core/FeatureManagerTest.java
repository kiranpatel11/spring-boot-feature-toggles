package com.example.featuretoggle.core;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.featuretoggle.TestApp;
import com.example.featuretoggle.core.Feature;
import com.example.featuretoggle.core.FeatureManager;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes=TestApp.class)
public class FeatureManagerTest {
	
	@Test
	public void testSystemPropertyActivationStrategy() {		
		
			Feature feature = new Feature("foo");
			assertTrue(FeatureManager.isEnabled(feature));

			feature = new Feature("bar");
			assertTrue(FeatureManager.isEnabled(feature));
			
			feature = new Feature("bar-details");
			assertFalse(FeatureManager.isEnabled(feature));
			
			feature = new Feature("baz-mode");
			assertFalse(FeatureManager.havingValue(feature, "details"));
			
			feature = new Feature("baz-mode");
			assertTrue(FeatureManager.havingValue(feature, "summary"));
	}
	
}
