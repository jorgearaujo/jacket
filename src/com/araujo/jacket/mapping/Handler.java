package com.araujo.jacket.mapping;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.NotFoundException;

import com.araujo.jacket.exception.DuplicateMappingException;
import com.araujo.jacket.exception.MappingNotFoundException;
import com.araujo.jacket.message.Messages;
import com.araujo.jacket.message.Printer;
import com.araujo.jacket.message.Severity;
import com.araujo.jacket.provider.AccessControlProvider;

@SuppressWarnings("rawtypes")
class Handler {
	static List<Mapping> mappingList;

	public List<Mapping> getMappingList() {
		if (mappingList == null) {
			mappingList = new ArrayList<Mapping>();
		}
		return mappingList;
	}

	public Mapping getMappingByUri(HttpServletRequest request)
			throws MappingNotFoundException {
		Mapping returnMapping = null;

		for (Mapping mapping : getMappingList()) {
			if (mapping.getUri() != null && mapping.getAction() == null) {

				String requestUri = request.getRequestURI();
				String mappingUri = request.getServletContext()
						.getContextPath() + mapping.getUri();

				if (matchUris(mappingUri, requestUri)) {
					returnMapping = mapping;
					break;
				}
			}
		}
		if (returnMapping == null) {
			throw new MappingNotFoundException();
		}
		return returnMapping;
	}

	public Mapping getMappingByAction(HttpServletRequest request)
			throws NotFoundException {
		Mapping returnMapping = null;

		for (Mapping mapping : getMappingList()) {
			if (mapping.getAction() != null) {

				String requestAction = request.getRequestURI();
				String mappingAction = request.getServletContext()
						.getContextPath() + mapping.getAction();

				if (matchUris(mappingAction, requestAction)) {
					returnMapping = mapping;
					break;
				}
			}
		}
		if (returnMapping == null) {
			throw new NotFoundException();
		}
		return returnMapping;
	}

	private static Boolean matchUris(String mappingUri, String requestUri) {
		String regexMappingUri1 = mappingUri.replace("*", "[ a-zA-Z0-9_/.]+");
		Pattern pattern = Pattern.compile(regexMappingUri1);
		Matcher matcher = pattern.matcher(requestUri);

		String regexMappingUri2 = mappingUri.replace("[ a-zA-Z0-9_/.]+", "");
		Pattern pattern2 = Pattern.compile(regexMappingUri2);
		Matcher matcher2 = pattern2.matcher(requestUri);

		return (matcher.matches() || matcher2.matches());
	}

	public Mapping getMappingByException(Class clazz) throws NotFoundException {
		Mapping returnMapping = null;
		for (Mapping m : getMappingList()) {
			if (m.getException() != null && m.getException().equals(clazz)) {
				returnMapping = m;
			}
		}
		if (returnMapping == null) {
			throw new NotFoundException();
		}
		return returnMapping;
	}

	public Boolean checkValidAccess(HttpServletRequest request, Mapping mapping)
			throws Exception {
		Boolean bool = Boolean.TRUE;
		if (mapping.getAccessControl() != null) {
			bool = ((AccessControlProvider) mapping.getAccessControl()
					.newInstance()).checkAccess(request, mapping.getAccess());
		}
		return bool;
	}

	public Mapping addMapping(Mapping m) {
		for (Mapping mapping : getMappingList()) {
			try {
				checkDuplicate(mapping, m);
			} catch (DuplicateMappingException e) {
				// Do nothing
			}
		}
		getMappingList().add(m);
		return m;
	}

	private static void checkDuplicate(Mapping m1, Mapping m2)
			throws DuplicateMappingException {
		if (m1.getUri() != null && m2.getUri() != null
				&& m1.getUri().equals(m2.getUri()) && m1.getPage() != null
				&& m2.getPage() != null) {
			Printer.print(Severity.ERR, Messages.DUPLICATE_URI, m2.getUri());
			throw new DuplicateMappingException();
		}
		if (m1.getException() != null && m2.getException() != null
				&& m1.getException().equals(m2.getException())) {
			Printer.print(Severity.ERR, Messages.DUPLICATE_EXCEPTION, m2
					.getException().toString());
			throw new DuplicateMappingException();
		}
		if (m1.getAction() != null && m2.getAction() != null
				&& m1.getAction().equals(m2.getAction())) {
			Printer.print(Severity.ERR, Messages.DUPLICATE_ACTION,
					m2.getAction());
			throw new DuplicateMappingException();
		}
	}
}