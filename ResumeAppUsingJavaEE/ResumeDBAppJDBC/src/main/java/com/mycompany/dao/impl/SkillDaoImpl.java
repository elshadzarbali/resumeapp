package com.mycompany.dao.impl;

import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.SkillDaoInter;
import com.mycompany.entity.Skill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SkillDaoImpl extends AbstractDAO implements SkillDaoInter {
    
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

    @Override
    public Skill getById(int id) {
        String query = "select * from skill where id = ?";
        try (Connection c = connect();) {
            PreparedStatement pstmt = c.prepareCall(query);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return getSkill(rs);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public boolean updateSkill(Skill s) {
        String query = "update skill set name = ? where id = ?";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareCall(query);
            pstmt.setString(1, s.getName());
            pstmt.setInt(2, s.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertSkill(Skill s) {
        String query = "insert into skill (name) values(?)";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareStatement(query,
                    Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, s.getName());
            
            boolean hasUpdatedRows = pstmt.executeUpdate() > 0;
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            if (generatedKeys != null && generatedKeys.next()) {
                s.setId(generatedKeys.getInt(1));
            }
            
            return hasUpdatedRows;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeSkill(int id) {
        String query = "delete from skill where id = ?";
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
