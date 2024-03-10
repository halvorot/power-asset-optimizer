package com.halvorteigen.assetoptimizer.model;

import com.halvorteigen.assetoptimizer.enums.PriceArea;

// Assuming the name is unique and can be used as an identifier
public record Asset(
    String name,
    Double totalEnergyUsagePer24Hours, // Assuming that the numbers as small enough to be represented by a double
    Double minPowerUsage,
    Double maxPowerUsage,
    PriceArea priceArea // Assuming that an asset needs to be in a valid price area
) {
}
