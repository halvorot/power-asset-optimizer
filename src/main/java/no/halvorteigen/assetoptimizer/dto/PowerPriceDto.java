package no.halvorteigen.assetoptimizer.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record PowerPriceDto(
    @JsonProperty("NOK_per_kWh")
    Double priceNokPerKwh,
    @JsonProperty("EUR_per_kWh")
    Double priceEurPerKwh,
    @JsonProperty("EXR")
    Double EXR,
    @JsonProperty("time_start")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    LocalDateTime timeStart,
    @JsonProperty("time_end")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
    LocalDateTime timeEnd
) {
    public PowerPriceDto {
        if (priceNokPerKwh < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (priceEurPerKwh < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
        if (EXR < 0) {
            throw new IllegalArgumentException("Exchange rate cannot be negative");
        }
        if (timeStart.isAfter(timeEnd)) {
            throw new IllegalArgumentException("Start time cannot be after end time");
        }
    }
}
