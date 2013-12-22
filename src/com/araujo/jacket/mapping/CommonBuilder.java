package com.araujo.jacket.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@SuppressWarnings("rawtypes")
public class CommonBuilder {

	protected Mapping.Builder.Configuration configuration;

	private String access;
	private Class accessControl;
	private String stringsPath;
	private List<String> strings;
	private Locale locale;
	private Class localeProvider;

	public CommonBuilder(Mapping.Builder.Configuration configuration) {
		this.configuration = configuration;
		this.strings = new ArrayList<String>();
		
	}

	public CommonBuilder locale(Locale locale) {
		this.locale = locale;
		return (CommonBuilder) this;
	}

	public CommonBuilder locale(Class localeProvider) {
		this.localeProvider = localeProvider;
		return (CommonBuilder) this;
	}

	public CommonBuilder accessControl(Class accessControl) {
		this.accessControl = accessControl;
		return (CommonBuilder) this;
	}

	public CommonBuilder access(String access) {
		this.access = access;
		return (CommonBuilder) this;
	}

	public CommonBuilder stringsPath(String stringsPath) {
		this.stringsPath = stringsPath;
		return (CommonBuilder) this;
	}

	public CommonBuilder string(String string) {
		this.strings.add(string);
		return (CommonBuilder) this;
	}

	public Mapping build() {
		if (this.stringsPath == null) {
			this.stringsPath = configuration.getDefaultStringsPath();
		}
		if (this.locale == null) {
			if (this.localeProvider == null) {
				if (configuration.getDefaultLocaleProvider() == null) {
					this.locale = configuration.getDefaultLocale();
				} else {
					this.localeProvider = configuration
							.getDefaultLocaleProvider();
				}
			}
		}
		if (this.access == null) {
			this.access = configuration.getDefaultAccess();
		}
		if (this.accessControl == null) {
			this.accessControl = configuration
					.getDefaultAccessControlProvider();
		}
		return new Mapping(this);
	}

	public Mapping.Builder.Configuration getConfiguration() {
		return configuration;
	}

	public String getAccess() {
		return access;
	}

	public Class getAccessControl() {
		return accessControl;
	}

	public String getStringsPath() {
		return stringsPath;
	}

	public List<String> getStrings() {
		return strings;
	}

	public Locale getLocale() {
		return locale;
	}

	public Class getLocaleProvider() {
		return localeProvider;
	}

}