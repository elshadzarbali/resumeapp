package com.mycompany.dao.inter;

import com.mycompany.entity.User;

import java.util.List;

public interface UserDaoInter {
    List<User> getAll();
    List<User> getAllBySearch(String name, String surname, Integer nationalityId);
    User getById(int id);
    boolean updateUser(User u);
    boolean removeUser(int id);
    boolean addUser(User u);
}
