/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.resume.controller;

import at.favre.lib.crypto.bcrypt.BCrypt;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;
import com.mycompany.main.Context;
import com.mycompany.resume.util.ControllerUtil;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elshadzarbali
 */
@WebServlet(name = "LoginController", urlPatterns = {"/login"})
public class LoginController extends HttpServlet {
    
    private UserDaoInter userDao = Context.instanceUserDao();
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            
            User user = userDao.findByEmail(email);
            
            if (user == null) {
                throw new IllegalArgumentException("User doesn't exist!");
            }
            
            BCrypt.Result result = BCrypt.verifyer().verify(password.toCharArray(), user.getPassword());
            
            if (!result.verified) {
                throw new IllegalArgumentException("Username or password is incorrect!");
            }
            
            request.getSession().setAttribute("loggedInUser", user);
            if (user.getId() == 6) {
                request.getSession().setAttribute("isAdmin", true);
            }
            response.sendRedirect("/resume/");
        } catch (Exception ex) {
            ControllerUtil.sendErrorPage(response, ex);
        }
    }
}
