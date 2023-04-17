package org.ssglobal.training.codes.service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.repository.UserRepository;
import org.ssglobal.training.codes.repository.UserTokenRepository;

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
	public User createUser(User user) {
		try {
			userRepository.insertUser(user.getEmail(), user.getMobileNumber(),
									  user.getPassword(), user.getUserType(), user.getFirstName(),
									  user.getMiddleName(), user.getLastName(), user.getDepartmentId(),
									  user.getBirthDate(), user.getGender(), user.getPositionId());
			return user;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	@PUT
	@Secured
	@Path("/update")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public User updateUser(User user) {
		try {
			userRepository.updateUser(user);
			return user;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
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
				return Response.status(400, "invalid employee ID").build();
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
	public GenericEntity<List<User>> getAllUsers() {
		List<User> users = new ArrayList<>();
		GenericEntity<List<User>> listUsers = null;
		try {
			users = userRepository.selectAllUser();
			listUsers = new GenericEntity<>(users) {};
			return listUsers;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public User getUserById(@PathParam("id") Integer id) {
		try {
			User user = userRepository.getUserById(id);
			return user;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	@GET
	@Path("/get/query")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public GenericEntity<List<Object>> authenticate(@QueryParam("username") String username,
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
				return new GenericEntity<>(userIdAndToken) {};
			}
		} catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	
	private String generateToken(Integer userId, String username) {
		SecureRandom random = new SecureRandom();
		username = username.split("@")[0];
		String token = "%s&d%d%d%d%d%d"
				.formatted(username, userId, 
				random.nextLong(10), random.nextLong(10), random.nextLong(10),
				random.nextLong(10), random.nextLong(10));
		if (userTokenRepository.isUserTokenIdExists(userId)) {
			userTokenRepository.updateUserToken(userId, token);
			return token;
		}
		userTokenRepository.createToken(userId, token);
		return token;
	}
}
