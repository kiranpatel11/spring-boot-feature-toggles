package com.example.featuretoggle.web.jaxrs.resource.baz;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import com.example.featuretoggle.web.FeatureToggle;

@FeatureToggle(feature="baz-mode", havingValue="requestonly")
@Controller
public class BazResourceImpl implements BazResource {
	
	@Override
	public Response getBar(String id) {		
		Baz bar = createBar(id);	
		return Response.ok(bar).build();
	}
	
	private Baz createBar(String id) {
		Baz baz = new Baz(id);
		baz.setName("Baz" + id);		
		
		return baz;
	}
	
}