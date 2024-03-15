package no.halvorteigen.powerassetoptimizer.service;

import no.halvorteigen.powerassetoptimizer.dto.AssetDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import no.halvorteigen.powerassetoptimizer.mappers.MapAssetDtoToAssetEntity;
import no.halvorteigen.powerassetoptimizer.repository.AssetRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssetService {

    private final AssetRepository assetRepository;

    public AssetService(final AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    public AssetEntity registerAsset(AssetDto asset) {
        AssetEntity assetEntity = MapAssetDtoToAssetEntity.map(asset);
        return assetRepository.save(assetEntity);
    }

    public AssetEntity updateAsset(AssetDto asset) {
        Optional<AssetEntity> assetEntityOpt = assetRepository.findByName(asset.name());
        if (assetEntityOpt.isEmpty()) {
            return null;
        }
        AssetEntity mappedAssetEntity = MapAssetDtoToAssetEntity.map(asset);
        mappedAssetEntity.setId(assetEntityOpt.get().getId());
        AssetEntity assetEntity = assetRepository.save(mappedAssetEntity);
        return assetRepository.save(assetEntity);
    }

    public boolean unregisterAsset(String name) {
        if (!assetRepository.existsByName(name)) {
            return false;
        }
        assetRepository.deleteByName(name);
        return true;
    }

}
