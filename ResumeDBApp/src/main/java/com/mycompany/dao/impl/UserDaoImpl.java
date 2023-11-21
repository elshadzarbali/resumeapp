package com.mycompany.dao.impl;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.entity.Country;
import com.mycompany.entity.User;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserDaoInter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl extends AbstractDAO implements UserDaoInter {
    
    // for crypting password
    private BCrypt.Hasher crypt = BCrypt.withDefaults();

    private User getUser(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String surname = rs.getString("surname");
        String username = rs.getString("username");
        String email = rs.getString("email");
        String phone = rs.getString("phone");
        String password = rs.getString("password");
        String profileDescription = rs.getString("profile_description");
        String address = rs.getString("address");
        Date bithdate = rs.getDate("birthdate");
        int birthplaceId = rs.getInt("birthplace_id");
        int nationalityId = rs.getInt("nationality_id");
        String birthplaceStr = rs.getString("birthplace");
        String nationalityStr = rs.getString("nationality");

        Country nationality = new Country(nationalityId, null, nationalityStr);
        Country birthplace = new Country(birthplaceId, birthplaceStr, null);

        return new User(id, name, surname, username, email, phone, password,
                profileDescription, address, bithdate, birthplace, nationality);
    }

    @Override
    public List<User> getAll() {
        String query = "select u.*, n.nationality, c.name as birthplace from user u "
                + "left join country n on u.nationality_id = n.id "
                + "left join country c on u.birthplace_id = c.id";
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

    // for searching users by name or surname or nationalityId. if name, surname
    // and nationalityId are all null, it returns all users in database.
    // i create this methos instead of changing getAll() method.
    // Menim fikrimce, name ve surname deyisenlerinin bos olub olmamasini
    // burda deyil, web terefde yoxlamaq lazimdir.
    @Override
    public List<User> getAllBySearch(String name, String surname, Integer nationalityId) {
        String query = "select u.*, n.nationality, c.name as birthplace from user u "
                + "left join country n on u.nationality_id = n.id "
                + "left join country c on u.birthplace_id = c.id where 1=1";

        if (name != null && !name.trim().isEmpty()) {
            query += " and u.name=?";
        }
        if (surname != null && !surname.trim().isEmpty()) {
            query += " and u.surname=?";
        }
        if (nationalityId != null) {
            query += " and u.nationality_id=?";
        }
        List<User> list = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);

            int i = 1;
            if (name != null && !name.trim().isEmpty()) {
                pstmt.setString(i++, name);
            }
            if (surname != null && !surname.trim().isEmpty()) {
                pstmt.setString(i++, surname);
            }
            if (nationalityId != null) {
                pstmt.setInt(i, nationalityId);
            }

            ResultSet rs = pstmt.executeQuery();

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
    public User findByEmail(String email) {
        String query = "select u.*, n.nationality, c.name as birthplace from user u "
                + "left join country n on u.nationality_id = n.id "
                + "left join country c on u.birthplace_id = c.id where u.email=?";

        User user = null;

        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return getUser(rs);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User getById(int userId) {
        String query = "select u.*, n.nationality, c.name as birthplace from user u "
                + "left join country n on u.nationality_id = n.id "
                + "left join country c on u.birthplace_id = c.id where u.id = ";
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
        String query = "insert into user (name, surname, username, email, phone, password, "
                + "profile_description, address, birthdate, birthplace_id, nationality_id) "
                + "values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        if (u.getName() == null || u.getSurname() == null || u.getUsername() == null ||
                u.getEmail() == null || u.getPassword() == null) {
            return false; // Ensure name, surname, username, email and are not null
        }

        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, u.getName());
            pstmt.setString(2, u.getSurname());
            pstmt.setString(3, u.getUsername());
            pstmt.setString(4, u.getEmail());
            pstmt.setString(5, u.getPhone());
            pstmt.setString(6, crypt.hashToString(12, u.getPassword().toCharArray()));
            pstmt.setString(7, u.getProfileDescription());
            pstmt.setString(8, u.getAddress());
            pstmt.setDate(9, u.getBirthdate());

            // using setObjetc(int index, Object x) instead setInt(int index, int x).
            // Beacause, we can not set null with setInt() method.
            pstmt.setObject(10, u.getBirthplace().getId());
            pstmt.setObject(11, u.getNationality().getId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public boolean isUsernameExists(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";

        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0; // Return true if the field already exists
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true; // Assuming an exception means the field exists (for error handling)
        }
    }

    @Override
    public boolean isEmailExists(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";

        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0; // Return true if the field already exists
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true; // Assuming an exception means the field exists (for error handling)
        }
    }

    @Override
    public boolean isPhoneExists(String phone) {
        String query = "SELECT COUNT(*) FROM user WHERE phone = ?";

        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setString(1, phone);
            ResultSet resultSet = pstmt.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0; // Return true if the field already exists
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return true; // Assuming an exception means the field exists (for error handling)
        }
    }
}
