<%-- 
    Document   : register
    Created on : Nov 5, 2023, 10:17:34 PM
    Author     : elshadzarbali
--%>

<%@page import="java.util.Arrays"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/b57cbc3ba7.js" crossorigin="anonymous"></script>
        <title>Register</title>
        <script>
            function removePasswordError() {
                var errorElement = document.getElementById("passwordMismatchError");
                if (errorElement) {
                    errorElement.remove();
                }
            }

            function removeEmailError() {
                var errorElement = document.getElementById("emailError");
                if (errorElement) {
                    errorElement.remove();
                }
            }

            function validateAgreement() {
                var isAgreed = document.getElementById("aggreement");

                if (!isAgreed.checked) {
                    alert("Please agree to the terms and conditions");
                    event.preventDefault(); // Prevent form submission
                }
            }
        </script>
    </head>
    <body>
        <!--JSP include action-->
        <jsp:include page="navbar.jsp"/>

        <%
            // Getting parameters if they exist
            String name = request.getParameter("name") != null ? request.getParameter("name") : "";
            String surname = request.getParameter("surname") != null ? request.getParameter("surname") : "";
            String email = request.getParameter("email") != null ? request.getParameter("email") : "";
            String password = request.getParameter("password") != null ? request.getParameter("password") : "";
            String rePassword = request.getParameter("repassword") != null ? request.getParameter("repassword") : "";
        %>

        <!--Registration-->
        <div style="text-align: center; margin-top: 70px">
            <h1>Registration</h1>
            <%
                // Getting error attribute if it is set
                if (request.getAttribute("emailError") != null) {
            %>
            <p id='emailError' style="color: red"><%=request.getAttribute("emailError")%></p>
            <%
            } else if (request.getAttribute("passwordError") != null) {
            %>
            <p id='passwordMismatchError' style="color: red"><%=request.getAttribute("passwordError")%></p>
            <%
                }
            %>
        </div>

        <!--Registration form-->
        <div class="container" style="width: 500px">
            <form id="registrationForm" action="register" method="POST">
                <div class="mb-3">
                    <label for="name" class="form-label">Name:</label>
                    <input type="text" class="form-control" id="name" name="name"
                           value="<%=name%>" placeholder="Enter your name" required>
                </div>
                <div class="mb-3">
                    <label for="surname" class="form-label">Surname:</label>
                    <input type="text" class="form-control" id="surname" name="surname"
                           value="<%=surname%>" placeholder="Enter your surname" required>
                </div>
                <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>
                    <input type="email" class="form-control" id="email" name="email" value="<%=email%>"
                           aria-describedby="emailHelp" placeholder="Enter your email" required
                           onkeyup="removeEmailError()">
                    <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                </div>
                <div class="mb-3">
                    <label for="password" class="form-label">Password:</label>
                    <input type="password" class="form-control" id="password" name="password"
                           value="<%=password%>" placeholder="Enter your password" required
                           oninput="removePasswordError()">
                </div>
                <div class="mb-3">
                    <label for="repassword" class="form-label">Repeat password:</label>
                    <input type="password" class="form-control" id="repassword" name="repassword"
                           value="<%=rePassword%>" placeholder="Re-enter your password" required
                           oninput="removePasswordError()">
                </div>
                <div class="mb-3 form-check">
                    <input type="checkbox" class="form-check-input" id="aggreement" name="agreement" required>
                    <label class="form-check-label" for="agreement">I accept the terms and conditions</label>
                </div>
                <button type="submit" class="btn btn-primary" id="btnregister""
                        onclick="validateAgreement()">Register</button>
            </form
        </div>
    </body>
</html>
