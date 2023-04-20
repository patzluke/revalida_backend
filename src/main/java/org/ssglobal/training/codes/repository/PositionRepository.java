package org.ssglobal.training.codes.repository;

import java.util.ArrayList;
import java.util.List;

import org.ssglobal.training.codes.model.Position;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;

public class PositionRepository {
	private EntityManagerFactory entityManagerFactory;
	
	public PositionRepository() {
		this.entityManagerFactory = Persistence.createEntityManagerFactory("user-persistence-unit");
	}
	
	public boolean insertPosition(String positionName) {
		Position pos = new Position(null, positionName);
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();
			
			em.persist(pos);
			
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
	
	public boolean updatePosition(Position currentpos) {
		EntityTransaction tx = null;
		try {
			EntityManager em = entityManagerFactory.createEntityManager();
			tx = em.getTransaction();
			tx.begin();

			Position dep = em.find(Position.class, currentpos.getPositionId());
			if (dep != null) {
				dep.setPositionName(currentpos.getPositionName());
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
	
	public boolean deletePosition(Integer id) {
		EntityManager em = null;
		EntityTransaction et = null;
		try {
			em = this.entityManagerFactory.createEntityManager();
			et = em.getTransaction();
			et.begin();
			
			Position pos = em.find(Position.class, id);
			em.remove(pos);
			
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
	public List<Position> selectAllPosition() {
		String sql = "from Position p";
		List<Position> postList = new ArrayList<>();
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, Position.class);
			postList = query.getResultList();
			return postList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Position getPositionById(Integer id) {
		String sql = "from Position p where p.positionId=:id";
		try {
			EntityManager em = this.entityManagerFactory.createEntityManager();
			Query query = em.createQuery(sql, Position.class);
			query.setParameter("id", id);
			Position pos = (Position) query.getSingleResult();
			return pos;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
