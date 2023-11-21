/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.resume.controller;

import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.Country;
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
@WebServlet(name = "RegisterController", urlPatterns = {"/register"})
public class RegisterController extends HttpServlet {

    private UserDaoInter userDao = Context.instanceUserDao();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String rePassword = request.getParameter("repassword");

        if (userDao.isEmailExists(email)) {
            request.setAttribute("emailError", "This email is already exist!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return; // Stop the process to prevent further execution
        } else if (!password.equals(rePassword)) {
            request.setAttribute("passwordError", "Passwords do not match. Please re-enter!");
            request.getRequestDispatcher("register.jsp").forward(request, response);
            return; // Stop the process to prevent further execution
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setSurname(surname);
        newUser.setEmail(email);
        newUser.setPassword(password);

        newUser.setNationality(new Country(null, null, null));
        newUser.setBirthplace(new Country(null, null, null));
        
        String username = ControllerUtil.generateUsername(name, surname);
        newUser.setUsername(username);

        boolean isRegistered = userDao.addUser(newUser);

        if (isRegistered) {
            request.getSession().setAttribute("loggedInUser", newUser);
            response.sendRedirect("/resume/");
        } else {
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
