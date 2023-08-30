package com.mycompany.dao.impl;

import com.mycompany.entity.Country;
import com.mycompany.entity.User;
import com.mycompany.dao.inter.ConnectDAO;
import com.mycompany.dao.inter.UserDaoInter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends ConnectDAO implements UserDaoInter {
    private User getUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String profileDescription = rs.getString("profile_description");
        Date bithdate = rs.getDate("birthdate");
        int birthplaceId = rs.getInt("birthplace_id");
        int nationalityId = rs.getInt("nationality_id");
        String birthplaceStr = rs.getString("birthplace");
        String nationalityStr = rs.getString("nationality");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);
        
        return new User(id, name, surname, email, phone, profileDescription,null, bithdate, birthplace, nationality);
    }

    @Override
    public List<User> getAll() {
        String query = "select u.*, n.nationality, c.name as birthplace from user u " +
                "left join country n on u.nationality_id = n.id " +
                "left join country c on u.birthplace_id = c.id";
        List<User> list = new ArrayList<>();
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute(query);
            ResultSet rs = stmt.getResultSet();

            while (rs.next()) {
                User u = getUser(rs);
                list.add(u);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public User getById(int userId) {
        String query = "select u.*, n.nationality, c.name as birthplace from user u " +
                "left join country n on u.nationality_id = n.id " +
                "left join country c on u.birthplace_id = c.id where u.id = ";
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            stmt.execute(query + userId);
            ResultSet rs = stmt.getResultSet();

            if (rs.next()) {
                return getUser(rs);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateUser(User u) {
        String query = "update user set name = ?, surname = ?, email = ?, phone = ?, profile_description = ?, "
                + "address = ?, birthdate = ?, birthplace_id = ?, nationality_id = ? where id = ?";
        try (Connection c = connect()) {
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, u.getName());
            stmt.setString(2, u.getSurname());
            stmt.setString(3, u.getEmail());
            stmt.setString(4, u.getPhone());
            stmt.setString(5, u.getProfileDescription());
            stmt.setString(6, u.getAddress());
            stmt.setDate(7, u.getBirthdate());
            stmt.setInt(8, u.getBirthplace().getId());
            stmt.setInt(9, u.getNationality().getId());
            stmt.setInt(10, u.getId());
            return stmt.execute();
//            return stmt.getUpdateCount() > 0; // I used to write like this, because, execute() method returns true
            // when sql query is select query.
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            return stmt.execute("delete from user where id = " + id + ";");
//            return stmt.getUpdateCount() > 0; // I used to write like this, because, execute() method returns true
            // when sql query is select query.
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUser(User u) {
        String query = "insert into user (name, surname, email, phone) values(?, ?, ?, ?, ?)";
        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getSurname());
            pstmt.setString(3, u.getEmail());
            pstmt.setString(4, u.getPhone());
            pstmt.setString(5, u.getProfileDescription());
            return pstmt.execute();
//            return pstmt.getUpdateCount() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
