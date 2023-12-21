package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.UserSkill;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {
    
    @Override
    public List<UserSkill> getAllUserSkillByUserId(int userId) {
        EntityManager em = createEM();
        try {
            String queryStr = "select us from UserSkill us where us.user.id = :userId";
            TypedQuery<UserSkill> query = em.createQuery(queryStr, UserSkill.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // if userSkill.id (PK) is null, this method insert userSkill into database instead of update
    @Override
    public boolean updateUserSkill(UserSkill userSkill) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(userSkill);
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
    public boolean insertUserSkill(UserSkill userSkill) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(userSkill);
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
    public boolean removeUserSkill(int id) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            UserSkill userSkill = entityManager.find(UserSkill.class, id);
            transaction.begin();
            entityManager.remove(userSkill);
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
