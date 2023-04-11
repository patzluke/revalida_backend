package org.ssglobal.training.codes.repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.ssglobal.training.codes.model.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class UserRepository {
	private EntityManagerFactory entityManagerFactory;
	
	public UserRepository() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("user-persistence-unit");
	}
	
	public boolean insertUser(
			Integer employeeId, String email, String mobileNumber, 
			String password, String userType, String firstName, 
			String middleName, String lastName, String department, 
			LocalDate birthDate, String gender, String position) {
		User user = new User(employeeId, email, mobileNumber, 
							 password, userType, firstName, 
							 middleName, lastName, department, 
							 birthDate, gender, position);

		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();
			
			em.persist(user);
			
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
	
	public boolean deleteUser(Integer id) {
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();
			
			User user = em.find(User.class, id);
			em.remove(user);
			
			et.commit();
			return true;
		} catch (Exception e) {
			et.rollback();
			e.printStackTrace();
		} finally {
			em.close();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> selectAllUser() {
		String sql = "from User s";
		List<User> bookList = new ArrayList<>();
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, User.class);
			bookList = query.getResultList();
			return Collections.unmodifiableList(bookList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
