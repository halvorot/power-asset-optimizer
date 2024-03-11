package no.halvorteigen.assetoptimizer.model;

import java.time.LocalDateTime;


/**
 * Choosing to create a separate record in addition to the DTO to separate the data model from the
 * DTO, even though they are almost identical.
 */
public record PowerPrice(
    Double priceNokPerKwh,
    Double priceEurPerKwh,
    Double EXR,
    LocalDateTime timeStart,
    LocalDateTime timeEnd
) {
    public PowerPrice {
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

