<%-- 
    Document   : error
    Created on : Oct 18, 2023, 3:49:41 PM
    Author     : elshadzarbali
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Why are you here?</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/b57cbc3ba7.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!--JSP include action-->
        <jsp:include page="navbar.jsp"/>
        
        <div style="text-align: center; margin: 70px 100px 0px 100px">
            <h1>Error!</h1>
            <p>Why are you here? I will tell why you are in here:</p>
            <p>Because, <%=request.getParameter("msg")%></p>
        </div>
    </body>
</html>
