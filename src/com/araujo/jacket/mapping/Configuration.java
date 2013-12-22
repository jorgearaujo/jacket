package com.araujo.jacket.mapping;

import java.util.Locale;

@SuppressWarnings("rawtypes")
class Configuration {
	private String defaultStringsPath = "string";
	private Locale defaultLocale = Locale.ENGLISH;
	private Class defaultLocaleProvider = null;
	private String defaultAccess = null;
	private Class defaultAccessControlProvider = null;
	private SendingMethod defaultSendingMethod = SendingMethod.SEND_REDIRECT;
	private String basePath = "";

	public String getDefaultStringsPath() {
		return defaultStringsPath;
	}

	public void setDefaultStringsPath(String defaultStringsPath) {
		if (defaultStringsPath != null && !defaultStringsPath.startsWith("/")) {
			defaultStringsPath = "/" + defaultStringsPath;
		}
		if (defaultStringsPath != null && defaultStringsPath.endsWith("/")) {
			defaultStringsPath = defaultStringsPath.substring(0,
					defaultStringsPath.length() - 1);
		}
		this.defaultStringsPath = defaultStringsPath;
	}

	public Locale getDefaultLocale() {
		return defaultLocale;
	}

	public void setDefaultLocale(Locale defaultLocale) {
		this.defaultLocale = defaultLocale;
	}

	public Class getDefaultLocaleProvider() {
		return defaultLocaleProvider;
	}

	public void setDefaultLocaleProvider(Class defaultLocaleProvider) {
		this.defaultLocaleProvider = defaultLocaleProvider;
	}

	public String getDefaultAccess() {
		return defaultAccess;
	}

	public void setDefaultAccess(String defaultAccess) {
		this.defaultAccess = defaultAccess;
	}

	public Class getDefaultAccessControlProvider() {
		return defaultAccessControlProvider;
	}

	public void setDefaultAccessControlProvider(
			Class defaultAccessControlProvider) {
		this.defaultAccessControlProvider = defaultAccessControlProvider;
	}

	public SendingMethod getDefaultSending() {
		return defaultSendingMethod;
	}

	public void setDefaultSendingMethod(SendingMethod defaultSending) {
		this.defaultSendingMethod = defaultSending;
	}

	public String getBasePath() {
		return basePath;
	}

	public void setBasePath(String basePath) {
		if (basePath != null && !basePath.startsWith("/")) {
			basePath = "/" + basePath;
		}
		if (basePath != null && !basePath.endsWith("/")) {
			basePath = basePath + "/";
		}
		this.basePath = basePath;

	}
}