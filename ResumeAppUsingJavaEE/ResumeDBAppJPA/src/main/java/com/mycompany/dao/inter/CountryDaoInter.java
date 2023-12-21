package com.mycompany.dao.inter;

import com.mycompany.entity.Country;

import java.util.List;

public interface CountryDaoInter {
    List<Country> getAll();
    Country getById(int id);
    boolean updateCountry(Country country);
    boolean insertCountry(Country country);
    boolean removeCountry(int id);
}
