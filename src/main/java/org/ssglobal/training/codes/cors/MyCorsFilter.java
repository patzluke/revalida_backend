package org.ssglobal.training.codes.cors;

import java.io.IOException;
import java.util.logging.Logger;

import org.ssglobal.training.codes.repository.UserTokenRepository;
import org.ssglobal.training.codes.service.UserService;

import jakarta.annotation.Priority;
import jakarta.ws.rs.NotAuthorizedException;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class MyCorsFilter implements ContainerResponseFilter, ContainerRequestFilter {
	private static Logger logger = Logger.getLogger(UserService.class.getName());

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		try {
			UriInfo uriInfo = requestContext.getUriInfo();
			String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
			if (!uriInfo.getPath().contains("users/get/query")) {
				if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
					logger.info("error occured here PATTTTT int the first if");
					throw new NotAuthorizedException("Authorization header must be provided");
				}
				String token = authorizationHeader.substring("Bearer".length()).trim();
				
				if (!validateToken(token)) {
					logger.info("error occured here PATTTTT int the second if");
					requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext)
			throws IOException {
		responseContext.getHeaders().add("Access-Control-Allow-Origin", "http://localhost:8000");
		responseContext.getHeaders().add("Access-Control-Allow-Credentials", "true");
		responseContext.getHeaders().add("Access-Control-Allow-Headers", "origin, content-type, accept, authorization");
		responseContext.getHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
	}

	private boolean validateToken(String token) {
		UserTokenRepository userTokenRepository = new UserTokenRepository();
		if (userTokenRepository.isUserTokenExists(token)) {
			return true;
		}
		return false;
	}
}
