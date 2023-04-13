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
			String email, String mobileNumber, 
			String password, String userType, String firstName, 
			String middleName, String lastName, String department, 
			LocalDate birthDate, String gender, String position) {
		User user = new User(null, email, mobileNumber, 
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
	
	public boolean updateUser(User currentUser) {
		EntityTransaction tx = null;
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			User user = em.find(User.class, currentUser.getEmployeeId());
			if (user != null) {
				user.setEmail(currentUser.getEmail());
				user.setMobileNumber(currentUser.getMobileNumber());
				user.setPassword(currentUser.getPassword());
				user.setUserType(currentUser.getUserType());
				user.setFirstName(currentUser.getFirstName());
				user.setMiddleName(currentUser.getMiddleName());
				user.setLastName(currentUser.getLastName());
				user.setDepartment(currentUser.getDepartment());
				user.setGender(currentUser.getGender());
				user.setPosition(currentUser.getPosition());
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
			e.getMessage();
		} finally {
			em.close();
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public List<User> selectAllUser() {
		String sql = "from User u";
		List<User> bookList = new ArrayList<>();
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, User.class);
			bookList = query.getResultList();
			return Collections.unmodifiableList(bookList);
		} catch (Exception e) {
			e.getMessage();
		}
		return null;
	}
	
	public User searchUserByEmailAndPass(String email, String password) {
		String sql = "from User u where u.email=:email and u.password=:password";
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, User.class);
			query.setParameter("email", email);
			query.setParameter("password", password);
			User user = (User) query.getSingleResult();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public User getUserById(Integer id) {
		String sql = "from User u where u.employeeId=:id";
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, User.class);
			query.setParameter("id", id);
			User user = (User) query.getSingleResult();
			return user;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
