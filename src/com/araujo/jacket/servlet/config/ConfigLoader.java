package com.araujo.jacket.servlet.config;

public interface ConfigLoader<T> {

	void loadConfig(T config);
	
}
