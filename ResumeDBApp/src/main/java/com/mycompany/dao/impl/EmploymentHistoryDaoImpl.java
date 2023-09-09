package com.mycompany.dao.impl;

import com.mycompany.main.Context;
import com.mycompany.dao.inter.AbstractDAO;
import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmploymentHistoryDaoImpl extends AbstractDAO implements EmploymentHistoryDaoInter {

    private UserDaoInter userDao = Context.instanceUserDao();

    private EmploymentHistory getEmploymentHistory(ResultSet rs) throws SQLException {
        int id = rs.getInt("id");
        String header = rs.getString("header");
        Date beginDate = rs.getDate("begin_date");
        Date endDate = rs.getDate("end_date");
        String jobDescription = rs.getString("job_description");
        int userId = rs.getInt("user_id");

        User user = userDao.getById(userId);

        return new EmploymentHistory(id, header, beginDate, endDate, jobDescription, user);
    }

    @Override
    public List<EmploymentHistory> getAllEmploymentHistoryByUserId(int userId) {
        String query = "select * from employment_history where user_id = ?";
        List<EmploymentHistory> list = new ArrayList<>();
        try (Connection c = connect()) {
            PreparedStatement pstmt = c.prepareStatement(query);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                EmploymentHistory employmentHistory = getEmploymentHistory(rs);
                list.add(employmentHistory);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public boolean updateEmpHistory(EmploymentHistory eh) {
        String query = "update employment_history set header = ?, begin_date = ?,"
                + " end_date = ?, job_description = ?, user_id = ? where id = ?";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, eh.getHeader());
            pstmt.setDate(2, eh.getBeginDate());
            pstmt.setDate(3, eh.getEndDate());
            pstmt.setString(4, eh.getJobDescription());
            pstmt.setInt(5, eh.getUser().getId());
            pstmt.setInt(6, eh.getId());

            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean insertEmpHistory(EmploymentHistory eh) {
        String query = "insert into employment_history (header, begin_date, end_date,job_description, user_id)"
                + " values(?, ?, ?, ?, ?)";
        try (Connection con = connect();) {
            PreparedStatement pstmt = con.prepareStatement(query);
            pstmt.setString(1, eh.getHeader());
            pstmt.setDate(2, eh.getBeginDate());
            pstmt.setDate(3, eh.getEndDate());
            pstmt.setString(4, eh.getJobDescription());
            pstmt.setInt(5, eh.getUser().getId());
            
            return pstmt.executeUpdate() > 0;
        } catch (ClassNotFoundException | SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean removeEmpHistory(int id) {
        String query = "delete from employment_history where id = ?";
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
