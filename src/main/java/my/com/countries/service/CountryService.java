package my.com.countries.service;

import jakarta.transaction.Transactional;
import java.util.List;
import my.com.countries.domain.Country;
import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.domain.graphql.CountryInputGql;

public interface CountryService {

    List<Country> allCountries();

    List<CountryGql> allCountriesGql();

    Country findById(String id);

    CountryGql countyGqlById(String id);

    Country addCountry(Country country);

    CountryGql addCountryGql(CountryInputGql country);

    Country updateCountry(String code, Country country);

    @Transactional
    CountryGql updateCountryGql(String code, CountryInputGql country);
}
