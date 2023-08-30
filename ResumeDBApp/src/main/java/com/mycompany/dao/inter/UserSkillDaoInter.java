package com.mycompany.dao.inter;

import com.mycompany.entity.UserSkill;

import java.util.List;

public interface UserSkillDaoInter {
    List<UserSkill> getAllUserSkillByUserId(int id);
}
