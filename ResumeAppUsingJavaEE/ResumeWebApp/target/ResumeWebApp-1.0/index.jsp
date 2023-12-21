<%-- 
    Document   : index
    Created on : Nov 8, 2023, 12:40:20 PM
    Author     : elshadzarbali
--%>

<%@page import="com.mycompany.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>Resume Maker</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/b57cbc3ba7.js" crossorigin="anonymous"></script>
    </head>
    <body>
        <!--JSP include action-->
        <jsp:include page="navbar.jsp"/>

        <div style="text-align: center; margin: 70px 100px 50px 100px">
            <h1>Welcome to ResumeMaker!</h1>
            <p>You can create your resume.</p>
            <%
                User user = (User) request.getSession().getAttribute("loggedInUser");
                if (request.getSession().getAttribute("loggedInUser") != null) {
            %>
            <h2>Hello, <%=user.getName()%></h2>
            <%
            } else {
            %>
            <button onclick="document.location = 'register'">Register</button>
            <button onclick="document.location = 'login'">Login</button>
            <%
                }
            %>
        </div>

        <div style="margin: 500px 200px 0px 200px; padding-top: 70px" id="1">
            <h1>About us</h1>
            <p>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et
                dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip
                ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu
                fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt
                mollit anim id est laborum.
            </p>
            <p>
                Curabitur pretium tincidunt lacus. Nulla gravida orci a odio. Nullam varius, turpis et commodo pharetra,
                est eros bibendum elit, nec luctus magna felis sollicitudin mauris. Integer in mauris eu nibh euismod
                gravida. Duis ac tellus et risus vulputate vehicula. Donec lobortis risus a elit. Etiam tempor. Ut
                ullamcorper, ligula eu tempor congue, eros est euismod turpis, id tincidunt sapien risus a quam. Maecenas
                fermentum consequat mi. Donec fermentum. Pellentesque malesuada nulla a mi. Duis sapien sem, aliquet nec,
                commodo eget, consequat quis, neque. Aliquam faucibus, elit ut dictum aliquet, felis nisl adipiscing
                sapien, sed malesuada diam lacus eget erat. Cras mollis scelerisque nunc. Nullam arcu. Aliquam consequat.
                Curabitur augue lorem, dapibus quis, laoreet et, pretium ac, nisi. Aenean magna nisl, mollis quis,
                molestie eu, feugiat in, orci. In hac habitasse platea dictumst.
            </p>
            <p>
                Fusce convallis, mauris imperdiet gravida bibendum, nisl turpis suscipit mauris, sed placerat ipsum urna
                sed risus. In convallis tellus a mauris. Curabitur non elit ut libero tristique sodales. Mauris a lacus.
                Donec mattis semper leo. In hac habitasse platea dictumst. Vivamus facilisis diam at odio. Mauris dictum,
                nisi eget consequat elementum, lacus ligula molestie metus, non feugiat orci magna ac sem. Donec turpis.
                Donec vitae metus. Morbi tristique neque eu mauris. Quisque gravida ipsum non sapien. Proin turpis lacus,
                scelerisque vitae, elementum at, lobortis ac, quam. Aliquam dictum eleifend risus. In hac habitasse
                platea dictumst. Etiam sit amet diam. Suspendisse odio. Suspendisse nunc. In semper bibendum libero.
            </p>
            <p>
                Proin nonummy, lacus eget pulvinar lacinia, pede felis dignissim leo, vitae tristique magna lacus sit
                amet eros. Nullam ornare. Praesent odio ligula, dapibus sed, tincidunt eget, dictum ac, nibh. Nam quis
                lacus. Nunc eleifend molestie velit. Morbi lobortis quam eu velit. Donec euismod vestibulum massa. Donec
                non lectus. Aliquam commodo lacus sit amet nulla. Cras dignissim elit et augue. Nullam non diam.
                Pellentesque habitant morbi tristique senectus et netus et malesuada fames ac turpis egestas. In hac
                habitasse platea dictumst. Aenean vestibulum. Sed lobortis elit quis lectus. Nunc sed lacus at augue
                bibendum dapibus.
            </p>
        </div>
    </body>
</html>

