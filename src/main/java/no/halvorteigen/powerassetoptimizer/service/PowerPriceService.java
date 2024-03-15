package no.halvorteigen.powerassetoptimizer.service;

import no.halvorteigen.powerassetoptimizer.dto.PowerPriceDto;
import no.halvorteigen.powerassetoptimizer.enums.PriceArea;
import no.halvorteigen.powerassetoptimizer.mappers.MapPowerPriceDtoToPowerPrice;
import no.halvorteigen.powerassetoptimizer.model.PowerPrice;
import no.halvorteigen.powerassetoptimizer.utils.DateUtils;
import no.halvorteigen.powerassetoptimizer.utils.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PowerPriceService {

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
        boolean isValidDate = DateUtils.isValidDate(year, month, day);
        if (!isValidDate) {
            throw new IllegalArgumentException("Invalid day, month or year.");
        }
        String monthFormatted = NumberUtils.fillLeadingZeros(month, 2);
        String dayFormatted = NumberUtils.fillLeadingZeros(day, 2);
        return BASE_URL + year + "/" + monthFormatted + "-" + dayFormatted + "_" + priceArea.name() + ".json";
    }
}
