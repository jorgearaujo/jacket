package com.araujo.jacket.mapping;

import com.araujo.jacket.exception.InvalidMappingException;
import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;

public class ActionBuilder extends CommonBuilder {
	private Mapping.Builder.Configuration configuration;

	private String uri;
	private String page;
	private String action;
	private SendingMethod sendingMethod;

	public ActionBuilder(Mapping.Builder.Configuration configuration,
			String action, String uri, String page)
			throws InvalidMappingException {
		super(configuration);
		this.action = action;
		this.uri = uri;
		this.page = page;

		// Check if action is specificed
		if (action == null) {
			Printer.print(Severity.ERR,
					Messages.INVALID_MAPPING_ACTION_NO_ACTION);
			throw new InvalidMappingException();
		}

		// Check if only uri or page are specified
		if (uri == null && page == null) {
			Printer.print(Severity.ERR,
					Messages.INVALID_MAPPING_ACTION_NO_PAGE_AND_NO_URI, action);
			throw new InvalidMappingException();
		} else if (uri != null && page != null) {
			Printer.print(Severity.ERR,
					Messages.INVALID_MAPPING_ACTION_PAGE_AND_URI, action);
			throw new InvalidMappingException();
		} else {
			// Sets forward or redirect due the specified data
			if (uri == null) {
				this.forward();
			} else if (page == null) {
				this.redirect();
			}
		}
	}

	private ActionBuilder redirect() {
		this.sendingMethod = SendingMethod.SEND_REDIRECT;
		return (ActionBuilder) this;
	}

	private ActionBuilder forward() {
		this.sendingMethod = SendingMethod.FORWARD;
		return (ActionBuilder) this;
	}

	public Mapping build() {
		Mapping mapping = super.build();
		mapping.setUri(uri);
		if (page != null) {
			mapping.setPage(configuration.getBasePath() + page);
		}
		mapping.setAction(action);
		if (this.sendingMethod == null) {
			mapping.setSendingMethod(configuration.getDefaultSending());
		} else {
			mapping.setSendingMethod(sendingMethod);
		}
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

	public String getAction() {
		return action;
	}

	public SendingMethod getSendingMethod() {
		return sendingMethod;
	}
}