package no.halvorteigen.powerassetoptimizer.dto;

import no.halvorteigen.powerassetoptimizer.enums.PriceArea;

public record AssetDto(
    String name,
    Double minPowerUsage,
    Double maxPowerUsage,
    Double totalEnergyUsagePer24Hours,
    PriceArea priceArea
) {
}
