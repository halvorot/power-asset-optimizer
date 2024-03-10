package com.halvorteigen.assetoptimizer.service;

import com.halvorteigen.assetoptimizer.dto.PowerPriceDto;
import com.halvorteigen.assetoptimizer.enums.PriceArea;
import com.halvorteigen.assetoptimizer.mappers.MapPowerPriceDtoToPowerPrice;
import com.halvorteigen.assetoptimizer.model.PowerPrice;
import com.halvorteigen.assetoptimizer.utils.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PowerPriceService {

    public static final String BASE_URL = "https://www.hvakosterstrommen.no/api/v1/prices/";
    private final RestTemplate restTemplate;

    public PowerPriceService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PowerPrice> getPowerPrices(int year, int month, int day, PriceArea priceArea) {
        String url = getUrl(year, month, day, priceArea);
        PowerPriceDto[] powerPriceDtos = restTemplate.getForObject(url, PowerPriceDto[].class);
        if (powerPriceDtos == null) {
            return List.of();
        }
        return Stream.of(powerPriceDtos)
                     .map(MapPowerPriceDtoToPowerPrice::map)
                     .toList();
    }

    private static String getUrl(int year, int month, int day, PriceArea priceArea) {
        // Assuming year, month and day are valid and not after today
        String monthFormatted = NumberUtils.fillLeadingZeros(month, 2);
        String dayFormatted = NumberUtils.fillLeadingZeros(day, 2);
        return BASE_URL + year + "/" + monthFormatted + "-" + dayFormatted + "_" + priceArea.name() + ".json";
    }
}
