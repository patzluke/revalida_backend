package org.ssglobal.training.codes.repository;

import org.ssglobal.training.codes.model.UserToken;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class UserTokenRepository {
	private EntityManagerFactory entityManagerFactory;

	public UserTokenRepository() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("user-persistence-unit");
	}

	public boolean createToken(Integer userId, String token) {
		UserToken userToken = new UserToken(userId, token);

		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();

			em.persist(userToken);

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
	
	public boolean updateUserToken(Integer userId, String newToken) {
		EntityTransaction tx = null;
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			UserToken userToken = em.find(UserToken.class, userId);
			if (userToken != null) {
				userToken.setToken(newToken);
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
	
	public boolean isUserTokenIdExists(Integer id) {
		String sql = "from UserToken u where u.userId=:id";
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, UserToken.class);
			query.setParameter("id", id);
			query.getSingleResult();
			return true;
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
	
	public boolean isUserTokenExists(String token) {
		String sql = "from UserToken u where u.token=:token";
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, UserToken.class);
			query.setParameter("token", token);
			query.getSingleResult();
			return true;
		} catch (Exception e) {
			e.getMessage();
		}
		return false;
	}
}
