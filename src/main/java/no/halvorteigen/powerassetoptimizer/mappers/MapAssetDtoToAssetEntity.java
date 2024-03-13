package no.halvorteigen.powerassetoptimizer.mappers;

import no.halvorteigen.powerassetoptimizer.dto.AssetDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;

public class MapAssetDtoToAssetEntity {

    private MapAssetDtoToAssetEntity() {
        // Hide default constructor
    }

    public static AssetEntity map(AssetDto assetDto) {
        return new AssetEntity(
            assetDto.name(),
            assetDto.minPowerUsage(),
            assetDto.maxPowerUsage(),
            assetDto.totalEnergyUsagePer24Hours(),
            assetDto.priceArea()
        );
    }
}
