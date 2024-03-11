package no.halvorteigen.assetoptimizer.service;

import no.halvorteigen.assetoptimizer.dto.PowerPriceDto;
import no.halvorteigen.assetoptimizer.enums.PriceArea;
import no.halvorteigen.assetoptimizer.mappers.MapPowerPriceDtoToPowerPrice;
import no.halvorteigen.assetoptimizer.model.PowerPrice;
import no.halvorteigen.assetoptimizer.utils.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PowerPriceService {

    // NOTE: May want to have this url as a configuration property
    public static final String BASE_URL = "https://www.hvakosterstrommen.no/api/v1/prices/";
    private final RestTemplate restTemplate;

    public PowerPriceService(final RestTemplate restTemplate) {
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
        // NOTE: Assuming year, month and day are valid
        String monthFormatted = NumberUtils.fillLeadingZeros(month, 2);
        String dayFormatted = NumberUtils.fillLeadingZeros(day, 2);
        return BASE_URL + year + "/" + monthFormatted + "-" + dayFormatted + "_" + priceArea.name() + ".json";
    }
}
