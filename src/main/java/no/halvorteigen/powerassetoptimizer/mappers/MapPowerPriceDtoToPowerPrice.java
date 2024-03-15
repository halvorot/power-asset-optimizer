package no.halvorteigen.powerassetoptimizer.mappers;

import no.halvorteigen.powerassetoptimizer.dto.PowerPriceDto;
import no.halvorteigen.powerassetoptimizer.model.PowerPrice;

public class MapPowerPriceDtoToPowerPrice {

    private MapPowerPriceDtoToPowerPrice() {
        // Hide default constructor
    }

    public static PowerPrice map(PowerPriceDto powerPriceDto) {
        return new PowerPrice(
            powerPriceDto.priceNokPerKwh()
        );
    }
}
