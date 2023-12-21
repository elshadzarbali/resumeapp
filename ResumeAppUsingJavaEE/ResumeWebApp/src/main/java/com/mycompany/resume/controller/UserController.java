/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.resume.controller;

import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.entity.User;
import com.mycompany.main.Context;
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
@WebServlet(name = "UserController", urlPatterns = {"/users"})
public class UserController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        UserDaoInter userDao = Context.instanceUserDao();

        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String nationalityIdStr = request.getParameter("nid");

        Integer nationalityId = null;

        if (nationalityIdStr != null && !nationalityIdStr.trim().isEmpty()) {
            nationalityId = Integer.valueOf(nationalityIdStr);
        }
        List<User> list = userDao.getAllBySearch(name, surname, nationalityId);
        
        request.setAttribute("userList", list);
        
        request.getRequestDispatcher("users.jsp").forward(request, response);
    }
}
