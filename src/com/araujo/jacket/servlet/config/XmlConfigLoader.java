package com.araujo.jacket.servlet.config;

import java.io.File;
import java.io.IOException;
import java.util.Locale;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.araujo.jacket.Jacket;
import com.araujo.jacket.exception.DuplicateMappingException;
import com.araujo.jacket.exception.InvalidMappingException;
import com.araujo.jacket.mapping.ActionBuilder;
import com.araujo.jacket.mapping.CommonBuilder;
import com.araujo.jacket.mapping.ExceptionBuilder;
import com.araujo.jacket.mapping.Mapping;
import com.araujo.jacket.mapping.SendingMethod;
import com.araujo.jacket.mapping.ViewBuilder;

public class XmlConfigLoader implements ConfigLoader<File> {

	private final String CONFIG = "config";
	private final String CONFIG_TYPE = "type";
	private final String CONFIG_TYPE_DEFAULT_LOCALE_PROVIDER = "defaultLocaleProvider";
	private final String CONFIG_TYPE_DEFAULT_LOCALE = "defaultLocale";
	private final String CONFIG_TYPE_DEFAULT_ACCESS_CONTROL_PROVIDER = "defaultAccessControlProvider";
	private final String CONFIG_TYPE_DEFAULT_ACCESS = "defaultAccess";
	private final String CONFIG_TYPE_DEFAULT_STRINGS_PATH = "defaultStringsPath";
	private final String CONFIG_TYPE_DEFAULT_SENDING_METHOD = "defaultSendingMethod";
	private final String CONFIG_TYPE_BASE_PATH = "basePath";
	private final String CONFIG_VALUE = "value";

	private final String VIEW = "view";
	private final String VIEW_URI = "uri";
	private final String VIEW_PAGE = "page";

	private final String ACTION = "action";
	private final String ACTION_URI = "uri";
	private final String ACTION_PAGE = "page";
	private final String ACTION_ACTION = "action";

	private final String EXCEPTION = "exception";
	private final String EXCEPTION_URI = "uri";
	private final String EXCEPTION_PAGE = "page";
	private final String EXCEPTION_EXCEPTION = "class";

	private final String COMMON_LOCALE = "locale";
	private final String COMMON_LOCALE_PROVIDER = "localeProvider";
	private final String COMMON_ACCESS = "access";
	private final String COMMON_ACCESS_CONTROL = "accessControl";
	private final String COMMON_STRINGS_PATH = "stringsPath";
	private final String COMMON_STRING = "string";

	@Override
	public void loadConfig(File file) {
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document doc;
			doc = db.parse(file);
			Element docEle = doc.getDocumentElement();

			// Config tags
			NodeList configs = docEle.getElementsByTagName(CONFIG);
			Mapping.Builder.Configuration configuration = addConfigs(configs);

			// View tags
			NodeList views = docEle.getElementsByTagName(VIEW);
			addViews(configuration, views);

			// Action tags
			NodeList actions = docEle.getElementsByTagName(ACTION);
			addActions(configuration, actions);

			// Action tags
			NodeList exceptions = docEle.getElementsByTagName(EXCEPTION);
			addExceptions(configuration, exceptions);
		} catch (InvalidMappingException e) {
			e.printStackTrace();
		} catch (DuplicateMappingException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Mapping.Builder.Configuration addConfigs(NodeList configs) {
		Mapping.Builder.Configuration configuration = Jacket.newConfiguration();
		if (configs != null && configs.getLength() > 0) {
			for (int i = 0; i < configs.getLength(); i++) {
				Node node = configs.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					String type = e.getAttribute(CONFIG_TYPE);
					String value = e.getAttribute(CONFIG_VALUE);
					addConfig(configuration, type, value);
				}
			}
		}
		return configuration;
	}

