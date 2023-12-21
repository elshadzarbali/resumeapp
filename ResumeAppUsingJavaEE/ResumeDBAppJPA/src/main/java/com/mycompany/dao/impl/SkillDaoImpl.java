package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.entity.Skill;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {
    
    @Override
    public List<Skill> getAll() {
        EntityManager em = createEM();

        try {
            TypedQuery<Skill> query = em.createQuery("select s from Skill s", Skill.class);
            List<Skill> list = query.getResultList();
            return list;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Skill getById(int id) {
        EntityManager entityManager = createEM();
        try {
            return entityManager.find(Skill.class, id);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    // if skill.id (PK) is null, this method insert skill into database instead of update
    @Override
    public boolean updateSkill(Skill skill) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(skill);
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
    public boolean insertSkill(Skill skill) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(skill);
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
    public boolean removeSkill(int id) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Skill skill = entityManager.find(Skill.class, id);
            transaction.begin();
            entityManager.remove(skill);
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
