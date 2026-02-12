package my.com.countries.service;

import jakarta.transaction.Transactional;
import java.util.List;
import my.com.countries.date.CountryEntity;
import my.com.countries.date.CountryRepository;
import my.com.countries.domain.Country;
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
    public Country addCountry(Country country) {
        if (!countryRepository.existsByCodeIgnoreCase(country.code())) {
            return Country.fromEntyty(countryRepository.save(CountryEntity.fromJson(country)));
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
                    return Country.fromEntyty(savedEntity);
                })
                .orElseThrow(() -> new IllegalArgumentException(
                        "Country with code " + code + " not found"
                ));
    }
}
