package com.mycompany.dao.impl;

import com.mycompany.main.Context;
import com.mycompany.dao.inter.ConnectDAO;
import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploymentHistoryDaoImpl extends ConnectDAO implements EmploymentHistoryDaoInter {
    private UserDaoInter userDao = Context.instanceUserDao();
    private EmploymentHistory getEmploymentHistory(ResultSet rs) throws SQLException {
        String header = rs.getString("header");
        Date beginDate = rs.getDate("begin_date");
        Date endDate = rs.getDate("end_date");
        String jobDescription = rs.getString("job_description");
        int userId = rs.getInt("user_id");
        User user = userDao.getById(userId);

        return new EmploymentHistory(null, header, beginDate, endDate, jobDescription, user);
    }

    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        String query = "select *from employment_history where user_id = ?";
        List<EmploymentHistory> list = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setInt(1, userId);
            pstmt.execute();
            ResultSet rs = pstmt.getResultSet();

            while (rs.next()) {
                EmploymentHistory employmentHistory = getEmploymentHistory(rs);
                list.add(employmentHistory);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
