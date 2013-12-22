package com.araujo.jacket.servlet.config;

import java.io.File;


public class ConfigLoaderFactory {

	private static ConfigLoader<File> xmlLoader;
	private static ConfigLoader<Class<MappingConfig>> classLoader;

	@SuppressWarnings("rawtypes")
	public static ConfigLoader getInstance(Config type) {
		ConfigLoader loader = null;
		if (type.equals(Config.XML)) {
			if (xmlLoader == null) {
				xmlLoader = new XmlConfigLoader();
			}
			loader = xmlLoader;
		} else if (type.equals(Config.CLASS)) {
			if (classLoader == null) {
				classLoader = new ClassConfigLoader();
			}
			loader = classLoader;
		}
		return loader;
	}
}
