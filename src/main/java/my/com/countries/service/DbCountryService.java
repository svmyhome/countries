package my.com.countries.service;

import jakarta.transaction.Transactional;
import java.util.List;
import java.util.UUID;
import my.com.countries.date.CountryEntity;
import my.com.countries.date.CountryRepository;
import my.com.countries.domain.Country;
import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.domain.graphql.CountryInputGql;
import my.com.countries.ex.CountryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DbCountryService implements CountryService {

    private final CountryRepository countryRepository;

    @Autowired
    public DbCountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    @Override
    public List<Country> allCountries() {
        return countryRepository.findAll()
                .stream()
                .map(c -> {
                    return new Country(
                            c.getName(),
                            c.getCode(),
                            c.getCoordinates()
                    );
                }).toList();
    }

    @Override
    public List<CountryGql> allCountriesGql() {
        return countryRepository.findAll()
                .stream()
                .map(c -> {
                    return new CountryGql(
                            c.getId(),
                            c.getName(),
                            c.getCode(),
                            c.getCoordinates()
                    );
                }).toList();
    }

    @Override
    public Country findById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .map(c -> new Country(
                        c.getName(),
                        c.getCode(),
                        c.getCoordinates()!=null ? new String(c.getCoordinates()):""
                )).orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public CountryGql countyGqlById(String id) {
        return countryRepository.findById(UUID.fromString(id))
                .map(c -> new CountryGql(
                        c.getId(),
                        c.getName(),
                        c.getCode(),
                        c.getCoordinates()!=null ? new String(c.getCoordinates()):""
                )).orElseThrow(CountryNotFoundException::new);
    }

    @Override
    public Country addCountry(Country country) {
        if (!countryRepository.existsByCodeIgnoreCase(country.code())) {
            return Country.fromEntity(countryRepository.save(CountryEntity.fromJson(country)));
        } else throw new IllegalArgumentException("Country exists");
    }

    @Override
    public CountryGql addCountryGql(CountryInputGql country) {
        if (!countryRepository.existsByCodeIgnoreCase(country.code())) {
            CountryEntity ce = new CountryEntity();
            ce.setName(country.name());
            ce.setCode(country.code());
            ce.setCoordinates(country.coordinates());
            CountryEntity saved = countryRepository.save(ce);

            return new CountryGql(
                    saved.getId(),
                    saved.getName(),
                    saved.getCode(),
                    saved.getCoordinates()
            );
        } else throw new IllegalArgumentException("Country exists");
    }

    @Override
    @Transactional
    public Country updateCountry(String code, Country country) {
        return countryRepository.findByCodeIgnoreCase(code)
                .map(e -> {
                    e.setName(country.name());
                    e.setCoordinates(country.coordinates());
                    CountryEntity savedEntity = countryRepository.save(e);
                    return Country.fromEntity(savedEntity);
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "Country with code " + code + " not found"
                ));
    }


    @Transactional
    @Override
    public CountryGql updateCountryGql(String code, CountryInputGql country) {
        return countryRepository.findByCodeIgnoreCase(code)
                .map(e -> {
                    e.setName(country.name());
                    e.setCoordinates(country.coordinates());
                    CountryEntity saved = countryRepository.save(e);
                    return new CountryGql(
                            saved.getId(),
                            saved.getName(),
                            saved.getCode(),
                            saved.getCoordinates()
                    );
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "Country with code " + code + " not found"
                ));
    }
}
