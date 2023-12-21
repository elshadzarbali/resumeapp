package com.mycompany.resume.controller;

import com.mycompany.dao.inter.CountryDaoInter;
import com.mycompany.dao.inter.EmploymentHistoryDaoInter;
import com.mycompany.dao.inter.UserDaoInter;
import com.mycompany.dao.inter.UserSkillDaoInter;
import com.mycompany.entity.Country;
import com.mycompany.entity.EmploymentHistory;
import com.mycompany.entity.User;
import com.mycompany.main.Context;
import com.mycompany.resume.util.ControllerUtil;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.sql.Date;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "UserDetailController", urlPatterns = {"/userdetail"})
public class UserDetailController extends HttpServlet {

    // We must write pattern of SimpleDateFormat object like "yyyy-MM-dd" not like "YYYY-MM-dd"
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    private UserDaoInter userDao = Context.instanceUserDao();
    private UserSkillDaoInter userSkillDao = Context.instanceUserSkillDao();
    private EmploymentHistoryDaoInter empHisDao = Context.instanceEmploymentHistoryDao();
    private CountryDaoInter countryDao = Context.instanceCountryDao();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Integer userId;

        try {
            userId = Integer.valueOf(request.getParameter("id"));
        } catch (NumberFormatException e) {
            ControllerUtil.sendErrorPage(response, e);
            return;
        }

        String action = request.getParameter("action");

        if ("updateUserSkills".equals(action)) {
            updateUserSkill(request, response, userId);
        } else if ("updateEmploymentHistory".equals(action)) {
            updateEmploymentHistory(request, response, userId);
        } else if ("updateUserDetails".equals(action)) {
            updateUserDetails(request, response, userId);
        } else if ("updateUserBasicDetails".equals(action)) {
            
        } else if ("deleteUser".equals(action)) {
            userDao.removeUser(userId);
        }
        response.sendRedirect("profile?id=" + userId);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            String userIdStr = request.getParameter("id");
            if (userIdStr == null || userIdStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Id is not specified");
            }

            Integer userId = Integer.valueOf(userIdStr);

            User u = userDao.getById(userId);

            // Checking user's nullable should be or (may be) in getById() method.
            if (u == null) {
                throw new NullPointerException("There is no user with the specified id");
            }

