package no.halvorteigen.powerassetoptimizer.mappers;

import no.halvorteigen.powerassetoptimizer.dto.AssetDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;

public class MapAssetEntityToAssetDto {

    private MapAssetEntityToAssetDto() {
        // Hide default constructor
    }

    public static AssetDto map(AssetEntity assetEntity) {
        return new AssetDto(
            assetEntity.getName(),
            assetEntity.getMinPowerUsage(),
            assetEntity.getMaxPowerUsage(),
            assetEntity.getTotalEnergyUsagePer24Hours(),
            assetEntity.getPriceArea()
        );
    }
}
