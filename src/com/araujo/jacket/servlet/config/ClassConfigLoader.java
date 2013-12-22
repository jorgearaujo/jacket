package com.araujo.jacket.servlet.config;

public class ClassConfigLoader implements ConfigLoader<Class<MappingConfig>> {

	@Override
	public void loadConfig(Class<MappingConfig> clazz) {
		MappingConfig config = null;
		try {
			config = (MappingConfig) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		if (config != null) {
			config.configure();
		}
	}

}
