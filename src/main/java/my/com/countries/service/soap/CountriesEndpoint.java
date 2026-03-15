package my.com.countries.service.soap;



import static my.com.countries.config.ApiConfig.SOAP_NAMESPACE;
import my.com.countries.CountriesResponse;
import my.com.countries.Country;
import my.com.countries.CountryResponse;
import my.com.countries.IdCountryRequest;
import my.com.countries.PageCountryRequest;
import my.com.countries.domain.graphql.CountryGql;
import my.com.countries.service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CountriesEndpoint {

    private final CountryService countryService;

    @Autowired
    public CountriesEndpoint(CountryService countryService) {
        this.countryService = countryService;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "idCountryRequest")
    @ResponsePayload
    public CountryResponse country(@RequestPayload IdCountryRequest request) {
        final CountryGql country = countryService.countyGqlById(request.getId());
        CountryResponse countryResponse = new CountryResponse();
        Country xmlCountry = new Country();
        xmlCountry.setId(country.id().toString());
        xmlCountry.setName(country.name());
        xmlCountry.setCode(country.code());
        xmlCountry.setCoordinates(country.coordinates());
        countryResponse.setCountry(xmlCountry);
        return countryResponse;
    }

    @PayloadRoot(namespace = SOAP_NAMESPACE, localPart = "pageCountryRequest")
    @ResponsePayload
    public CountriesResponse countries(@RequestPayload PageCountryRequest request) {
        Page<CountryGql> country = (Page<CountryGql>) countryService.allCountriesGql(
                PageRequest.of(
                        request.getPage(),
                        request.getSize()
                )
        );
        CountriesResponse countriesResponse = new CountriesResponse();
        countriesResponse.setTotalPages(country.getTotalPages());
        countriesResponse.setTotalElements(country.getTotalElements());
        countriesResponse.getCountries().addAll(
                country.getContent().stream().map(
                        gqlPhoto -> {
                            Country xmlCountry = new Country();
                            xmlCountry.setId(gqlPhoto.id().toString());
                            xmlCountry.setName(gqlPhoto.name());
                            xmlCountry.setCode(gqlPhoto.code());
                            xmlCountry.setCoordinates(gqlPhoto.coordinates());
                            return xmlCountry;
                        }
                ).toList()
        );
        return countriesResponse;
    }
}
