package com.mycompany.dao.inter;

import com.mycompany.entity.UserSkill;

import java.util.List;

public interface UserSkillDaoInter {
    List<UserSkill> getAllUserSkillByUserId(int userId);
    boolean updateUserSkill(UserSkill userSkill);
    boolean insertUserSkill(UserSkill userSkill);
    boolean removeUserSkill(int id);
}
