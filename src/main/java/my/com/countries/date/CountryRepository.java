package my.com.countries.date;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<CountryEntity, String> {
    boolean existsByCodeIgnoreCase(String code);

    Optional<CountryEntity> findByCodeIgnoreCase(String code);
}
