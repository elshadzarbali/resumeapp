package com.mycompany.dao.impl;

import com.mycompany.main.Context;
import com.mycompany.dao.inter.ConnectDAO;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.Skill;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserSkillDaoImpl extends ConnectDAO implements UserSkillDaoInter {
    
    private static UserDaoInter userDao = Context.instanceUserDao();
    
    private UserSkill getUserSkill(ResultSet rs) throws SQLException {
        int userId = rs.getInt("id");
        int skillId = rs.getInt("skill_id");
        String skillName = rs.getString("skill_name");
        int power = rs.getInt("power");

        User user = userDao.getById(userId);
        Skill skill = new Skill(skillId, skillName);

        return new UserSkill(null, user, skill, power);
    }
    
    @Override
    public List<UserSkill> getAllUserSkillByUserId(int userId) {
        String query = "select u.*, us.skill_id, s.name as skill_name, us.power from user_skill us " +
                "left join skill s on us.skill_id = s.id " +
                "left join user u on us.user_id = u.id where u.id = ?";
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
}
