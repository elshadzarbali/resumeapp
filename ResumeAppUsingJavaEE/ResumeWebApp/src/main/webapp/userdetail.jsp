<%-- 
    Document   : userdetail
    Created on : Nov 15, 2023, 6:31:48 PM
    Author     : elshadzarbali
--%>

<%@page import="com.mycompany.entity.Skill"%>
<%@page import="com.mycompany.dao.inter.SkillDaoInter"%>
<%@page import="com.mycompany.entity.UserSkill"%>
<%@page import="com.mycompany.dao.inter.UserSkillDaoInter"%>
<%@page import="com.mycompany.entity.EmploymentHistory"%>
<%@page import="com.mycompany.dao.inter.EmploymentHistoryDaoInter"%>
<%@page import="com.mycompany.entity.Country"%>
<%@page import="java.util.List"%>
<%@page import="com.mycompany.dao.inter.CountryDaoInter"%>
<%@page import="com.mycompany.main.Context"%>
<%@page import="com.mycompany.entity.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-T3c6CoIi6uLrA9TneNEoa7RxnatzjcDSCmG1MXxSR1GAsXEV/Dwwykc2MPK8M2HN" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-C6RzsynM9kWDrMNeT87bh95OGNyZPhcTNXj1NW7RuBCsyN/o0jlpcV8Qyq46cDfL" crossorigin="anonymous"></script>
        <script src="https://kit.fontawesome.com/b57cbc3ba7.js" crossorigin="anonymous"></script>
        <script src="assets/js/userdetail.js"></script>
    </head>
    <body>
        <%
            User u = (User) request.getAttribute("user");
        %>

        <!--JSP include action-->
        <jsp:include page="navbar.jsp"/>

        <div style="margin: 70px 10px 20px 10px">
            <ul class="nav nav-tabs" id="myTab" role="tablist">
                <li class="nav-item" role="presentation">
                    <button class="nav-link active" id="basicDetail-tab" data-bs-toggle="tab"
                            data-bs-target="#basicDetail-tab-pane" type="button" role="tab"
                            aria-controls="basicDetail-tab-pane" aria-selected="true">Basic details</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="details-tab" data-bs-toggle="tab"
                            data-bs-target="#details-tab-pane" type="button" role="tab"
                            aria-controls="details-tab-pane" aria-selected="false">Details</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="empHist-tab" data-bs-toggle="tab"
                            data-bs-target="#empHist-tab-pane" type="button" role="tab"
                            aria-controls="empHist-tab-pane" aria-selected="false">Employment history</button>
                </li>
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="skills-tab" data-bs-toggle="tab"
                            data-bs-target="#skills-tab-pane" type="button" role="tab"
                            aria-controls="skills-tab-pane" aria-selected="false">Skills</button>
                </li>
            </ul>
        </div>
        <div class="tab-content" id="myTabContent">
            <!--Basic details-->
            <div class="tab-pane fade show active" id="basicDetail-tab-pane" role="tabpanel"
                 aria-labelledby="basicDetail-tab" tabindex="0">
                <form action="userdetail" method="POST">
                    <div class="container">
                        <!--Hidden parameters-->
                        <input type="hidden" name="id" value="<%=u.getId()%>">
                        <input type="hidden" name="action" value="updateUserBasicDetails">

                        <!--Name-->
                        <div class="mb-3">
                            <label for="name" class="form-label">Name:</label>
                            <input type="text" class="form-control" id="name"
                                   name="name" value="<%=u.getName()%>" required>
                        </div>

                        <!--Surname-->
                        <div class="mb-3">
                            <label for="surname" class="form-label">Surname:</label>
                            <input type="text" class="form-control" id="surname"
                                   name="surname" value="<%=u.getSurname()%>" required>
                        </div>

                        <!--Username-->
                        <div class="mb-3">
                            <label for="username" class="form-label">Username:</label>
                            <input type="text" class="form-control" id="username"
                                   name="username" value="<%=u.getUsername()%>" required>
                        </div>

                        <!--Email-->
                        <div class="mb-3">
                            <label for="email" class="form-label">Email:</label>
                            <input type="email" class="form-control" id="email" name="email" value="<%=u.getEmail()%>"
                                   aria-describedby="emailHelp" placeholder="Enter your email" required>
                            <div id="emailHelp" class="form-text">We'll never share your email with anyone else.</div>
                        </div>

                        <!--Phone-->
                        <div class="mb-3">
                            <label for="phone" class="form-label">Phone:</label>
                            <input type="tel" class="form-control" id="phone" name="phone" value="<%=u.getPhone()%>"
                                   aria-describedby="phoneHelp" placeholder="Enter your phone">
                            <div id="phoneHelp" class="form-text">We'll never share your phone with anyone else.</div>
                        </div>

                        <!--Submit-->
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>

            <!--Details-->
            <div class="tab-pane fade" id="details-tab-pane" role="tabpanel"
                 aria-labelledby="details-tab" tabindex="0">
                <form action="userdetail" method="POST">
                    <div class="container">
                        <!--Hidden parameters-->
                        <input type="hidden" name="id" value="<%=u.getId()%>">
                        <input type="hidden" name="action" value="updateUserDetails">

                        <!--Profile description-->
                        <div class="mb-3">
                            <label for="prof_desc" class="form-label">Profile description:</label>
                            <textarea class="form-control" id="prof_desc" name="prof_desc" rows="10">
                                <%=u.getProfileDescription()%>
                            </textarea>
                        </div>

                        <!--Address-->
                        <div class="mb-3">
                            <label for="address" class="form-label">Address:</label>
                            <input type="text" class="form-control" id="address"
                                   name="address" value="<%=u.getAddress()%>">
                        </div>

                        <!--Birth date-->
                        <div class="mb-3">
                            <label for="bithdate" class="form-label">Birth date:</label>
                            <input type="date" class="form-control" id="birthdate"
                                   name="birthdate" value="<%=u.getBirthdate()%>">
                        </div>

                        <!--Birth place-->
                        <div class="mb-3">
                            <label for="birthplace" class="form-label">Birth place:</label>
                            <select class="form-select" name="birthplaceId" id="birthplace">
                                <%
                                    if (u.getBirthplace().getId() == null) {
                                %>
                                <option selected>Open this select menu</option>
                                <%
                                    }

                                    CountryDaoInter countryDao = Context.instanceCountryDao();
                                    List<Country> countryList = countryDao.getAll();

                                    for (Country c : countryList) {
                                        if (c.getId() == u.getBirthplace().getId()) {
                                %>
                                <option value="<%=c.getId()%>" selected>
                                    <%=c.getName()%>
                                </option>
                                <%
                                } else {
                                %>
                                <option value="<%=c.getId()%>">
                                    <%=c.getName()%>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <!--Nationality-->
                        <div class="mb-3">
                            <label for="nationality" class="form-label">Nationality:</label>
                            <select class="form-select" name="nationalityId" id="nationality">
                                <%
                                    if (u.getBirthplace().getId() == null) {
                                %>
                                <option selected>Open this select menu</option>
                                <%
                                    }

                                    for (Country c : countryList) {
                                        if (c.getId() == u.getNationality().getId()) {
                                %>
                                <option value="<%=c.getId()%>" selected>
                                    <%=c.getNationality()%>
                                </option>
                                <%
                                } else {
                                %>
                                <option value="<%=c.getId()%>">
                                    <%=c.getNationality()%>
                                </option>
                                <%
                                        }
                                    }
                                %>
                            </select>
                        </div>

                        <!--Submit-->
                        <button type="submit" class="btn btn-primary">Submit</button>
                    </div>
                </form>
            </div>

            <!--Employment History-->
            <div class="tab-pane fade" id="empHist-tab-pane" role="tabpanel"
                 aria-labelledby="empHist-tab" tabindex="0">
                <%
                    EmploymentHistoryDaoInter empHistoryDao = Context.instanceEmploymentHistoryDao();
                    List<EmploymentHistory> empHistoryList = empHistoryDao.getAllEmploymentHistoryByUserId(u.getId());
                %>
                <form action="userdetail" method="POST">
                    <div class="container">
                        <div class="accordion" id="accordionExample">

                            <%
                                int i = 0;
                                for (EmploymentHistory history : empHistoryList) {
                                    i++;
                            %>
                            <div class="accordion-item" id="employmentHistory<%=i%>">
                                <div class="row" style="margin: inherit">
                                    <h2 class="accordion-header col-11" style="padding-left: 0px;">
                                        <button class="accordion-button collapsed" type="button" data-bs-toggle="collapse"
                                                data-bs-target="#collapse<%=i%>" aria-expanded="false"
                                                aria-controls="collapse<%=i%>">
                                            <%=history.getHeader()%>
                                        </button>
                                    </h2>
                                    <div id="collapse<%=i%>" class="accordion-collapse collapse col-11"
                                         data-bs-parent="#accordionExample">
                                        <div class="accordion-body">
                                            <!--Header-->
                                            <div class="mb-3">
                                                <label for="header<%=i%>" class="form-label">Header:</label>
                                                <input type="text" class="form-control" id="header<%=i%>"
                                                       name="header<%=i%>" value="<%=history.getHeader()%>">
                                            </div>

                                            <!--Begin date-->
                                            <div class="mb-3">
                                                <label for="begindate<%=i%>" class="form-label">Begin date:</label>
                                                <input type="date" class="form-control" id="begindate<%=i%>"
                                                       name="begindate<%=i%>" value="<%=history.getBeginDate()%>">
                                            </div>

                                            <!--End date-->
                                            <div class="mb-3">
                                                <label for="enddate<%=i%>" class="form-label">End date:</label>
                                                <input type="date" class="form-control" id="enddate<%=i%>"
                                                       name="enddate<%=i%>" value="<%=history.getEndDate()%>">
                                            </div>

                                            <!--Job description-->
                                            <div class="mb-3">
                                                <label for="job_desc<%=i%>" class="form-label">Job description:</label>
                                                <textarea class="form-control" id="job_desc<%=i%>" name="job_desc<%=i%>">
                                                    <%=history.getJobDescription()%>
                                                </textarea>
                                            </div>
                                        </div>
                                    </div>
                                    <button type="button" class="btn btn-danger col-1"
                                            onclick="deleteEmploymentHistory(<%=i%>, <%=history.getId()%>)">
                                        <i class="fa-solid fa-square-minus"></i>
                                    </button>
                                </div>
                                <input type="hidden" name="paramIndexId<%=history.getId()%>" value="<%=i%>">
                            </div>
                            <%
                                }
                                i++;
                            %>
                            <!--Setting i to paramIndex variable in userdetail.js-->
                            <script>
                                paramIndex = <%=i%>;
                            </script>
                        </div>
                        <input type="hidden" name="id" value="<%=u.getId()%>">
                        <input type="hidden" name="action" value="updateEmploymentHistory">

                        <!-- Hidden input to store deleted history ids -->
                        <input type="hidden" name="deletedEmploymentHistories" id="deletedEmploymentHistories" value="">

                        <!-- Hidden input to store added history indices -->
                        <input type="hidden" name="addedEmpHistoryIndices" id="addedEmpHistoryIndices" value="">

                        <!--Submit-->
                        <div style="margin-top: 20px">
                            <button type="submit" class="btn btn-primary">Submit</button>
                            <button type="button" class="btn btn-secondary" onclick="addEmploymentHistory()">
                                <i class="fa-solid fa-square-plus"></i>
                                <span>Add new history</span>
                            </button>
                        </div>
                    </div>
                </form>
            </div>

            <!--Skills-->
            <div class="tab-pane fade" id="skills-tab-pane" role="tabpanel"
                 aria-labelledby="skills-tab" tabindex="0">
                <%
                    UserSkillDaoInter userSkillDao = Context.instanceUserSkillDao();
                    List<UserSkill> userSkillList = userSkillDao.getAllUserSkillByUserId(u.getId());
                %>
                <div class="container">
                    <form action="userdetail" method="POST">
                        <!--Hidden form-data parameters-->
                        <input type="hidden" id="deletedUserSkills" name="deletedUserSkillId" value="">
                        <input type="hidden" name="id" value="<%=u.getId()%>">
                        <input type="hidden" name="action" value="updateUserSkills">

                        <%
                            SkillDaoInter skillDao = Context.instanceSkillDao();
                            List<Skill> skills = skillDao.getAll();

                            i = 0;
                            for (UserSkill uSkill : userSkillList) {
                                i++;
                        %>

                        <!--UserSkill rows-->
                        <div class="row mb-3" id="userSkill<%=i%>">
                            <!--Skill name of user-->
                            <div class="col-3">
                                <label class="form-label" id="skillName<%=i%>"><%=uSkill.getSkill().getName()%></label>
                            </div>

                            <!--Selecting skill-->
                            <div class="col-3">
                                <select class="form-select" id="selectedSkill<%=i%>" onchange="showSkillName(<%=i%>)">
                                    <%
                                        for (Skill skill : skills) {
                                            if (skill.getId() == uSkill.getSkill().getId()) {
                                    %>
                                    <option name="skillId" value="<%=skill.getId()%>" selected>
                                        <%=skill.getName()%>
                                    </option>
                                    <%
                                    } else {
                                    %>
                                    <option "name="skillId" value="<%=skill.getId()%>">
                                        <%=skill.getName()%>
                                    </option>
                                    <%
                                            }
                                        }
                                    %>
                                </select>
                            </div>

                            <!--Power of user skill-->
                            <div class="col-3">
                                <label for="powerRange<%=i%>" class="form-label" id="powerLabel<%=i%>">
                                    <%=uSkill.getPower()%>0%
                                </label>
                                <input type="range" class="form-range" min="1" max="10" id="powerRange<%=i%>"
                                       name="power" value="<%=uSkill.getPower()%>" onclick="showPower(<%=i%>)">
                            </div>

                            <!--Delete button for delete user skill-->
                            <div class="col-3">
                                <button type="button" class="btn btn-danger"
                                        onclick="deleteUserSkill(<%=i%>, <%=uSkill.getId()%>)">
                                    <i class="fa-solid fa-square-minus"></i>
                                </button>
                            </div>
                            <hr>
                        </div>
                        <%
                            }
                        %>

                        <!--Buttons-->
                        <button type="submit" class="btn btn-primary">Submit</button>
                        <button type="button" class="btn btn-primary" disabled>Add skill</button>
                        <button type="reset" class="btn btn-primary">Reset</button>

                        <!--                        Ede bilmedim:
                                                1. Burada "Add Skill" buttonunu ve funksiyasini elave ede bilmedim.
                                                2. Movcud user skillerini change ederken istediyimiz skill skiller listinde olmadiqda manual
                                                olaraq yeni skill elave ederek user skille elave etmeyi ede bilmedim.
                                                3. Movcud skiller change olunduqdan sonra submit etdikde, form-data'da yalniz hidden
                                                parameterler gedir. <select> elementinin ve range ucun <input> elementinin value'lari getmir.
                                                4. Change olunmus movcud skilleri nece form-data parametrleri kimi gonderecem bilmirem-->
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
