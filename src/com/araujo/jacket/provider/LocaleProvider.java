package com.araujo.jacket.provider;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

public interface LocaleProvider {

	Locale loadLocale(HttpServletRequest request);
	
}
