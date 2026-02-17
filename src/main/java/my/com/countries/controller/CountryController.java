package my.com.countries.controller;

import java.util.List;
import my.com.countries.domain.Country;
import my.com.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/countries")
public class CountryController {

    private final CountryService countryService;


    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/all")
    List<Country> countries() {
        return countryService.allCountries();
    }

    @PostMapping("")
    public Country addCountry(@RequestBody Country country) {
        return countryService.addCountry(country);
    }

    @PatchMapping("/{code}")
    public Country updateCountry(@PathVariable String code, @RequestBody Country country) {
        return countryService.updateCountry(code, country);
    }

    @GetMapping("/{id}")
    public Country byId(@PathVariable String id) {
        return countryService.findById(id);
    }
}
