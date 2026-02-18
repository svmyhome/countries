package my.com.countries.domain.graphql;

import java.util.UUID;
import my.com.countries.date.CountryEntity;

public record CountryGql(
        UUID id, String name, String code, String coordinates) {

    public static CountryGql fromEntity(CountryEntity entity) {
        return new CountryGql(
                entity.getId(),
                entity.getName(),
                entity.getCode(),
                entity.getCoordinates());
    }
}
