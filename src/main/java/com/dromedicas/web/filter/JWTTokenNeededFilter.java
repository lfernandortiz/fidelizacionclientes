package com.dromedicas.web.filter;


import java.io.IOException;

import javax.annotation.Priority;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.ext.Provider;
import com.dromedicas.web.filter.JWTTokenNeeded;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {

	@Override
	public void filter(ContainerRequestContext arg0) throws IOException {
		// TODO Auto-generated method stub
		
	}

}
