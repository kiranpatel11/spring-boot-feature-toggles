package com.example.featuretoggle.web.jaxrs.resource.baz;

import com.example.featuretoggle.web.jaxrs.resource.foo.Foo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Baz extends Foo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Baz(String id) {
		super(id);
	}
	
}
