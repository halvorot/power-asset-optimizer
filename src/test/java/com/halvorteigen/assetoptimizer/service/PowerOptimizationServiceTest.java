package com.halvorteigen.assetoptimizer.service;

import com.halvorteigen.assetoptimizer.enums.PriceArea;
import com.halvorteigen.assetoptimizer.model.Asset;
import com.halvorteigen.assetoptimizer.registry.AssetRegistry;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

class PowerOptimizationServiceTest {

    @Test
    void should_optimize_power() {
        // Arrange
        AssetRegistry assetRegistry = new AssetRegistry();
        double totalEnergyUsagePer24Hours = 2231.0;
        double minPowerUsage = 10.0;
        double maxPowerUsage = 100.0;
        assetRegistry.register(
            new Asset("asset1", totalEnergyUsagePer24Hours, minPowerUsage, maxPowerUsage, PriceArea.NO1)
        );
        PowerPriceService powerPriceService = new PowerPriceService(new RestTemplate());
        Clock clock = Clock.fixed(Instant.parse("2024-03-10T14:22:00Z"), ZoneId.of("Europe/Oslo"));

        PowerOptimizationService powerOptimizationService = new PowerOptimizationService(assetRegistry, powerPriceService, clock);

        // Act
        Map<Integer, Double> optimizePowerUsage = powerOptimizationService.optimizePowerUsage("asset1");

        // Assert
        System.out.println(optimizePowerUsage);
        System.out.println(optimizePowerUsage.values().stream().mapToDouble(Double::doubleValue).sum());
        assertThat(optimizePowerUsage).hasSize(24);
        assertThat(optimizePowerUsage.values().stream().mapToDouble(Double::doubleValue).sum())
            .isLessThanOrEqualTo(totalEnergyUsagePer24Hours);
        assertThat(optimizePowerUsage.values()).allSatisfy(power -> {
            assertThat(power).isGreaterThanOrEqualTo(minPowerUsage);
            assertThat(power).isLessThanOrEqualTo(maxPowerUsage);
        });

    }
}