package org.ssglobal.training.codes.cors;

import java.io.IOException;
import java.util.logging.Logger;

import org.ssglobal.training.codes.repository.UserTokenRepository;

import com.fasterxml.jackson.annotation.JacksonInject.Value;

import jakarta.annotation.Priority;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyCorsFilter implements ContainerResponseFilter {
	private Logger logger = Logger.getLogger(MyCorsFilter.class.getName());

	@Context
	
	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		UriInfo uriInfo = requestContext.getUriInfo();
		String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
		if (!uriInfo.getPath().contains("users/get/query")) {
			
			logger.info(authorizationHeader + "pat header *********************");
			if (authorizationHeader == null || !authorizationHeader.toString().startsWith("Bearer ")) {
				throw new NotAuthorizedException("Authorization header must be provided");
			}
			try {
				logger.info(authorizationHeader + " this is the token header pat");
				validateToken(authorizationHeader);

			} catch (Exception e) {
				requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
			}
		}
		
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8000");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}

	private void validateToken(String token) throws Exception {
		UserTokenRepository userTokenRepository = new UserTokenRepository();
		if (userTokenRepository.isUserTokenExists(token)) {
			return;
		} else {
			throw new Exception("token not valid");
		}
	}
}
