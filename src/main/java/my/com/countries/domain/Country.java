package my.com.countries.domain;

import my.com.countries.date.CountryEntity;
import my.com.countries.domain.graphql.CountryGql;

public record Country(String name, String code, String coordinates) {
    public static Country fromEntity(CountryEntity entity) {
        return new Country(entity.getName(), entity.getCode(), entity.getCoordinates());
    }

    public static Country fromCountryGql(CountryGql countryGql) {
        return new Country(
                countryGql.name(),
                countryGql.code(),
                countryGql.coordinates()!=null ? new String(countryGql.coordinates()):"");
    }
}
