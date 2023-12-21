package com.mycompany.dao.impl;

import com.mycompany.main.Context;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSkillDaoImpl extends AbstractDAO implements UserSkillDaoInter {
    
    private static UserDaoInter userDao = Context.instanceUserDao();
    
    private UserSkill getUserSkill(ResultSet rs) throws SQLException {
        int userSkillId = rs.getInt("user_skill_id");
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        User user = userDao.getById(userId);
        Skill skill = new Skill(skillId, skillName);

        return new UserSkill(userSkillId, user, skill, power);
    }
    
    @Override
    public List<UserSkill> getAllUserSkillByUserId(int userId) {
        String query = "select u.*, us.id as user_skill_id, us.skill_id, s.name as skill_name, us.power "
                + "from user_skill us "
                + "left join skill s on us.skill_id = s.id "
                + "left join user u on us.user_id = u.id where u.id = ?";
        List<UserSkill> list = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                UserSkill userSkill = getUserSkill(rs);
                list.add(userSkill);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateUserSkill(UserSkill us) {
        String query = "update user_skill set user_id = ?, skill_id = ?, power = ? where id = ?";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, us.getUser().getId());
            pstmt.setInt(2, us.getSkill().getId());
            pstmt.setInt(3, us.getPower());
            pstmt.setInt(4, us.getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertUserSkill(UserSkill us) {
        String query = "insert into user_skill (user_id, skill_id, power) values(?, ?, ?)";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, us.getUser().getId());
            pstmt.setInt(2, us.getSkill().getId());
            pstmt.setInt(3, us.getPower());
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeUserSkill(int id) {
        String query = "delete from user_skill where id = ?";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setInt(1, id);
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
