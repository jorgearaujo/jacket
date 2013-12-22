package com.araujo.jacket.view;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import com.araujo.jacket.factory.JacketFactory;
import com.araujo.jacket.mapping.Mapping;
import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;
import com.araujo.jacket.provider.LocaleProvider;

public class JacketViewFactory implements ViewFactory {

	public View makeView(HttpServletRequest request, Mapping mapping) {
		String page = mapping.getPage();
		HashMap<String, Object> data = getStringsFromDirectory(request, mapping);
		View view = new View(page, data);
		return view;
	}

	private HashMap<String, Object> getStringsFromDirectory(
			HttpServletRequest request, Mapping mapping) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		File directory = getStringsDirectory(request, mapping);
		if (directory != null
				&& (mapping.getStrings() == null || mapping.getStrings()
						.isEmpty())) {
			File stringsDirectory = new File(directory.getPath());
			String[] fileList = stringsDirectory.list();
			mapping.setStrings(Arrays.asList(fileList));
		}

		if (mapping.getStrings() != null) {
			for (String xml : mapping.getStrings()) {
				map.putAll(JacketFactory.getStringsHandler().getStrings(
						new File(directory + "/" + xml)));
			}
		}
		return map;
	}

	private File getStringsDirectory(HttpServletRequest request, Mapping mapping) {
		File directory = null;
		Locale locale = getLocale(request, mapping);
		String stringsPath = mapping.getStringsPath() + "-"
				+ locale.getLanguage();
		File localeDirectory = new File(request.getServletContext()
				.getRealPath(stringsPath));
		if (localeDirectory.exists()) {
			directory = localeDirectory;
		} else {
			stringsPath = mapping.getStringsPath();
			File standardDirectory = new File(request.getServletContext()
					.getRealPath(stringsPath));
			if (standardDirectory.exists()) {
				directory = standardDirectory;
			}
		}
		return directory;
	}

	private Locale getLocale(HttpServletRequest request, Mapping mapping) {
		Locale locale = Locale.forLanguageTag("");
		if (mapping.getLocale() != null) {
			locale = mapping.getLocale();
		} else if (mapping.getLocaleProvider() != null) {
			try {
				locale = ((LocaleProvider) mapping.getLocaleProvider()
						.newInstance()).loadLocale(request);
			} catch (InstantiationException e) {
				Printer.print(Severity.ERR, Messages.LOCALE_PROVIER_CANNOT_INSTANTIATE, mapping.getLocaleProvider().getName());
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				Printer.print(Severity.ERR, Messages.LOCALE_PROVIDER_CANNOT_ACCESS, mapping.getLocaleProvider().getName());
				e.printStackTrace();
			}
		} else {
			locale = request.getLocale();
		}
		return locale;
	}

}
