package com.mycompany.dao.impl;

import com.mycompany.dao.inter.ConnectDAO;
import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.entity.Country;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CountryDaoImpl extends ConnectDAO implements CountryDaoInter {
    
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
}
