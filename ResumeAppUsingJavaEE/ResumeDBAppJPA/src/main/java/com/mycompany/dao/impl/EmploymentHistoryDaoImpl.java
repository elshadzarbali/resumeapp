package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.entity.*;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class EmploymentHistoryDaoImpl extends AbstractDAO implements EmploymentHistoryDaoInter {
    
    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        EntityManager em = createEM();
        try {
            String queryStr = "select eh from EmploymentHistory eh where eh.user.id = :userId";
            TypedQuery<EmploymentHistory> query = em.createQuery(queryStr, EmploymentHistory.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // if eh.id (PK) is null, this method insert eh into database instead of update
    @Override
    public boolean updateEmpHistory(EmploymentHistory eh) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(eh);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public boolean insertEmpHistory(EmploymentHistory eh) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(eh);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    @Override
    public boolean removeEmpHistory(int id) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            EmploymentHistory eh = entityManager.find(EmploymentHistory.class, id);
            transaction.begin();
            entityManager.remove(eh);
            transaction.commit();
            return true;
        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }
}
