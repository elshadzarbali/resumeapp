/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.resume.controller;

import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.EmploymentHistory;
import com.mycompany.entity.User;
import com.mycompany.entity.UserSkill;
import com.mycompany.main.Context;
import com.mycompany.resume.util.ControllerUtil;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elshadzarbali
 */
@WebServlet(name = "ProfileController", urlPatterns = {"/profile"})
public class ProfileController extends HttpServlet {
    private UserDaoInter userDao = Context.instanceUserDao();
    private UserSkillDaoInter userSkillDao = Context.instanceUserSkillDao();
    private EmploymentHistoryDaoInter empHisDao = Context.instanceEmploymentHistoryDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String userIdStr = request.getParameter("id");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Id is not specified");
            }

            Integer userId = Integer.parseInt(userIdStr);

            User u = userDao.getById(userId);
            List<UserSkill> userSkills = userSkillDao.getAllUserSkillByUserId(userId);
            List<EmploymentHistory> empHistories = empHisDao.getAllEmploymentHistoryByUserId(userId);

            // Checking user's nullable should be or (may be) in getById() method.
            if (u == null) {
                throw new NullPointerException("There is no user with the specified id");
            }

            request.setAttribute("user", u);
            request.setAttribute("userSkills", userSkills);
            request.setAttribute("empHistories", empHistories);
            request.getRequestDispatcher("profile.jsp").forward(request, response);
        } catch (Exception ex) {
            ControllerUtil.sendErrorPage(response, ex);
        }
    }
}
