package com.example.featuretoggle.web.jaxrs.resource.bar;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import com.example.featuretoggle.core.Feature;
import com.example.featuretoggle.core.FeatureManager;
import com.example.featuretoggle.web.FeatureToggle;

@Controller
public class BarResourceImpl implements BarResource {

	@FeatureToggle(feature="bar")
	@Override
	public Response getBar(String id) {		
		Bar bar = createBar(id);	
		return Response.ok(bar).build();
	}	

	
	@FeatureToggle(feature="bar-query")
	@Override
	public Response getBarByQuery(String query) {
		
		List<Bar> bars = new ArrayList<>();
		
		for(int i=1000; i<=1005; i++) {			
			bars.add(createBar("" + i));
		}
		
		return Response.ok(bars).build();
	}
	
	
	private Bar createBar(String id) {
		Bar bar = new Bar(id);
		bar.setName("Bar" + id);		

		//indirection to hide how the "bar" is enhanced with "details"
		//This helps keeping featureToggle code to designated classes, avoids proliferation of toggles throughout the code, 
		// e.g. Strategy class in this example
		BarDetailsStrategy.barDetails(bar);
		return bar;
	}
	
}