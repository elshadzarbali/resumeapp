/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.resume.controller;

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
@WebServlet(name = "ErrorController", urlPatterns = {"/error"})
public class ErrorController extends HttpServlet {
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // error.jsp sehifesine msg parametrini gondermeye ehtiyac yoxdur.
        // error.jsp sehifesi request obyektinden parametri goturecek, cunki,
        // error.jsp sehifesine gonderilen request error servlet sehifesine
        // gelen request'dir.
        request.getRequestDispatcher("error.jsp").forward(request, response);
    }
}
