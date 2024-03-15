package no.halvorteigen.powerassetoptimizer.repository;

import jakarta.transaction.Transactional;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

    Optional<AssetEntity> findByName(final String name);

    boolean existsByName(final String name);

    @Transactional
    void deleteByName(final String name);

}
