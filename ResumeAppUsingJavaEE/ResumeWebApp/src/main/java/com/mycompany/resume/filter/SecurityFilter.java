/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Filter.java to edit this template
 */
package com.mycompany.resume.filter;

import com.mycompany.entity.User;
import com.mycompany.resume.util.ControllerUtil;
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elshadzarbali
 */
@WebFilter(filterName = "SecurityFilter", urlPatterns = {"*"})
public class SecurityFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Initialization code, if needed
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        HttpServletRequest req = (HttpServletRequest) request;

        User user = (User) req.getSession().getAttribute("loggedInUser");
        String requestURI = req.getRequestURI();

        if (user != null) {
            if (requestURI.contains("/login")) {
                ControllerUtil.sendErrorPage(res, new IllegalArgumentException("You are already logged in!"));
            } else if (requestURI.contains("/register")) {
                ControllerUtil.sendErrorPage(res, new IllegalArgumentException("You are already logged in!\n"
                        + "Please log out and try again!"));
            } else if (requestURI.contains("/userdetail")) {
                Integer userId;
                try {
                    userId = Integer.valueOf(request.getParameter("id"));
                } catch (NumberFormatException e) {
                    res.sendRedirect("userdetail?id=" + user.getId());
                    return;
                }
                Object isAdmin = req.getSession().getAttribute("isAdmin");
                if (isAdmin == null && user.getId() != userId) {
                    ControllerUtil.sendErrorPage(res, new IllegalArgumentException("Page is not found!"));
                } else {
                    chain.doFilter(request, response);
                }
            } else {
                chain.doFilter(request, response);
            }
        } else {
            if (requestURI.contains("/users") || requestURI.contains("/profile") || requestURI.contains("/userdetail")) {
                res.sendRedirect("login");
            } else {
                chain.doFilter(request, response);
            }
        }
    }

    @Override
    public void destroy() {
        // Cleanup code, if needed
    }
}
