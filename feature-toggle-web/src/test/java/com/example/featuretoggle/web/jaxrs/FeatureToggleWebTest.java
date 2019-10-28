package com.example.featuretoggle.web.jaxrs;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasKey;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

import org.junit.Test;

import com.example.featuretoggle.web.jaxrs.resource.bar.BarResourceImpl;
import com.example.featuretoggle.web.jaxrs.resource.baz.BazResourceImpl;
import com.example.featuretoggle.web.jaxrs.resource.foo.FooResourceImpl;

public class FeatureToggleWebTest extends BaseCompTest {
	
	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.foo=true
	 * 
	 * @FeatureToggle(feature="bar") on {@link FooResourceImpl}
	 * 
	 */
	@Test
	public void testClassAnnotationTrue() {
		
		String uri = "/v1/foo/{id}";

		givenBaseSpec()
			.pathParam("id", "12345")
			.when()
				.get(uri)
				.then()
					.statusCode(200)
					.body("id", equalTo("12345"))
					.body("name", equalTo("Foo12345"));
	}
	
	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.baz=false (or property not present)
	 * 
	 * @FeatureToggle(feature="baz") on {@link BazResourceImpl}
	 * 
	 */
	@Test
	public void testClassAnnotationFalse() {
		
		String uri = "/v1/baz/{id}";

		givenBaseSpec()
			.pathParam("id", "12345")
			.when()
				.get(uri)
				.then()
					.statusCode(404);
	}
	
	
	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.bar.query=false (or not present)
	 * 
	 * No @FeatureToggle on {@link BarResourceImpl}
	 * 
	 */
	@Test
	public void testClassAnnotationFalseOverride() {
		
		String uri = "/v1/baz/{id}";

		givenBaseSpec()
			.pathParam("id", "12345")
			.header("my-trace-id", "47e8df49d3410d37:e9123acf450013b2:594b2c9afde0d6ae:1")
			.header("myctx-feature-baz-mode" , "requestonly")
			.when()
				.get(uri)
				.then()
					.statusCode(200)
					.body("id", equalTo("12345"))
					.body("name", equalTo("Baz12345"));
	}
	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.bar=true
	 * 
	 * No @FeatureToggle on {@link BarResourceImpl}
	 * 
	 */
	@Test
	public void testMethodAnnotationTrue() {
		
		String uri = "/v1/bar/{id}";

		givenBaseSpec()
			.pathParam("id", "12345")
			.when()
				.get(uri)
				.then()
					.statusCode(200)
					.body("id", equalTo("12345"))
					.body("name", equalTo("Bar12345"))
					.body("$", not(hasKey("details")));
	}
	
	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.bar.query=false (or not present)
	 * 
	 * No @FeatureToggle on {@link BarResourceImpl}
	 * 
	 */
	@Test
	public void testMethodAnnotationFalse() {
		
		String uri = "/v1/bar/";

		givenBaseSpec()
			.queryParam("query", "something")
			.when()
				.get(uri)
				.then()
					.statusCode(404);
	}
	
	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.bar.query=false (or not present)
	 * 
	 * No @FeatureToggle on {@link BarResourceImpl}
	 * 
	 */
	@Test
	public void testMethodAnnotationFalseOverride() {
		
		String uri = "/v1/bar/";

		givenBaseSpec()
			.header("my-trace-id", "47e8df49d3410d37:e9123acf450013b2:594b2c9afde0d6ae:1")
			.header("myctx-feature-bar-query" , "true")
			.queryParam("query", "something")
			.when()
				.get(uri)
				.then()
					.statusCode(200)
					.body("size()", is(6));				
	}

	/**
	 * 	Config for the Test
	 * 	application-test.properties
	 * 		toggles.features.bar=true
	 * 		toggles.features.bar.details=false (or not present)
	 * 
	 * No @FeatureToggle on {@link BarResourceImpl}
	 * 
	 */	
	@Test
	public void testRequestOverride() {
		
		String uri = "/v1/bar/{id}";

		givenBaseSpec()
			.header("my-trace-id", "47e8df49d3410d37:e9123acf450013b2:594b2c9afde0d6ae:1")
			.header("myctx-feature-bar-details" , "true")
			.pathParam("id", "12345")
			.when()
				.get(uri)
				.then()
					.statusCode(200)
					.body("id", equalTo("12345"))
					.body("name", equalTo("Bar12345"))
					.body("$", hasKey("details"));
	}


}
