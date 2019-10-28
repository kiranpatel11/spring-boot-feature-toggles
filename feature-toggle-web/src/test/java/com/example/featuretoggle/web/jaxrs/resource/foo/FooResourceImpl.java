package com.example.featuretoggle.web.jaxrs.resource.foo;

import javax.ws.rs.core.Response;

import org.springframework.stereotype.Controller;

import com.example.featuretoggle.web.FeatureToggle;

@FeatureToggle(feature="foo")
@Controller
public class FooResourceImpl implements FooResource {
	
	@Override
	public Response getFoo(String id) {
				
		Foo foo = new Foo(id);
		foo.setName("Foo" + id);
		
		return Response.ok(foo).build();
	}
	
}