package no.halvorteigen.powerassetoptimizer.dto;

import java.util.Map;

public record PowerUsageDto(
    Map<Integer, Double> powerUsage
) {
}
