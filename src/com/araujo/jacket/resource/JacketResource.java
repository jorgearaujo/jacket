package com.araujo.jacket.resource;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.araujo.jacket.mapping.Mapping;
import com.araujo.jacket.util.JacketUtil;

@Path("/")
public class JacketResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		Response responseObject = null;
		Mapping mapping = JacketUtil.getViewMapping(request);
		if (mapping == null) {
			mapping = JacketUtil.getActionMapping(request);
		}
		responseObject = JacketUtil.createResponse(request, response, mapping);
		return responseObject;
	}

	@Path("{path: [a-zA-Z0-9_/.]+}")
	@GET
	@Produces(MediaType.TEXT_HTML)
	public Response getNoRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return getRoot(request, response);
	}

	@POST
	@Produces(MediaType.TEXT_HTML)
	public Response postRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return JacketUtil.handleActionMapping(request, response);
	}

	@Path("{path: [a-zA-Z0-9_/.]+}")
	@POST
	@Produces(MediaType.TEXT_HTML)
	public Response postNoRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return postRoot(request, response);
	}

	@PUT
	@Produces(MediaType.TEXT_HTML)
	public Response putRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return JacketUtil.handleActionMapping(request, response);
	}

	@Path("{path: [a-zA-Z0-9_/.]+}")
	@PUT
	@Produces(MediaType.TEXT_HTML)
	public Response putNoRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return postRoot(request, response);
	}

	@DELETE
	@Produces(MediaType.TEXT_HTML)
	public Response deleteRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return JacketUtil.handleActionMapping(request, response);
	}

	@Path("{path: [a-zA-Z0-9_/.]+}")
	@DELETE
	@Produces(MediaType.TEXT_HTML)
	public Response deleteNoRoot(@Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		return postRoot(request, response);
	}

	

	

}