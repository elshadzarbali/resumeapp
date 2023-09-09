package com.mycompany.dao.inter;

import com.mycompany.entity.Skill;

import java.util.List;

public interface SkillDaoInter {
    List<Skill> getAll();
    Skill getById(int id);
    boolean updateSkill(Skill s);
    boolean insertSkill(Skill s);
    boolean removeSkill(int id);
}
