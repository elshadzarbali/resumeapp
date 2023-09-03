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
        String address = rs.getString("address");
        Date bithdate = rs.getDate("birthdate");
        int birthplaceId = rs.getInt("birthplace_id");
        int nationalityId = rs.getInt("nationality_id");
        String birthplaceStr = rs.getString("birthplace");
        String nationalityStr = rs.getString("nationality");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);
        
        return new User(id, name, surname, email, phone, profileDescription, address, bithdate, birthplace, nationality);
    }

    @Override
    public List<User> getAll() {
        String query = "select u.*, n.nationality, c.name as birthplace from user u " +
                "left join country n on u.nationality_id = n.id " +
                "left join country c on u.birthplace_id = c.id";
        List<User> list = new ArrayList<>();
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

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
            ResultSet rs = stmt.executeQuery(query + userId);

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
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getSurname());
            pstmt.setString(3, u.getEmail());
            pstmt.setString(4, u.getPhone());
            pstmt.setString(5, u.getProfileDescription());
            pstmt.setString(6, u.getAddress());
            pstmt.setDate(7, u.getBirthdate());
            pstmt.setInt(8, u.getBirthplace().getId());
            pstmt.setInt(9, u.getNationality().getId());
            pstmt.setInt(10, u.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean removeUser(int id) {
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            return stmt.executeUpdate("delete from user where id = " + id + ";") > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean addUser(User u) {
        String query = "insert into user (name, surname, email, phone, profile_description, address, "
                + "bithdate, birthplace_id, nationality_id) values(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getSurname());
            pstmt.setString(3, u.getEmail());
            pstmt.setString(4, u.getPhone());
            pstmt.setString(5, u.getProfileDescription());
            pstmt.setString(6, u.getAddress());
            pstmt.setDate(7, u.getBirthdate());
            pstmt.setInt(8, u.getBirthplace().getId());
            pstmt.setInt(9, u.getNationality().getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
