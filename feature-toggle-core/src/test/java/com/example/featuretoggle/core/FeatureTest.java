package com.example.featuretoggle.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import org.junit.Test;

import com.example.featuretoggle.core.Feature;

public class FeatureTest {
	
	@Test
	public void testFeature() {
		Feature feature = new Feature("feature1");
		assertEquals("feature1", feature.key()); 
		assertNotEquals("feature2", feature.key()); 
		assertEquals("", feature.name()); 

		
		Feature feature2 = new Feature("feature2", "feature2 strategy");
		assertEquals("feature2", feature2.key()); 
		assertNotEquals("something", feature2.key()); 
		assertEquals("feature2 strategy", feature2.name()); 
	}

}
