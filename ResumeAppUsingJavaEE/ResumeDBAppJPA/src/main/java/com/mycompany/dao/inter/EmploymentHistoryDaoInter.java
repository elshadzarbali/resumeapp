package com.mycompany.dao.inter;

import com.mycompany.entity.EmploymentHistory;

import java.util.List;

public interface EmploymentHistoryDaoInter {
    List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId);
    boolean updateEmpHistory(EmploymentHistory eh);
    boolean insertEmpHistory(EmploymentHistory eh);
    boolean removeEmpHistory(int id);
}