	private Mapping.Builder.Configuration addConfig(
			Mapping.Builder.Configuration configuration, String type,
			String value) {

		switch (type) {
		case CONFIG_TYPE_DEFAULT_LOCALE_PROVIDER:
			try {
				configuration.setDefaultLocaleProvider(Class.forName(value));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case CONFIG_TYPE_DEFAULT_LOCALE:
			configuration.setDefaultLocale(Locale.forLanguageTag(value));
			break;
		case CONFIG_TYPE_DEFAULT_ACCESS_CONTROL_PROVIDER:
			try {
				configuration.setDefaultAccessControlProvider(Class
						.forName(value));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			break;
		case CONFIG_TYPE_DEFAULT_ACCESS:
			configuration.setDefaultAccess(value);
			break;
		case CONFIG_TYPE_DEFAULT_STRINGS_PATH:
			configuration.setDefaultStringsPath(value);
			break;
		case CONFIG_TYPE_DEFAULT_SENDING_METHOD:
			if (value.equals(SendingMethod.SEND_REDIRECT.toString())) {
				configuration
						.setDefaultSendingMethod(SendingMethod.SEND_REDIRECT);
			} else if (value.equals(SendingMethod.FORWARD.toString())) {
				configuration.setDefaultSendingMethod(SendingMethod.FORWARD);
			}
			break;
		case CONFIG_TYPE_BASE_PATH:
			configuration.setBasePath(value);
			break;
		default:
			break;
		}
		return configuration;
	}

	private void addViews(Mapping.Builder.Configuration config, NodeList views)
			throws InvalidMappingException, DuplicateMappingException {
		if (views != null && views.getLength() > 0) {
			for (int i = 0; i < views.getLength(); i++) {
				Node node = views.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Mapping.Builder builder = Jacket.newBuilder(config);
					String uri = e.getAttribute(VIEW_URI);
					String page = e.getAttribute(VIEW_PAGE);
					ViewBuilder viewBuilder = builder.view(uri, page);
					setCommons(viewBuilder, e);
					viewBuilder.build().add();
				}
			}
		}
	}

	private void addActions(Mapping.Builder.Configuration config,
			NodeList actions) throws InvalidMappingException,
			DuplicateMappingException {
		if (actions != null && actions.getLength() > 0) {
			for (int i = 0; i < actions.getLength(); i++) {
				Node node = actions.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Mapping.Builder builder = Jacket.newBuilder(config);
					String action = e.getAttribute(ACTION_ACTION);
					action = action.isEmpty() ? null : action;
					String uri = e.getAttribute(ACTION_URI);
					uri = uri.isEmpty() ? null : uri;
					String page = e.getAttribute(ACTION_PAGE);
					page = page.isEmpty() ? null : page;
					ActionBuilder actionBuilder = builder.action(action, uri,
							page);
					setCommons(actionBuilder, e);
					actionBuilder.build().add();
				}
			}
		}
	}

	@SuppressWarnings("rawtypes")
	private void addExceptions(Mapping.Builder.Configuration config,
			NodeList exceptions) throws InvalidMappingException,
			DuplicateMappingException {
		if (exceptions != null && exceptions.getLength() > 0) {
			for (int i = 0; i < exceptions.getLength(); i++) {
				Node node = exceptions.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element e = (Element) node;
					Mapping.Builder builder = Jacket.newBuilder(config);
					Class exception = null;
					try {
						exception = Class.forName(e
								.getAttribute(EXCEPTION_EXCEPTION));
					} catch (ClassNotFoundException e1) {
						e1.printStackTrace();
					}
					String uri = e.getAttribute(EXCEPTION_URI);
					uri = uri.isEmpty() ? null : uri;
					String page = e.getAttribute(EXCEPTION_PAGE);
					page = page.isEmpty() ? null : page;
					ExceptionBuilder exceptionBuilder = builder.exception(
							exception, uri, page);
					setCommons(exceptionBuilder, e);
					exceptionBuilder.build().add();
				}
			}
		}
	}

	private CommonBuilder setCommons(CommonBuilder builder, Element e) {
		setLocale(builder, e.getAttribute(COMMON_LOCALE));
		setLocaleProvider(builder, e.getAttribute(COMMON_LOCALE_PROVIDER));
		setAccess(builder, e.getAttribute(COMMON_ACCESS));
		setAccessControl(builder, e.getAttribute(COMMON_ACCESS_CONTROL));
		setStringsPath(builder, e.getAttribute(COMMON_STRINGS_PATH));
		setString(builder, e.getAttribute(COMMON_STRING));
		return builder;
	}

	private CommonBuilder setLocale(CommonBuilder builder, String locale) {
		if (locale != null && !locale.isEmpty()) {
			builder.locale(Locale.forLanguageTag(locale));
		}
		return builder;
	}

	private CommonBuilder setLocaleProvider(CommonBuilder builder,
			String localeProvider) {
		if (localeProvider != null && !localeProvider.isEmpty()) {
			try {
				builder.locale(Class.forName(localeProvider));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return builder;
	}

	private CommonBuilder setAccess(CommonBuilder builder, String access) {
		if (access != null && !access.isEmpty()) {
			builder.access(access);
		}
		return builder;
	}

	private CommonBuilder setAccessControl(CommonBuilder builder,
			String accessControl) {
		if (accessControl != null && !accessControl.isEmpty()) {
			try {
				builder.accessControl(Class.forName(accessControl));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return builder;
	}

	private CommonBuilder setStringsPath(CommonBuilder builder,
			String stringsPath) {
		if (stringsPath != null && !stringsPath.isEmpty()) {
			builder.stringsPath(stringsPath);
		}
		return builder;
	}

	private CommonBuilder setString(CommonBuilder builder, String string) {
		if (string != null && !string.isEmpty()) {
			builder.string(string);
		}
		return builder;
	}

}
