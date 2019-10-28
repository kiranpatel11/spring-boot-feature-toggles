package com.example.featuretoggle.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.featuretoggle.TestApp;
import com.example.featuretoggle.core.Feature;
import com.example.featuretoggle.web.RequestContextActivationStrategy;

import io.opentracing.Tracer;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.NONE, classes = TestApp.class)
public class RequestContextActivationStrategyTest {

	@Autowired
	private Tracer tracer;

	private RequestContextActivationStrategy requestContextActivationStrategy;

	@Before
	public void setUp() throws Exception {
		tracer.buildSpan("span1").startActive(true);
		requestContextActivationStrategy = new RequestContextActivationStrategy(tracer);
	}

	@Test
	public void testIsApplicable() throws Exception {

		tracer.activeSpan().setBaggageItem("feature-bar-details", "true");
		Feature feature = new Feature("bar-details");
		assertTrue(requestContextActivationStrategy.isApplicable(feature));
	}

	@Test
	public void testIsEnabled() throws Exception {
		tracer.activeSpan().setBaggageItem("feature-bar-details", "true");
		Feature feature = new Feature("bar-details");
		assertTrue(requestContextActivationStrategy.isEnabled(feature));
	}

	@Test
	public void testHavingValue() throws Exception {
		tracer.activeSpan().setBaggageItem("feature-bar-details", "true");
		Feature feature = new Feature("bar-details");
		assertTrue(requestContextActivationStrategy.havingValue(feature, "true"));
	}

	// With Single fetaure as key value
	@Test
	public void testGetValueInRequestContextWithSingleKV() throws Exception {
		tracer.activeSpan().setBaggageItem("feature-kv", "feature-bar-details=true");
		Feature feature = new Feature("bar-details");
		assertEquals(requestContextActivationStrategy.getValueInRequestContext(feature), "true");
	}

	// With multiple fetaure as key value
	@Test
	public void testGetValueInRequestContextWithMultipleKV() throws Exception {
		tracer.activeSpan().setBaggageItem("feature-kv",
				"feature-foo-details=true&feature-bar-details=true&feature-foo-details=true");
		Feature feature = new Feature("bar-details");
		assertEquals(requestContextActivationStrategy.getValueInRequestContext(feature), "true");
	}

	
}