            request.setAttribute("user", u);
            request.getRequestDispatcher("userdetail.jsp").forward(request, response);
        } catch (Exception ex) {
            ControllerUtil.sendErrorPage(response, ex);
        }
    }

    public void updateUserSkill(HttpServletRequest request, HttpServletResponse response, Integer userId)
            throws ServletException, IOException {
        String deletedUserSkillIdStr = request.getParameter("deletedUserSkillId");

        String[] deletedUserSkillIdArray = deletedUserSkillIdStr.split(",");

        for (String s : deletedUserSkillIdArray) {
            userSkillDao.removeUserSkill(Integer.parseInt(s));
        }
    }

    public void updateEmploymentHistory(HttpServletRequest request, HttpServletResponse response, Integer userId)
            throws ServletException, IOException {
        // header2 = "Java Developer"
        // begindate2 = "09/12/2015"
        // enddate2 = "12/31/2016"
        // job_desc2 = "Some information"
        // paramIndexId46 = "2" ( in paramIndexId46 46 shows employment history id, and value shows
        // that we can get params of this employment history with params ending its value.
        // ...
        // id = "3"
        // action = "updateEmploymentHistory"
        // deletedEmploymentHistories = "1,3"
        // addedEmpHistoryIndices = "5, 6, 9, 15"

        String deletedEmploymentHistoryIdStr = request.getParameter("deletedEmploymentHistories");

        // delete employment history if deletedEmploymentHistoryIdStr is not empty
        if (!deletedEmploymentHistoryIdStr.isEmpty()) {
            String[] deletedEmploymentHistoryArray = deletedEmploymentHistoryIdStr.split(",");

            for (String s : deletedEmploymentHistoryArray) {
                empHisDao.removeEmpHistory(Integer.parseInt(s));
            }
        }

        // updating employment history with the form data parameters
        List<EmploymentHistory> empHisList = empHisDao.getAllEmploymentHistoryByUserId(userId);

        try {
            for (EmploymentHistory empHist : empHisList) {
                // paramIndex - Bu Employmen History'nin melumatlari hansi sonu hansi indexle biten
                // parametrlerde gonderilib onu bildirir.
                String paramIndex = request.getParameter("paramIndexId" + empHist.getId());

                String header = request.getParameter("header" + paramIndex);
                String beginDateStr = request.getParameter("begindate" + paramIndex);
                String endDateStr = request.getParameter("enddate" + paramIndex);
                String jobDesc = request.getParameter("job_desc" + paramIndex);

//                // We can also parse String to LocalDate and convert it to java.sql.Date. So, we
//                // don't need SimpleDateFormat object
//                // Parse the date string into a LocalDate object
//                LocalDate beginLocalDate = LocalDate.parse(beginDateStr);
//                // Convert LocalDate to java.sql.Date
//                java.sql.Date beginDate = java.sql.Date.valueOf(beginLocalDate);
                long beginDateMilliSec = sdf.parse(beginDateStr).getTime();
                long endDateMilliSec = sdf.parse(endDateStr).getTime();
                Date beginDate = new Date(beginDateMilliSec);
                Date endDate = new Date(endDateMilliSec);

                empHist.setHeader(header);
                empHist.setBeginDate(beginDate);
                empHist.setEndDate(endDate);
                empHist.setJobDescription(jobDesc);

                empHisDao.updateEmpHistory(empHist);
            }
        } catch (ParseException ex) {
            ControllerUtil.sendErrorPage(response, ex);
            return;
        }

        try {
            String addedEmpHistoryIndicesStr = request.getParameter("addedEmpHistoryIndices");

            String[] addedEmpHistoryIndices = addedEmpHistoryIndicesStr.split(",");

            for (String paramIndex : addedEmpHistoryIndices) {
                String header = request.getParameter("header" + paramIndex);
                String beginDateStr = request.getParameter("begindate" + paramIndex);
                String endDateStr = request.getParameter("enddate" + paramIndex);
                String jobDesc = request.getParameter("job_desc" + paramIndex);

                long beginDateMilliSec = sdf.parse(beginDateStr).getTime();
                long endDateMilliSec = sdf.parse(endDateStr).getTime();
                Date beginDate = new Date(beginDateMilliSec);
                Date endDate = new Date(endDateMilliSec);

                User currentUser = userDao.getById(userId);
                EmploymentHistory newHistory = new EmploymentHistory(null, header, beginDate,
                        endDate, jobDesc, currentUser);

                empHisDao.insertEmpHistory(newHistory);
            }
        } catch (ParseException ex) {
            ControllerUtil.sendErrorPage(response, ex);
            return;
        }
    }

    public void updateUserDetails(HttpServletRequest request, HttpServletResponse response, Integer userId)
            throws ServletException, IOException {
        // getting form-data param values
        String profileDesc = request.getParameter("prof_desc");
        String address = request.getParameter("address");
        String birthDateStr = request.getParameter("birthdate");
        String birthplaceIdStr = request.getParameter("birthplaceId");
        String natioanlityIdStr = request.getParameter("nationalityId");

        // converting birthDateStr to java.sql.Date type
        Date birthDate;
        try {
            long birthDateMilliSec = sdf.parse(birthDateStr).getTime();
            birthDate = new Date(birthDateMilliSec);
        } catch (ParseException ex) {
            ControllerUtil.sendErrorPage(response, ex);
            return;
        }
        
        // converting birthplaceIdStr and natioanlityIdStr to Integer
        Integer birthplaceId;
        Integer nationalityId;
        try {
            birthplaceId = Integer.valueOf(birthplaceIdStr);
            nationalityId = Integer.valueOf(natioanlityIdStr);
        } catch (NumberFormatException e) {
            ControllerUtil.sendErrorPage(response, e);
            return;
        }
        
        // getting appropriate Country according to birthplaceId and nationalityId
        Country birthplace = countryDao.getById(birthplaceId);
        Country nationality = countryDao.getById(nationalityId);

        // getting relevant User
        User user = userDao.getById(userId);

        // setting chosen User's details
        user.setProfileDescription(profileDesc);
        user.setAddress(address);
        user.setBirthdate(birthDate);
        user.setBirthplace(birthplace);
        user.setNationality(nationality);
        
        // updating user
        boolean wasUpdated = userDao.updateUser(user);
        
        if (!wasUpdated) {
            ControllerUtil.sendErrorPage(response, new IllegalArgumentException("User's info was not updated!"));
        }
    }
}
