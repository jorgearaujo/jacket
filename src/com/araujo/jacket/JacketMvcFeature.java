package com.araujo.jacket;

import javax.ws.rs.core.Feature;
import javax.ws.rs.core.FeatureContext;

import org.glassfish.jersey.server.mvc.jsp.JspMvcFeature;

import com.araujo.jacket.resource.JacketResource;

public class JacketMvcFeature implements Feature {

    @Override
    public boolean configure(final FeatureContext context) {
        if (!context.getConfiguration().isRegistered(JspMvcFeature.class)) {
            context.register(JspMvcFeature.class);
        }
        context.register(JacketResource.class);
        return true;
    }
}