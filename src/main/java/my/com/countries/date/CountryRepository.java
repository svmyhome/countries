package my.com.countries.date;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, UUID> {
    boolean existsByCodeIgnoreCase(String code);

    Optional<CountryEntity> findByCodeIgnoreCase(String code);
}
