package com.araujo.jacket.util;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.server.mvc.Viewable;

import com.araujo.jacket.factory.JacketFactory;
import com.araujo.jacket.mapping.Mapping;
import com.araujo.jacket.mapping.SendingMethod;
import com.araujo.jacket.view.View;

public final class JacketUtil {

	public static Response handleActionMapping(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		Response responseObject = null;
		Mapping mapping = getActionMapping(request);
		responseObject = createResponse(request, response, mapping);
		return responseObject;
	}

	public static Response createResponse(HttpServletRequest request,
			HttpServletResponse response, Mapping mapping) throws IOException {
		Response responseObject = null;
		if (mapping != null
				&& mapping.getSendingMethod() != null
				&& mapping.getSendingMethod().equals(
						SendingMethod.SEND_REDIRECT)) {
			 response.sendRedirect(request.getServletContext().getContextPath()
			 + mapping.getUri());
		} else if (mapping != null
				&& (mapping.getSendingMethod() == null || (mapping
						.getSendingMethod() != null && mapping
						.getSendingMethod().equals(SendingMethod.FORWARD)))) {
			View view = getView(request, mapping);
			responseObject = getResponse(view);
		} else if (mapping == null) {
			responseObject = getResponseKo();
		}
		return responseObject;
	}

	public static Mapping getActionMapping(HttpServletRequest request) {
		Mapping mapping;
		try {
			mapping = JacketFactory.getMappingHandler().getMappingByAction(
					request);
			JacketFactory.getMappingHandler()
					.checkValidAccess(request, mapping);
		} catch (NotFoundException e1) {
			try {
				mapping = JacketFactory.getMappingHandler()
						.getMappingByException(NotFoundException.class);
			} catch (NotFoundException e) {
				mapping = null;
			}
		} catch (Exception e) {
			try {
				mapping = JacketFactory.getMappingHandler()
						.getMappingByException(e.getClass());
			} catch (NotFoundException e3) {
				try {
					mapping = JacketFactory.getMappingHandler()
							.getMappingByException(NotFoundException.class);
				} catch (NotFoundException e4) {
					mapping = null;
				}
			}
		}
		return mapping;
	}

	public static Mapping getViewMapping(HttpServletRequest request) {
		Mapping mapping;
		try {
			mapping = JacketFactory.getMappingHandler()
					.getMappingByUri(request);
			JacketFactory.getMappingHandler()
					.checkValidAccess(request, mapping);
		} catch (NotFoundException e1) {
			try {
				mapping = JacketFactory.getMappingHandler()
						.getMappingByException(NotFoundException.class);
			} catch (NotFoundException e) {
				mapping = null;
			}
		} catch (Exception e) {
			try {
				mapping = JacketFactory.getMappingHandler()
						.getMappingByException(e.getClass());
			} catch (NotFoundException e3) {
				try {
					mapping = JacketFactory.getMappingHandler()
							.getMappingByException(NotFoundException.class);
				} catch (NotFoundException e4) {
					mapping = null;
				}
			}
		}
		return mapping;
	}

	public static View getView(HttpServletRequest request, Mapping mapping)
			throws NotFoundException {
		View view = null;
		if (mapping != null) {
			view = JacketFactory.getViewFactory().makeView(request, mapping);
		} else {
			throw new NotFoundException();
		}
		return view;
	}

	private static Response getResponse(View view) {
		Response response;
		if (view == null) {
			response = Response.status(Status.NOT_FOUND).build();
		} else {
			response = Response.ok(
					new Viewable(view.getTemplateName(), view.getModel()))
					.build();
		}
		return response;
	}

	private static Response getResponseKo() {
		Response response = null;
		response = Response.status(404).build();
		return response;
	}

}
