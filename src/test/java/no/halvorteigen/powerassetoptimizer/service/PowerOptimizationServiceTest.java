package no.halvorteigen.powerassetoptimizer.service;

import no.halvorteigen.powerassetoptimizer.dto.PowerPriceDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import no.halvorteigen.powerassetoptimizer.enums.PriceArea;
import org.apache.commons.math3.optim.linear.NoFeasibleSolutionException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;

import java.time.*;
import java.util.Map;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
class PowerOptimizationServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @Test
    void should_optimize_power() {
        // Arrange
        double totalEnergyUsagePer24Hours = 1924.0;
        double minPowerUsage = 10.0;
        double maxPowerUsage = 100.0;
        AssetEntity asset1 = new AssetEntity(
            "asset1",
            minPowerUsage,
            maxPowerUsage,
            totalEnergyUsagePer24Hours,
            PriceArea.NO1
        );
        PowerPriceService powerPriceService = new PowerPriceService(restTemplate);
        Clock clock = Clock.fixed(Instant.parse("2024-03-10T14:22:00Z"), ZoneId.of("Europe/Oslo"));

        PowerPriceDto[] powerPriceDtos = Stream
            .generate(() -> new PowerPriceDto(
                0.123,
                0.0123,
                10.0,
                LocalDateTime.now(clock),
                LocalDateTime.now(clock).plusHours(1)
            ))
            .limit(24)
            .toArray(PowerPriceDto[]::new);
        Mockito.when(restTemplate.getForObject(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(PowerPriceDto[].class)
            ))
            .thenReturn(powerPriceDtos);

        PowerOptimizationService powerOptimizationService = new PowerOptimizationService(
            powerPriceService
        );

        // Act
        Map<Integer, Double> optimizePowerUsage = powerOptimizationService.optimizePowerUsage(
            asset1,
            LocalDate.now(clock)
        );

        // Assert
        assertThat(optimizePowerUsage).hasSize(24);
        assertThat(optimizePowerUsage.values().stream().mapToDouble(Double::doubleValue).sum())
            .isEqualTo(totalEnergyUsagePer24Hours);
        assertThat(optimizePowerUsage.values()).allSatisfy(power -> {
            assertThat(power).isGreaterThanOrEqualTo(minPowerUsage);
            assertThat(power).isLessThanOrEqualTo(maxPowerUsage);
        });
    }

    @Test
    void should_throw_exception_when_infeasible_optimization_problem() {
        // Arrange
        double totalEnergyUsagePer24Hours = 3000.0;
        double minPowerUsage = 10.0;
        double maxPowerUsage = 100.0;
        AssetEntity asset1 = new AssetEntity(
            "asset1",
            minPowerUsage,
            maxPowerUsage,
            totalEnergyUsagePer24Hours,
            PriceArea.NO1
        );
        PowerPriceService powerPriceService = new PowerPriceService(restTemplate);
        Clock clock = Clock.fixed(Instant.parse("2024-03-10T14:22:00Z"), ZoneId.of("Europe/Oslo"));
        LocalDate dateNow = LocalDate.now(clock);

        PowerPriceDto[] powerPriceDtos = Stream
            .generate(() -> new PowerPriceDto(
                0.123,
                0.0123,
                10.0,
                LocalDateTime.now(clock),
                LocalDateTime.now(clock).plusHours(1)
            ))
            .limit(24)
            .toArray(PowerPriceDto[]::new);
        Mockito.when(restTemplate.getForObject(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.eq(PowerPriceDto[].class)
            ))
            .thenReturn(powerPriceDtos);

        PowerOptimizationService powerOptimizationService = new PowerOptimizationService(
            powerPriceService
        );

        // Act & Assert
        assertThatThrownBy(() -> powerOptimizationService.optimizePowerUsage(asset1, dateNow))
            .isInstanceOf(NoFeasibleSolutionException.class)
            .hasMessageContaining("no feasible solution");
    }
}