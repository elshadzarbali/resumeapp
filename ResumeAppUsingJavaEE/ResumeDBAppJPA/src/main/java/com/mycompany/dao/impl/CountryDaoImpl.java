package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.entity.Country;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter {
    
    @Override
    public List<Country> getAll() {
        EntityManager em = createEM();

        try {
            TypedQuery<Country> query = em.createQuery("select c from Country c", Country.class);
            List<Country> list = query.getResultList();
            return list;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public Country getById(int id) {
        EntityManager em = createEM();
        try {
            return em.find(Country.class, id);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // if country.id (PK) is null, this method insert country into database instead of update
    @Override
    public boolean updateCountry(Country country) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(country);
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
    public boolean insertCountry(Country country) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(country);
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
    public boolean removeCountry(int id) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            Country country = entityManager.find(Country.class, id);
            transaction.begin();
            entityManager.remove(country);
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
