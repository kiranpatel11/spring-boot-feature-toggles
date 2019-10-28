# Feature Toggle

Implementation of the feature toggle capabilities explained in Martin Fowler's article @ https://martinfowler.com/articles/feature-toggles.html


# Maven Coordinates

In non-web environment, Use feature-toggle-core maven dependency as mentioned below.

```	
	<dependency>
		<groupId>com.example</groupId>
		<artifactId>feature-toggle-core</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
```

In web environment, Use feature-toggle-web maven dependency as mentioned below.

```
	<dependency>
		<groupId>com.example</groupId>
		<artifactId>feature-toggle-web</artifactId>
		<version>0.0.1-SNAPSHOT</version>
	</dependency>
```

# Example Usage

For #1 and #2 below, please make configuration in JerseyConfiguration

```
	register(FeatureToggleFilterBinder.class);
```

The implementation covers below scenarios for feature toggle - 
1. JAX-RS Resource/REST API Endpoint

```
@FeatureToggle(feature="foo")
    @Controller
    public class FooResourceImpl implements FooResource {
	
	@Override
	public Response getFoo(String id) {
    				
	}
}
```

2. JAX-RS Method

```
@Controller
public class BarResourceImpl implements BarResource {

	@FeatureToggle(feature="bar")
	@Override
	public Response getBar(String id) {		
		Bar bar = createBar(id);	
		return Response.ok(bar).build();
	}	

}
```

3. Anywhere in a method implementation, Please refer test cases for complete example.

```
	private Bar createBar(String id) {
		Bar bar = new Bar(id);
		bar.setName("Bar" + id);		

		//Indirection/abstraction to hide how the "bar" is enhanced with "details"
		//This helps keeping featureToggle code to designated classes, avoids proliferation of toggles throughout the code, 
		BarDetailsStrategy.barDetails(bar);
		return bar;
	}
	
	
	/**
	 * Enhances Bar object based on feature toggle.
	 */
	public class BarDetailsStrategy {
		
		static Bar barDetails(Bar bar) {
			Feature barDetails = new Feature("bar-details");		
			if(FeatureManager.isEnabled(barDetails)) {
				bar.setDetails(new Details("details of " + bar.getId()));
			}
			return bar;
		}
	}
```

## Feature toggle properties

Feature toggle can be controlled using various ActivationStrategies. e.g. Env variables or override on per request basis.

### application.properties

Below example explains how the feature properties are defined in spring-boot-application properties

```
# any undefined property takes false as default value.

# properties as boolean flags
toggles.features.foo=true
toggles.features.bar-details=false

# properties having a particular value
toggles.features.baz-mode=summary 
```

### Feature override using request header parameter.

Here, the opentracing context is used to carry the request parameters.

Header Pattern for feature toggle : 
myctx-feature-<feature-key>=<feature-state>

e.g. 

```
Request URL: https://play.google.com/log?format=json
Request Method: POST
Request HEADERS
my-trace-id: ace7b3849def4d0f:786aa7f7aa1e6bae:18b191d2526516a5:1  			
myctx-feature-foo: true
myctx-feature-bar-mode: summary
```

The implementation uses the opentracing request context. In addition to the header for the feature, another  header "my-trace-id" is mandatory for the request basis feature override to work.  