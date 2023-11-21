<%-- 
    Document   : user
    Created on : Nov 10, 2023, 12:47:50 AM
    Author     : elshadzarbali
--%>

<%@page import="com.mycompany.entity.EmploymentHistory"%>
<%@page import="com.mycompany.entity.UserSkill"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>User</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/b57cbc3ba7.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!--JSP include action-->
        <jsp:include page="navbar.jsp"/>

        <%
            User user = (User) request.getAttribute("user");
            List<UserSkill> userSkills = (List<UserSkill>) request.getAttribute("userSkills");
            List<EmploymentHistory> empHistories = (List<EmploymentHistory>) request.getAttribute("empHistories");
        %>

        <div class="container" style="margin: 70px 10px 20px 10px; max-width: -webkit-fill-available;">
            <div class="row">
                <div class="col-4" style="border: 1px solid;">
                    <p><%=user.getName() + " " + user.getSurname()%></p>
                    <hr>
                    <p><i class="fa-solid fa-briefcase"></i>Specialist</p>
                    <p><i class="fa-solid fa-location-dot"></i><%=user.getAddress()%></p>
                    <p><i class="fa-solid fa-envelope"></i><%=user.getEmail()%></p>
                    <p><i class="fa-solid fa-phone"></i><%=user.getPhone()%></p>
                    <hr>
                    <h2>Skills</h2>
                    <%
                        for (UserSkill skill : userSkills) {
                    %>
                    <div style="margin: 5px 0px 10px 0px">
                        <p style="margin-bottom: -2px"><%=skill.getSkill().getName()%></p>
                        <div class="progress">
                            <div class="progress-bar" style="width: <%=skill.getPower()%>0%"><%=skill.getPower()%>0%</div>
                        </div>
                    </div>
                    <%
                        }
                    %>
                </div>
                <div class="col-8" style="border: 1px solid;">
                    <div>
                        <h2><i class="fa-regular fa-id-card"></i><%=user.getName()%>'s profile description:</h2>
                        <p><%=user.getProfileDescription()%></p>
                    </div>
                    <div>
                        <h2><i class="fa-solid fa-briefcase"></i>Work experience</h2>
                        <%
                            for (EmploymentHistory empHist : empHistories) {
                        %>
                        <div>
                            <p><%=empHist.getHeader()%></p>
                            <p><%=empHist.getBeginDate()%> - <%=empHist.getEndDate()%></p>
                            <p><%=empHist.getJobDescription()%></p>
                        </div>
                        <%
                            }
                        %>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
