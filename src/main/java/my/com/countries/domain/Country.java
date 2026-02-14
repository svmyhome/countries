package my.com.countries.domain;

import my.com.countries.date.CountryEntity;

public record Country(String name, String code, String coordinates) {
    public static Country fromEntity(CountryEntity entity) {
        return new Country(entity.getName(), entity.getCode(), entity.getCoordinates());
    }
}
