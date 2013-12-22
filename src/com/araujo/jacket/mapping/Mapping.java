package com.araujo.jacket.mapping;

import java.util.List;
import java.util.Locale;

import com.araujo.jacket.factory.JacketFactory;

@SuppressWarnings("rawtypes")
public class Mapping {
	private String uri;
	private String page;
	private Class exception;
	private String access;
	private Class accessControl;
	private String stringsPath;
	private List<String> strings;
	private Locale locale;
	private Class localeProvider;
	private String action;
	private SendingMethod sendingMethod;

	public static class Builder extends com.araujo.jacket.mapping.Builder {
		public static class Configuration extends
				com.araujo.jacket.mapping.Configuration {
		}

		public Builder() {
			super();
		}

		public Builder(Mapping.Builder.Configuration configuration) {
			super(configuration);
		}

	}

	public static class Handler extends com.araujo.jacket.mapping.Handler {
	}

	public Mapping(CommonBuilder builder) {
		this.stringsPath = builder.getStringsPath();
		this.strings = builder.getStrings();
		this.locale = builder.getLocale();
		this.localeProvider = builder.getLocaleProvider();
		this.accessControl = builder.getAccessControl();
		this.access = builder.getAccess();
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getPage() {
		return page;
	}

	public void setPage(String page) {
		this.page = page;
	}

	public Class getException() {
		return exception;
	}

	public void setException(Class exception) {
		this.exception = exception;
	}

	public String getAccess() {
		return access;
	}

	public void setAccess(String access) {
		this.access = access;
	}

	public String getStringsPath() {
		return stringsPath;
	}

	public void setStringsPath(String stringsPath) {
		this.stringsPath = stringsPath;
	}

	public List<String> getStrings() {
		return strings;
	}

	public void setStrings(List<String> strings) {
		this.strings = strings;
	}

	public Class getLocaleProvider() {
		return localeProvider;
	}

	public void setLocaleProvider(Class localeProvider) {
		this.localeProvider = localeProvider;
	}

	public Locale getLocale() {
		return locale;
	}

	public void setLocale(Locale locale) {
		this.locale = locale;
	}

	public Class getAccessControl() {
		return accessControl;
	}

	public void setAccessControl(Class accessControl) {
		this.accessControl = accessControl;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public SendingMethod getSendingMethod() {
		return sendingMethod;
	}

	public void setSendingMethod(SendingMethod sendingMethod) {
		this.sendingMethod = sendingMethod;
	}

	public Mapping add() {
		return JacketFactory.getMappingHandler().addMapping(this);
	}

}