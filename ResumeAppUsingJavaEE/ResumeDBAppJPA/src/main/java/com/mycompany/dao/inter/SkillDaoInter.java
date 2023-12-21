package com.mycompany.dao.inter;

import com.mycompany.entity.Skill;

import java.util.List;

public interface SkillDaoInter {
    List<Skill> getAll();
    Skill getById(int id);
    boolean updateSkill(Skill skill);
    boolean insertSkill(Skill skill);
    boolean removeSkill(int id);
}
