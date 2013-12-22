package com.araujo.jacket.mapping;

import com.araujo.jacket.exception.InvalidMappingException;
import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;

public class ViewBuilder extends CommonBuilder {

	private String uri;
	private String page;

	public ViewBuilder(Mapping.Builder.Configuration configuration, String uri,
			String page) throws InvalidMappingException {
		super(configuration);
		this.uri = uri;
		this.page = page;
		// Check if uri and page are specificed
		if (uri == null || page == null) {
			Printer.print(Severity.ERR, Messages.INVALID_MAPPING_VIEW);
			throw new InvalidMappingException();
		}
	}

	public Mapping build() {
		Mapping mapping = super.build();
		mapping.setUri(this.uri);
		mapping.setPage(configuration.getBasePath() + this.page);
		return mapping;
	}

	public Mapping.Builder.Configuration getConfiguration() {
		return configuration;
	}

	public String getUri() {
		return uri;
	}

	public String getPage() {
		return page;
	}

}