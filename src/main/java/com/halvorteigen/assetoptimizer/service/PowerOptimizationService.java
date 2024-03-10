package com.halvorteigen.assetoptimizer.service;

import com.halvorteigen.assetoptimizer.model.Asset;
import com.halvorteigen.assetoptimizer.model.PowerPrice;
import com.halvorteigen.assetoptimizer.registry.AssetRegistry;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PowerOptimizationService {
    public static final int HOURS_PER_DAY = 24;
    private final AssetRegistry assetRegistry;
    private final PowerPriceService powerPriceService;
    private final Clock clock;

    public PowerOptimizationService(final AssetRegistry assetRegistry, final PowerPriceService powerPriceService, Clock clock) {
        this.assetRegistry = assetRegistry;
        this.powerPriceService = powerPriceService;
        this.clock = clock;
    }

    public Map<Integer, Double> optimizePowerUsage(String assetName) {
        Map<Integer, Double> powerUsagePerHour = new HashMap<>();

        Asset asset = assetRegistry.getByName(assetName);
        // Assuming min, max and total energy usage are valid
        Double totalEnergyUsagePer24Hours = asset.totalEnergyUsagePer24Hours();
        Double minPowerUsage = asset.minPowerUsage();
        Double maxPowerUsage = asset.maxPowerUsage();

        LocalDateTime now = LocalDateTime.now(clock);
        List<PowerPrice> powerPrices = powerPriceService.getPowerPrices(
            now.getYear(),
            now.getMonthValue(),
            now.getDayOfMonth(),
            asset.priceArea()
        );
        Double sumPowerPrices = powerPrices.stream()
                                           .mapToDouble(PowerPrice::priceNokPerKwh)
                                           .sum();

        // Assuming that the optimization calculation method itself is not the most important part of this case
        /*
        Could set this up as a linear optimization problem
        Minimize f = sumFrom1To24(priceForHourX * powerUsageForHourX)
        subject to:
        sumFrom1To24(powerUsageForHourX) = totalEnergyUsagePer24Hours
        minPowerUsage <= powerUsageForHourX <= maxPowerUsage
        */
        for (int hour = 0; hour < HOURS_PER_DAY; hour++) {
            double priceFactor = powerPrices.get(hour).priceNokPerKwh() / sumPowerPrices;
            double optimizedPower = totalEnergyUsagePer24Hours * priceFactor;

            // Assuming that it is more important to keep within the min max range than to sum up to the total energy usage
            optimizedPower = Math.max(minPowerUsage, Math.min(optimizedPower, maxPowerUsage));

            powerUsagePerHour.put(hour, optimizedPower);
        }

        return powerUsagePerHour;
    }

}