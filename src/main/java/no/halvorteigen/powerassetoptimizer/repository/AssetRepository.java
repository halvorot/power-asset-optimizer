package no.halvorteigen.powerassetoptimizer.repository;

import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends JpaRepository<AssetEntity, Long> {

    AssetEntity findByName(final String name);

}
