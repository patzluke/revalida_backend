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
	
	public boolean updateUser(User newUser, Integer id) {
		EntityTransaction tx = null;
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			User user = em.find(User.class, id);
			if (user != null) {
				user.setEmail(newUser.getEmail());
				user.setMobileNumber(newUser.getMobileNumber());
				user.setPassword(newUser.getPassword());
				user.setUserType(newUser.getUserType());
				user.setFirstName(newUser.getFirstName());
				user.setMiddleName(newUser.getMiddleName());
				user.setLastName(newUser.getLastName());
				user.setDepartment(newUser.getDepartment());
				user.setGender(newUser.getGender());
				user.setPosition(newUser.getPosition());
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
}
