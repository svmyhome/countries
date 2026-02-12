package my.com.countries.service;

import java.util.List;
import my.com.countries.domain.Country;

public interface CountryService {

    List<Country> allCountries();

    Country addCountry(Country country);

    Country updateCountry(String code, Country country);
}
