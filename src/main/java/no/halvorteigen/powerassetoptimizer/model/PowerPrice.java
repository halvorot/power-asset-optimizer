package no.halvorteigen.powerassetoptimizer.model;

public record PowerPrice(
    Double priceNokPerKwh
) {
    public PowerPrice {
        if (priceNokPerKwh < 0) {
            throw new IllegalArgumentException("Price cannot be negative");
        }
    }
}

