package org.ssglobal.training.codes.service;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.Position;
import org.ssglobal.training.codes.repository.PositionRepository;

import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.GenericEntity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

@Path("/position")
public class PositionService {
	
	private PositionRepository posRepo = new PositionRepository();
	
	@POST
	@Secured
	@Path("/insert")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response createPosition(Position pos) {
		try {
			posRepo.insertPosition(pos.getPositionName());
			return Response.ok(pos).build();
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
	public Response updateDepartment(Position pos) {
		try {
			posRepo.updatePosition(pos);
			return Response.ok(pos).build();
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
	public Response DeletePosition(@PathParam("id") Integer id) {
		try {
			Boolean result = posRepo.deletePosition(id);
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
	public Response getallPosition() {
		List<Position> dep = new ArrayList<>();
		GenericEntity<List<Position>> listPos = null;
		try {
			dep = posRepo.selectAllPosition();
			listPos = new GenericEntity<>(dep) {};
			return Response.ok(listPos).build();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response getPositionById(@PathParam("id") Integer id) {
		try {
			Position dep = posRepo.getPositionById(id);
			return Response.ok(dep).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.noContent().build();
	}

}
