package com.araujo.jacket.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;
import com.araujo.jacket.servlet.config.Config;
import com.araujo.jacket.servlet.config.ConfigLoader;
import com.araujo.jacket.servlet.config.ConfigLoaderFactory;
import com.araujo.jacket.servlet.config.MappingConfig;

@SuppressWarnings("rawtypes")
public class ServletContainer implements Filter {

	private static final String CONFIG_XML = "com.araujo.jacket.mapping.config.xml";
	private static final String CONFIG_CLASS = "com.araujo.jacket.mapping.config.class";

	@SuppressWarnings("unchecked")
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String xmlFileName = filterConfig.getInitParameter(CONFIG_XML);
		File xmlFile = new File(filterConfig.getServletContext().getRealPath(
				"/")
				+ "/" + xmlFileName);
		if (xmlFile.exists() && xmlFile.canRead()) {
			ConfigLoader<File> loader = ConfigLoaderFactory
					.getInstance(Config.XML);
			loader.loadConfig(xmlFile);
		}

		String className = filterConfig.getInitParameter(CONFIG_CLASS);
		Class clazz = null;
		try {
			if (className != null && !className.isEmpty()) {
				clazz = Class.forName(className);
			}
		} catch (ClassNotFoundException e) {
			Printer.print(Severity.ERR, Messages.XML_CONFIG_FILE_NOT_EXIST,
					className);
			e.printStackTrace();
		}
		if (clazz != null && MappingConfig.class.isAssignableFrom(clazz)) {
			ConfigLoader<Class> loader = ConfigLoaderFactory
					.getInstance(Config.CLASS);
			loader.loadConfig(clazz);
		}
	}

	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		chain.doFilter(request, response);
	}

}
