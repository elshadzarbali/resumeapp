package com.mycompany.main;

import com.mycompany.dao.inter.*;
import com.mycompany.entity.Country;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        // DAO - Data Access Object

        // UserDaoImpl classinin instance'ini yaratmaq ucun biz Context adinda ayrica bir class yaratdiq ve hemin
        // classin icinde instanceUserDao() methodunda UserDaoImple() obyektini return etdik. Bunu ona gore etdik
        // ki, classlar bir birinden asili olmasin. Bu halda Main classi UserDaoImpl classindan asili deyil.
        // Meselen, ne vaxtsa, oracle database'i ile isleyen UserDaoImpl2 classi olar, o zaman biz sadece Context
        // classindaki instanceUserDao() methodunda UserDaoImpl2() obyektini return edeceyik. Belelikle, bizim
        // Main classi UserDaoImpl classindan asili olmur. Buna loosely coupling deyilir.

        // Ancaq biz Main classinda birbasa UserDaoImpl() obyektini yaradib istifade etsek, Main classi UserDaoImpl
        // classindan asili olacaq. Ne vaxtsa, UserDaoImple2() obyektini istifade etmek istesek, onda Main classinda
        // deyisiklik etmeli olacagiq. Buna ise tightly coupling deyilir.

        // entity ve ya model package adlari database'de ola table'lar ucun yaradilmis classlar ucundur. Lakin,
        // bean normal classlar ucundur.
        
        // **************************************************************************************************************

        UserDaoInter userDao = Context.instanceUserDao();

        // getAll()
//        List<User> list = userDao.getAll();
//        list.forEach(System.out::println);

        // getById()
//        User u = userDao.getById(2);
//        u.setName("Javid");

        // updateUser()
//        boolean update = userDao.updateUser(u);
//        System.out.println(update);

        // removeUser()
//        boolean remove = userDao.removeUser(2);
//        System.out.println(remove);

        UserSkillDaoInter userSkillDao = Context.instanceUserSkillDao();

        // getAllSkillByUserId()
//        List<UserSkill> list = userSkillDao.getAllUserSkillByUserId(3);
//        list.forEach(System.out::println);

        EmploymentHistoryDaoInter employmentHistoryDao = Context.instanceEmploymentHistoryDao();

        // getAllEmploymentHistoryByUserId()
//        List<EmploymentHistory> list = employmentHistoryDao.getAllEmploymentHistoryByUserId(3);
//        list.forEach(System.out::println);

        SkillDaoInter skillDao = Context.instanceSkillDao();

        // getAll()
//        List<Skill> list = skillDao.getAll();
//        list.forEach(System.out::println);

        CountryDaoInter countryDao = Context.instanceCountryDao();

        // getAll()
        List<Country> list = countryDao.getAll();
        list.forEach(System.out::println);
    }
}
