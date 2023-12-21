<%-- 
    Document   : navbar
    Created on : Nov 8, 2023, 12:29:21 PM
    Author     : elshadzarbali
--%>

<%@page import="com.mycompany.entity.User"%>
<%
    User user = (User) request.getSession().getAttribute("loggedInUser");
%>
<!--Navbar-->
<nav class="navbar navbar-expand-lg bg-primary fixed-top" data-bs-theme="dark">
    <div class="container-fluid">

        <!--Brand-->
        <a class="navbar-brand" href="/resume/">ResumeMaker</a>

        <!--Left navbar-->
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item">
                    <a class="nav-link active" aria-current="page" href="/resume/">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="/resume#1">About us</a>
                </li>
                <%
                    if (user != null) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="users">Other users</a>
                </li>
                <%
                    }
                %>
            </ul>
        </div>

        <!--Right navbar-->
        <div class="collapse navbar-collapse justify-content-end">
            <ul class="navbar-nav ml-auto">
                <%
                    if (user == null) {
                        if (request.getRequestURI().contains("/login")) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="register">Register</a>
                </li>
                <%
                } else if (request.getRequestURI().contains("/register")) {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="login">Login</a>
                </li>
                <%
                } else {
                %>
                <li class="nav-item">
                    <a class="nav-link" href="register">Register</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="login">Login</a>
                </li>
                <%
                    }
                } else {
                %>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" role="button"
                       data-bs-toggle="dropdown" aria-expanded="false">
                        <i class="fa-solid fa-user"></i>
                        <span><%=user.getName()%></span>
                    </a>
                    <ul class="dropdown-menu dropdown-menu-end">
                        <li style="padding-left: 10px">
                            <span><%=user.getName()%> <%=user.getSurname()%></span>
                            <span>@<%=user.getUsername()%></span>
                        </li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item" href="profile?id=<%=user.getId()%>">View profile</a></li>
                        <li><a class="dropdown-item" href="userdetail?id=<%=user.getId()%>">Update details</a></li>
                        <li><hr class="dropdown-divider"></li>
                        <li><a class="dropdown-item btn btn-danger" href="#"
                               data-bs-toggle="modal" data-bs-target="#logoutModal">Log out</a></li>
                    </ul>
                </li>
                <%
                    }
                %>
            </ul>
        </div>
    </div>
</nav>

<!-- Modal -->
<div class="modal fade" id="logoutModal" tabindex="-1" aria-labelledby="logoutModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="logoutModalLabel">Log out</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                <p>Are you sure to logout?</p>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <a class="btn btn-danger" href="logout" role="button">Log out</a>
            </div>
        </div>
    </div>
</div>