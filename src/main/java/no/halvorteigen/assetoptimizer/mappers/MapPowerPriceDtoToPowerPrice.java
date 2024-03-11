package no.halvorteigen.assetoptimizer.mappers;

import no.halvorteigen.assetoptimizer.dto.PowerPriceDto;
import no.halvorteigen.assetoptimizer.model.PowerPrice;

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
