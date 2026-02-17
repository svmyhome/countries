package my.com.countries.controller.graphql;

import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.domain.graphql.CountryInputGql;
import my.com.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
public class CountryMutationController {

    private final CountryService countryService;


    @Autowired
    public CountryMutationController(CountryService countryService) {
        this.countryService = countryService;
    }

    @MutationMapping
    public CountryGql addCountry(@Argument CountryInputGql input) {
        return countryService.addCountryGql(input);
    }

    @MutationMapping
    public CountryGql updateCountry(@Argument String code, @Argument CountryInputGql input) {
        return countryService.updateCountryGql(code, input);
    }
}
