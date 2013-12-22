package com.araujo.jacket.provider;

import javax.servlet.http.HttpServletRequest;

public interface AccessControlProvider {

	Boolean checkAccess(HttpServletRequest request, String access)
			throws Exception;

}
