package org.ssglobal.training.codes.service;

import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.crypto.KeyGenerator;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.repository.UserRepository;
import org.ssglobal.training.codes.repository.UserTokenRepository;


import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/users")
public class UserService {
	@SuppressWarnings("unused")
	private static Logger logger = Logger.getLogger(UserService.class.getName());
	private UserRepository userRepository = new UserRepository();
	private UserTokenRepository userTokenRepository = new UserTokenRepository();
	
	@POST
	@Secured
	@Path("/insert")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response createUser(User user) {
		try {
			userRepository.insertUser(user.getEmail(), user.getMobileNumber(),
									  user.getPassword(), user.getUserType(), user.getFirstName(),
									  user.getMiddleName(), user.getLastName(), user.getDepartmentId(),
									  user.getBirthDate(), user.getGender(), user.getPositionId());
			return Response.ok(user).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@PUT
	@Secured
	@Path("/update")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response updateUser(User user) {
		try {
			userRepository.updateUser(user);
			return Response.ok(user).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.status(Status.BAD_REQUEST).build();
	}
	
	@DELETE
	@Secured
	@Path("/delete/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	@Consumes(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response deleteUser(@PathParam("id") Integer id) {
		try {
			Boolean result = userRepository.deleteUser(id);
			if(result) {
				return Response.ok().build();
			} else {
				return Response.status(404, "invalid employee ID").build();
			}
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.serverError().build();
	}
	
	@GET
	@Secured
	@Path("/get")
	@Produces(value = {MediaType.APPLICATION_JSON})
	public Response getAllUsers() {
		List<User> users = new ArrayList<>();
		GenericEntity<List<User>> listUsers = null;
		try {
			users = userRepository.selectAllUser();
			listUsers = new GenericEntity<>(users) {};
			return Response.ok(listUsers).build();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response getUserById(@PathParam("id") Integer id) {
		try {
			User user = userRepository.getUserById(id);
			return Response.ok(user).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Path("/get/query")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response authenticate(@QueryParam("username") String username,
						  @QueryParam("password") String password) {
		List<Object> userIdAndToken = new ArrayList<>();
		try {
			User user = userRepository.searchUserByEmailAndPass(username, password);
			String token = generateToken(user.getEmployeeId(), username);
			if (user != null) {
				userIdAndToken.add(token);
				userIdAndToken.add(user.getEmployeeId());
				userIdAndToken.add(user.getEmail().toString());
				userIdAndToken.add(user.getUserType());
				return Response.ok( new GenericEntity<>(userIdAndToken) {}).build();
			}
		} catch(Exception e) {
			logger.severe(e.getMessage());
			e.getMessage();
		}
		return Response.status(Response.Status.UNAUTHORIZED).build();
	}	
	
	private String generateToken(Integer userId, String username) {
		KeyGenerator keyGenerator = null;
		try {
			keyGenerator = KeyGenerator.getInstance("HmacSHA256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		Key key = keyGenerator.generateKey();
		String jwtToken = Jwts.builder()
							  .setIssuedAt(new Date())
							  .setExpiration(Date.from(LocalDateTime.now().plusMinutes(10L).atZone(ZoneId.systemDefault()).toInstant()))
							  .signWith(key, SignatureAlgorithm.HS256)
							  .compact();
		
		if (userTokenRepository.isUserTokenIdExists(userId)) {
			userTokenRepository.updateUserToken(userId, jwtToken);
			return jwtToken;
		}
		userTokenRepository.createToken(userId, jwtToken);
		return jwtToken;
	}
	
}
