package org.ssglobal.training.codes.service;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.Position;
import org.ssglobal.training.codes.repository.PositionRepo;

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

@Path("/position")
public class PositionService {
	
	private PositionRepo posRepo = new PositionRepo();
	
	@POST
	@Secured
	@Path("/insert")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Position createPosition(Position pos) {
		try {
			posRepo.insertPosition(pos.getPositionName());
			return pos;
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
	public Position updateDepartment(Position pos) {
		try {
			posRepo.updatePosition(pos);
			return pos;
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
	public GenericEntity<List<Position>> getallPosition() {
		List<Position> dep = new ArrayList<>();
		GenericEntity<List<Position>> listPos = null;
		try {
			dep = posRepo.selectAllPosition();
			listPos = new GenericEntity<>(dep) {};
			return listPos;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Position getPositionById(@PathParam("id") Integer id) {
		try {
			Position dep = posRepo.getPositionById(id);
			return dep;
		}catch(Exception e) {
			e.getMessage();
		}
		return null;
	}

}
