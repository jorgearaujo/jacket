package com.araujo.jacket.view;

import java.util.HashMap;

import org.glassfish.jersey.server.mvc.Viewable;


public class View extends Viewable {
	public View(String templateName) throws IllegalArgumentException {
		super(templateName, null, null);
	}

	public View(String templateName, HashMap<String, Object> data)
			throws IllegalArgumentException {
		super(templateName, data, null);
	}

}