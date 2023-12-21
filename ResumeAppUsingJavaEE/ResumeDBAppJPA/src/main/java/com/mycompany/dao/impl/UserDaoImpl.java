package com.mycompany.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.entity.User;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserDaoInter;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {

    // for crypting password
    private BCrypt.Hasher crypt = BCrypt.withDefaults();

    @Override
    public List<User> getAll() {
        EntityManager em = createEM();

        try {
            TypedQuery<User> query = em.createQuery("select u from User u", User.class);
            List<User> list = query.getResultList();
            return list;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // for searching users by name or surname or nationalityId. if name, surname
    // and nationalityId are all null, it returns all users in database.
    @Override
    public List<User> getAllBySearch(String name, String surname, Integer nationalityId) {
        EntityManager em = createEM();

        try {
            String queryStr = "select u from User u where 1=1";

            if (name != null && !name.trim().isEmpty()) {
                queryStr += " and u.name=:name";
            }
            if (surname != null && !surname.trim().isEmpty()) {
                queryStr += " and u.surname=:surname";
            }
            if (nationalityId != null) {
                queryStr += " and u.nationality.id=:nid";
            }

            TypedQuery<User> query = em.createQuery(queryStr, User.class);

            if (name != null && !name.trim().isEmpty()) {
                query.setParameter("name", name);
            }
            if (surname != null && !surname.trim().isEmpty()) {
                query.setParameter("surname", surname);
            }
            if (nationalityId != null) {
                query.setParameter("nid", nationalityId);
            }

            List<User> list = query.getResultList();

            return list;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public User findByEmail(String email) {
        EntityManager em = createEM();
        try {
            String queryStr = "select u from User u where u.email = :email";
            TypedQuery<User> query = em.createQuery(queryStr, User.class);
            query.setParameter("email", email);
            List<User> list = query.getResultList();

            if (list.size() == 1) {
                return list.get(0);
            }
            return null;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    // findByEmail using Criteria API
    // <editor-fold defaultstate="collapsed" desc="expand to see code">
//    @Override
//    public User findByEmail(String email) {
//        EntityManager em = createEM();
//
//        try {
//            CriteriaBuilder cBuilder = em.getCriteriaBuilder();
//            CriteriaQuery<User> cQuery = cBuilder.createQuery(User.class);
//            Root<User> root = cQuery.from(User.class);
//
//            Predicate predicate = cBuilder.equal(root.get("email"), email);
//
//            cQuery.where(predicate);
//
//            TypedQuery<User> query = em.createQuery(cQuery);
//            List<User> list = query.getResultList();
//
//            if (list.size() == 1) {
//                return list.get(0);
//            }
//            return null;
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
//    }
    // </editor-fold>
    
    // findByEmail using NamedQuery
    // <editor-fold defaultstate="collapsed" desc="expand to see code">
//    @Override
//    public User findByEmail(String email) {
//        EntityManager em = createEM();
//        try {
//            TypedQuery<User> query = em.createNamedQuery("User.findByEmail", User.class);
//            query.setParameter("email", email);
//            List<User> list = query.getResultList();
//
//            if (list.size() == 1) {
//                return list.get(0);
//            }
//            return null;
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
//    }
    // </editor-fold>
    
    // findByEmail using Native SQL
    // <editor-fold defaultstate="collapsed" desc="expand to see code">
//    @Override
//    public User findByEmail(String email) {
//        EntityManager em = createEM();
//        try {
//            String sql = "select * from user where email = ?";
//            Query query = em.createNativeQuery(sql, User.class);
//            query.setParameter(1, email);
//            List<User> list = query.getResultList();
//
//            if (list.size() == 1) {
//                return list.get(0);
//            }
//            return null;
//        } finally {
//            if (em != null && em.isOpen()) {
//                em.close();
//            }
//        }
//    }
    // </editor-fold>
    
    @Override
    public User getById(int userId) {
        EntityManager entityManager = createEM();
        try {
            return entityManager.find(User.class, userId);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
            }
        }
    }

    // if user.id (PK) is null, this method insert user into database instead of update
    @Override
    public boolean updateUser(User user) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.merge(user);
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
    public boolean removeUser(int id) {
        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            User user = entityManager.find(User.class, id);
            transaction.begin();
            entityManager.remove(user);
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
    public boolean addUser(User user) {
        user.setPassword(crypt.hashToString(12, user.getPassword().toCharArray()));

        EntityManager entityManager = createEM();
        EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();
            entityManager.persist(user);
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
    public boolean isUsernameExists(String username) {
        EntityManager em = createEM();
        try {
            String queryStr = "select count(u) from User u where u.username = :username";
            TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
            query.setParameter("username", username);
            Long count = query.getSingleResult();

            return count > 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        EntityManager em = createEM();
        try {
            String queryStr = "select count(u) from User u where u.email = :email";
            TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
            query.setParameter("email", email);
            Long count = query.getSingleResult();

            return count > 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }

    @Override
    public boolean isPhoneExists(String phone) {
        EntityManager em = createEM();
        try {
            String queryStr = "select count(u) from User u where u.phone = :phone";
            TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
            query.setParameter("phone", phone);
            Long count = query.getSingleResult();

            return count > 0;
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
}
