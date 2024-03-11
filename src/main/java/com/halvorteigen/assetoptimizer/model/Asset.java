package com.halvorteigen.assetoptimizer.model;

import com.halvorteigen.assetoptimizer.enums.PriceArea;

public record Asset(
    String name,                        // NOTE: Assuming the name is unique and can be used as an identifier
    Double totalEnergyUsagePer24Hours,  // NOTE: Assuming that the numbers as small enough to be represented by a double
    Double minPowerUsage,
    Double maxPowerUsage,
    PriceArea priceArea                 // NOTE: Assuming that an asset needs to be in a valid price area
) {
}
