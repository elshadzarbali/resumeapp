<%-- 
    Document   : user
    Created on : Oct 2, 2023, 6:00:59 PM
    Author     : elshadzarbali
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.main.Context"%>
<%@page import="com.mycompany.dao.inter.UserDaoInter"%>
<%@page import="com.mycompany.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="assets/css/users.css"/>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/b57cbc3ba7.js" crossorigin="anonymous"></script>
        <script src="assets/js/users.js" ></script>
    </head>
    <body>
        <!--JSP include action-->
        <jsp:include page="navbar.jsp"/>

        <%
            List<User> list = (List<User>) request.getAttribute("userList");
        %>

        <div class = "container mycontainer col-12" style="margin-top: 70px">
            <div class="row">
                <div class="col-4">
                    <form action="users" method="GET">
                        <div class = "mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input placeholder="Enter name" class="form-control" type="text" name="name" value="">
                        </div>
                        <div class = "mb-3">
                            <label for="surname" class="form-label">Surname:</label>
                            <input placeholder="Enter surname" class="form-control" type="text" name="surname" value="">
                        </div>
                        <div class = "mb-3">
                            <label for="nid" class="form-label">Nationality:</label>
                            <input placeholder="Enter nationality" class="form-control" type="text" name="nid" value="">
                        </div>
                        <input class="btn btn-primary" type="submit" name="search" value="Search">
                    </form>
                </div>
            </div>
            <hr>
            <div>
                <table class="table table-hover">
                    <thead>
                        <tr>
                            <th>Name</th>
                            <th>Surname</th>
                            <th>Nationality</th>
                                <%
                                    Object isAdmin = session.getAttribute("isAdmin");
                                    if (isAdmin != null) {
                                %>
                            <th></th>
                            <th></th>
                                <%
                                    }
                                %>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody>
                        <%
                            for (User user : list) {
                        %>
                        <tr>
                            <td><%=user.getName()%></td>
                            <td><%=user.getSurname()%></td>
                            <td><%=user.getNationality().getNationality() == null ? "N/A" : user.getNationality().getNationality()%></td>
                            <%
                                if (isAdmin != null) {
                            %>
                            <td style="width: 5px">
                                <button type="button" value="delete" class="btn btn-danger"
                                        onclick="setIdForDelete('<%=user.getId()%>')"
                                        data-bs-toggle="modal" data-bs-target="#exampleModal">
                                    <i class="fa-solid fa-trash-can"></i>
                                </button>
                            </td>
                            <td style="width: 5px">
                                <form action="userdetail" method="GET">
                                    <input type="hidden" name="id" value="<%=user.getId()%>">
                                    <input type="hidden" name="action" value="update">
                                    <button type="submit" value="update" class="btn btn-secondary">
                                        <i class="fa-solid fa-square-pen"></i>
                                    </button>
                                </form>
                            </td>
                            <%
                                }
                            %>
                            <td style="width: 5px">
                                <a class="btn btn-primary" href="profile?id=<%=user.getId()%>" role="button">
                                    <i class="fa-solid fa-info"></i>
                                </a>
                            </td>
                        </tr>
                        <%}%>
                    </tbody>
                </table>
            </div>
        </div>

        <!-- Modal -->
        <div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h1 class="modal-title fs-5" id="exampleModalLabel">Delete</h1>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <p>Are you sure?</p>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <form action="userdetail" method="POST">
                            <input type="hidden" name="id" value="" id="idForDelete">
                            <input type="hidden" name="action" value="deleteUser">
                            <button type="submit" class="btn btn-danger">Delete</button>
                        </form
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
