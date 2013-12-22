package com.araujo.jacket.mapping;

import com.araujo.jacket.exception.InvalidMappingException;
import com.araujo.jacket.mapping.Mapping.Builder.Configuration;

@SuppressWarnings("rawtypes")
public class Builder {
	private Mapping.Builder.Configuration configuration;

	public Builder() {
		this.configuration = new Configuration();
	}

	public Builder(Mapping.Builder.Configuration configuration) {
		this.configuration = configuration;
	}

	public ViewBuilder view(String uri, String page)
			throws InvalidMappingException {
		return new ViewBuilder(configuration, uri, page);
	}

	public ActionBuilder action(String action, String uri, String page)
			throws InvalidMappingException {
		return new ActionBuilder(configuration, action, uri, page);
	}

	public ExceptionBuilder exception(Class exception, String uri, String page)
			throws InvalidMappingException {
		return new ExceptionBuilder(configuration, exception, uri, page);
	}
}