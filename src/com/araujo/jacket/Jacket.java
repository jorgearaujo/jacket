package com.araujo.jacket;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Response;

import com.araujo.jacket.factory.JacketFactory;
import com.araujo.jacket.mapping.Mapping;
import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;
import com.araujo.jacket.resource.JacketResource;
import com.araujo.jacket.servlet.config.MappingConfig;
import com.araujo.jacket.util.JacketUtil;
import com.araujo.jacket.view.View;

@SuppressWarnings("rawtypes")
public class Jacket extends JacketResource {

	public static final Mapping.Builder newBuilder(
			Mapping.Builder.Configuration config) {
		return JacketFactory.getMappingBuilder(config);
	}

	public static final Mapping.Builder newBuilder() {
		return JacketFactory.getMappingBuilder();
	}

	public static final Mapping.Builder.Configuration newConfiguration() {
		return JacketFactory.getMappingBuilderConfiguration();
	}

	public static final Mapping register(Mapping mapping) {
		return JacketFactory.getMappingHandler().addMapping(mapping);
	}

	public static final Response handleAction(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		return JacketUtil.handleActionMapping(request, response);
	}

	public static final View handleView(HttpServletRequest request,
			HttpServletResponse response) {
		View view = JacketUtil.getView(request,
				JacketUtil.getViewMapping(request));
		return view;
	}

	public static final void config(Class jacketConfig) {
		try {
			((MappingConfig) jacketConfig.newInstance()).configure();
		} catch (InstantiationException e) {
			Printer.print(Severity.ERR, Messages.JACKET_CONFIG_CANNOT_INSTANTIATE, jacketConfig.getName());
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			Printer.print(Severity.ERR, Messages.JACKET_CONFIG_CANNOT_INSTANTIATE, jacketConfig.getName());
			e.printStackTrace();
		}
	}

}