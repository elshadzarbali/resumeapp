package com.mycompany.dao.impl;

import com.mycompany.dao.inter.ConnectDAO;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.entity.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl extends ConnectDAO implements SkillDaoInter {
    
    private Skill getSkill(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String name = rs.getString("name");

        return new Skill(id, name);
    }

    @Override
    public List<Skill> getAll() {
        String query = "select * from skill";
        List<Skill> list = new ArrayList<>();
        try (Connection c = connect()) {
            Statement stmt = c.createStatement();
            ResultSet rs = stmt.executeQuery(query);

            while (rs.next()) {
                Skill skill = getSkill(rs);
                list.add(skill);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
