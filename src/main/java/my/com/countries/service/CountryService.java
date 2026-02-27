package my.com.countries.service;

import jakarta.transaction.Transactional;
import java.util.List;
import my.com.countries.domain.Country;
import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.domain.graphql.CountryInputGql;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CountryService {

    List<Country> allCountries();

    Slice<CountryGql> allCountriesGql(Pageable pageable);

    List<CountryGql> allCountriesGql();

    Country findById(String id);

    CountryGql countyGqlById(String id);

    Country addCountry(Country country);

    CountryGql addCountryGql(CountryInputGql country);

    Country updateCountry(String code, Country country);

    @Transactional
    CountryGql updateCountryGql(String code, CountryInputGql country);
}
