package org.ssglobal.training.codes.repository;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.model.Department;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class DepartmentRepo {
	private EntityManagerFactory entityManagerFactory;
	
	public DepartmentRepo() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("user-persistence-unit");
	}
	
	public boolean insertDepartment(String departmentName) {
		Department dep = new Department(null, departmentName);
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();
			
			em.persist(dep);
			
			et.commit();
			return true;
		} catch (Exception e) {
			et.rollback();
			e.getMessage();
		} finally {
			em.close();
		}
		return false;
	}
	
	public boolean updateDepartment(Department currentDep) {
		EntityTransaction tx = null;
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Department dep = em.find(Department.class, currentDep.getDepartmentId());
			if (dep != null) {
				dep.setDepartmentName(currentDep.getDepartmentName());
			}
			tx.commit();
			em.close();
			return true;
		} catch (Exception e) {
			tx.rollback();
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean deleteDepartment(Integer id) {
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();
			
			Department dep = em.find(Department.class, id);
			em.remove(dep);
			
			et.commit();
			return true;
		} catch (Exception e) {
			et.rollback();
			e.getMessage();
		} finally {
			em.close();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<Department> selectAllDepartment() {
		String sql = "from Department d";
		List<Department> depList = new ArrayList<>();
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, Department.class);
			depList = query.getResultList();
			return depList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Department getDepartmentById(Integer id) {
		String sql = "from Department d where d.departmentId=:id";
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, Department.class);
			query.setParameter("id", id);
			Department dep = (Department) query.getSingleResult();
			return dep;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
}
