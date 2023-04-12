package org.ssglobal.training.codes.service;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.model.User;
import org.ssglobal.training.codes.repository.UserRepository;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;

@Path("/users")
public class UserService {
	private UserRepository userRepository = new UserRepository();
	
	@POST
	@Path("/insert")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public User insertSurveyJson(User user) {
		try {
			userRepository.insertUser(user.getEmail(), user.getMobileNumber(),
									  user.getPassword(), user.getUserType(), user.getFirstName(),
									  user.getMiddleName(), user.getLastName(), user.getDepartment(),
									  user.getBirthDate(), user.getGender(), user.getPosition());
			return user;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	@GET
	@Path("/get")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public GenericEntity<List<User>> getAllSurveys() {
		List<User> users = new ArrayList<>();
		GenericEntity<List<User>> listUsers = null;
		try {
			users = userRepository.selectAllUser();
			listUsers = new GenericEntity<>(users) {};
			return listUsers;
		}catch(Exception e) {
			e.getMessage();
		}
		listUsers = new GenericEntity<>(null) {};
		return listUsers;
	}
	
	@GET
	@Path("/get/query")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public User getSurvey(@QueryParam("username") String username,
						  @QueryParam("password") String password) {
		try {
			User user = userRepository.searchUserByEmailAndPass(username, password);
			return user;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}
}
