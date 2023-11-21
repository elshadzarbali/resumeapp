/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.resume.util;

import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.main.Context;
import java.io.IOException;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author elshadzarbali
 */
public class ControllerUtil {
    private static UserDaoInter userDao = Context.instanceUserDao();
    
    public static void sendErrorPage(HttpServletResponse response, Exception ex) throws IOException {
        ex.printStackTrace();
        response.sendRedirect("error?msg=" + ex.getMessage());
    }
    
    // I think it must locate in service layer or Controller, it must not locate in DAO, because, this
    // method doesn't do database related work.
    public static String generateUsername(String name, String surname) {
        String rootUsername = (name + surname).toLowerCase().replace(" ", "");

        // Initial username based on name and surname
        String generatedUsername = rootUsername;

        // Start with a suffix of 1
        int suffix = 1;

        while (userDao.isUsernameExists(generatedUsername)) {
            suffix++;
            // Append the incremented suffix to the root username
            generatedUsername = rootUsername + suffix;
        }

        return generatedUsername;
    }
}
