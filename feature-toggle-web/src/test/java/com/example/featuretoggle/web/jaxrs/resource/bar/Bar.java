package com.example.featuretoggle.web.jaxrs.resource.bar;

import com.example.featuretoggle.web.jaxrs.resource.foo.Foo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)
public class Bar extends Foo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bar(String id) {
		super(id);
		// TODO Auto-generated constructor stub
	}
	
	private Details details;

	public Details getDetails() {
		return details;
	}

	public void setDetails(Details details) {
		this.details = details;
	}	
	
	
	
}

class Details {
	
	private String notes;
	
	public Details(String notes) {
		super();
		this.notes = notes;
	}

	public String getNotes() {
		return notes;
	}
	
}