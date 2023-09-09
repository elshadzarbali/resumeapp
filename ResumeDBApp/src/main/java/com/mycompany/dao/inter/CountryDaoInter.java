package com.mycompany.dao.inter;

import com.mycompany.entity.Country;

import java.util.List;

public interface CountryDaoInter {
    List<Country> getAll();
    Country getById(int id);
    boolean updateCountry(Country c);
    boolean insertCountry(Country c);
    boolean removeCountry(int id);
}
