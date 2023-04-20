package org.ssglobal.training.codes.service;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.cors.Secured;
import org.ssglobal.training.codes.model.Department;
import org.ssglobal.training.codes.repository.DepartmentRepository;

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

@Path("/department")
public class DepartmentService {

	private DepartmentRepository departmentRepo = new DepartmentRepository();
	
	@POST
	@Secured
	@Path("/insert")
	@Produces(value= {MediaType.APPLICATION_JSON})
	@Consumes(value = {MediaType.APPLICATION_JSON})
	public Response createDepartment(Department dep) {
		try {
			departmentRepo.insertDepartment(dep.getDepartmentName());
			return Response.ok(dep).build();
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
	public Response updateDepartment(Department dep) {
		try {
			departmentRepo.updateDepartment(dep);
			return Response.ok(dep).build();
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
	public Response deleteDepartment(@PathParam("id") Integer id) {
		try {
			Boolean result = departmentRepo.deleteDepartment(id);
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
	public Response getallDepartment() {
		List<Department> dep = new ArrayList<>();
		GenericEntity<List<Department>> listDeo = null;
		try {
			dep = departmentRepo.selectAllDepartment();
			listDeo = new GenericEntity<>(dep) {};
			return Response.ok(listDeo).build();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return Response.noContent().build();
	}
	
	@GET
	@Secured
	@Path("/get/{id}")
	@Produces(value = {MediaType.APPLICATION_JSON, MediaType.TEXT_PLAIN})
	public Response getDepartmentById(@PathParam("id") Integer id) {
		try {
			Department dep = departmentRepo.getDepartmentById(id);
			return Response.ok(dep).build();
		}catch(Exception e) {
			e.getMessage();
		}
		return Response.noContent().build();
	}
		
}
