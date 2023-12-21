package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.entity.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl extends AbstractDAO implements CountryDaoInter {
    
    private Country getCountry(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");
        String nationality = rs.getString("nationality");

        return new Country(id, name, nationality);
    }

    @Override
    public List<Country> getAll() {
        String query = "select * from country";
        List<Country> list = new ArrayList<>();
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Country country = getCountry(rs);
                list.add(country);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Country getById(int id) {
        String query = "select * from country where id = ?";
        try (Connection c = connect();) {
            PreparedStatement pstmt = c.prepareCall(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return getCountry(rs);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateCountry(Country c) {
        String query = "update country set name = ?, nationality = ? from country where id = ?";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareCall(query);
            pstmt.setString(1, c.getName());
            pstmt.setString(2, c.getNationality());
            pstmt.setInt(3, c.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertCountry(Country c) {
        String query = "insert into country (name, nationality) values(?, ?)";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareCall(query);
            pstmt.setString(1, c.getName());
            pstmt.setString(2, c.getNationality());
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeCountry(int id) {
        String query = "delete from country where id = ?";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareCall(query);
            pstmt.setInt(1, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
