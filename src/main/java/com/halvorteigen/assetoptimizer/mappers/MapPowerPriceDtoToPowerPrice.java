package com.halvorteigen.assetoptimizer.mappers;

import com.halvorteigen.assetoptimizer.dto.PowerPriceDto;
import com.halvorteigen.assetoptimizer.model.PowerPrice;

public class MapPowerPriceDtoToPowerPrice {

    private MapPowerPriceDtoToPowerPrice() {
        // Hide default constructor
    }

    public static PowerPrice map(PowerPriceDto powerPriceDto) {
        return new PowerPrice(
            powerPriceDto.priceNokPerKwh(),
            powerPriceDto.priceEurPerKwh(),
            powerPriceDto.EXR(),
            powerPriceDto.timeStart(),
            powerPriceDto.timeEnd()
        );
    }
}
