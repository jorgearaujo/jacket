package com.araujo.jacket.mapping;

import com.araujo.jacket.exception.InvalidMappingException;
import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;

@SuppressWarnings("rawtypes")
public class ExceptionBuilder extends CommonBuilder {
	private String uri;
	private String page;
	private Class exception;
	private SendingMethod sendingMethod;

	public ExceptionBuilder(Mapping.Builder.Configuration configuration,
			Class exception, String uri, String page)
			throws InvalidMappingException {
		super(configuration);
		this.uri = uri;
		this.page = page;
		this.exception = exception;

		// Check if exception is specificed
		if (exception == null) {
			Printer.print(Severity.ERR,
					Messages.INVALID_MAPPING_ACTION_NO_ACTION);
			throw new InvalidMappingException();
		}

		// Check if only uri or page are specified
		if (uri == null && page == null) {
			Printer.print(Severity.ERR,
					Messages.INVALID_MAPPING_EXCEPTION_NO_PAGE_AND_NO_URI,
					exception.getName());
			throw new InvalidMappingException();
		} else if (uri != null && page != null) {
			Printer.print(Severity.ERR,
					Messages.INVALID_MAPPING_EXCEPTION_PAGE_AND_URI,
					exception.getName());
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

	private ExceptionBuilder redirect() {
		this.sendingMethod = SendingMethod.SEND_REDIRECT;
		return (ExceptionBuilder) this;
	}

	private ExceptionBuilder forward() {
		this.sendingMethod = SendingMethod.FORWARD;
		return (ExceptionBuilder) this;
	}

	public Mapping build() {
		Mapping mapping = super.build();
		mapping.setUri(uri);
		if (page != null) {
			mapping.setPage(configuration.getBasePath() + page);
		}
		mapping.setException(exception);
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

	public Class getException() {
		return exception;
	}

	public SendingMethod getSendingMethod() {
		return sendingMethod;
	}

	public void setSendingMethod(SendingMethod sendingMethod) {
		this.sendingMethod = sendingMethod;
	}
}