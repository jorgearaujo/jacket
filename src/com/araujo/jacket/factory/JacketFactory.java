package com.araujo.jacket.factory;

import com.araujo.jacket.mapping.Mapping;
import com.araujo.jacket.message.Message;
import com.araujo.jacket.view.JacketViewFactory;
import com.araujo.jacket.view.ViewFactory;
import com.araujo.jacket.view.strings.JacketStringsHandler;
import com.araujo.jacket.view.strings.StringsHandler;

public final class JacketFactory {

	private static Mapping.Handler mappingHandler;
	private static ViewFactory viewFactory;
	private static StringsHandler stringsHandler;

	public static ViewFactory getViewFactory() {
		if (viewFactory == null) {
			viewFactory = new JacketViewFactory();
		}
		return viewFactory;
	}

	public static StringsHandler getStringsHandler() {
		if (stringsHandler == null) {
			stringsHandler = new JacketStringsHandler();
		}
		return stringsHandler;
	}

	public static Mapping.Handler getMappingHandler() {
		if (mappingHandler == null) {
			mappingHandler = new Mapping.Handler();
		}
		return mappingHandler;
	}

	public static Mapping.Builder.Configuration getMappingBuilderConfiguration() {
		return new Mapping.Builder.Configuration();
	}

	public static Mapping.Builder getMappingBuilder(
			Mapping.Builder.Configuration config) {
		return new Mapping.Builder(config);
	}

	public static Mapping.Builder getMappingBuilder() {
		return new Mapping.Builder();
	}

	public static Message.Builder getMessageBuilder(String base) {
		return new Message.Builder(base);
	}
}
