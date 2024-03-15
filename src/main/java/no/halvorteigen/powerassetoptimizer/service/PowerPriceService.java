package no.halvorteigen.powerassetoptimizer.service;

import no.halvorteigen.powerassetoptimizer.dto.PowerPriceDto;
import no.halvorteigen.powerassetoptimizer.enums.PriceArea;
import no.halvorteigen.powerassetoptimizer.mappers.MapPowerPriceDtoToPowerPrice;
import no.halvorteigen.powerassetoptimizer.model.PowerPrice;
import no.halvorteigen.powerassetoptimizer.utils.DateUtils;
import no.halvorteigen.powerassetoptimizer.utils.NumberUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Stream;

@Service
public class PowerPriceService {

    public static final String BASE_URL = "https://www.hvakosterstrommen.no/api/v1/prices/";
    private static final List<PowerPrice> DEFAULT_POWER_PRICES = List.of(
        new PowerPrice(0.58909),
        new PowerPrice(0.57729),
        new PowerPrice(0.58761),
        new PowerPrice(0.58002),
        new PowerPrice(0.60462),
        new PowerPrice(0.62311),
        new PowerPrice(0.6569),
        new PowerPrice(0.70611),
        new PowerPrice(0.73469),
        new PowerPrice(0.71269),
        new PowerPrice(0.70192),
        new PowerPrice(0.68536),
        new PowerPrice(0.6679),
        new PowerPrice(0.66812),
        new PowerPrice(0.66586),
        new PowerPrice(0.6798),
        new PowerPrice(0.69863),
        new PowerPrice(0.7407),
        new PowerPrice(0.8279),
        new PowerPrice(0.79388),
        new PowerPrice(0.7619),
        new PowerPrice(0.74376),
        new PowerPrice(0.71949),
        new PowerPrice(0.717)
    );
    private final RestTemplate restTemplate;

    public PowerPriceService(final RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PowerPrice> getPowerPrices(int year, int month, int day, PriceArea priceArea) {
        String url = getUrl(year, month, day, priceArea);
        PowerPriceDto[] powerPriceDtos;
        try {
            powerPriceDtos = restTemplate.getForObject(url, PowerPriceDto[].class);
        } catch (RestClientException exception) {
            return DEFAULT_POWER_PRICES;
        }
        if (powerPriceDtos == null) {
            return DEFAULT_POWER_PRICES;
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
