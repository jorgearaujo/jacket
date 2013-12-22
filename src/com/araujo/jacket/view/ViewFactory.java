package com.araujo.jacket.view;

import javax.servlet.http.HttpServletRequest;

import com.araujo.jacket.mapping.Mapping;

public interface ViewFactory {

	View makeView(HttpServletRequest request, Mapping mapping);
	
}
