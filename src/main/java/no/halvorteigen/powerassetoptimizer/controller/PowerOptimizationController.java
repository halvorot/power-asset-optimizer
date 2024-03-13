package no.halvorteigen.powerassetoptimizer.controller;

import no.halvorteigen.powerassetoptimizer.registry.AssetRegistry;
import no.halvorteigen.powerassetoptimizer.service.PowerOptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * NOTE: The asset polls its optimized power usage through this API
 */
@RestController
@RequestMapping("api/v1/optimize")
public class PowerOptimizationController {

    private final AssetRegistry assetRegistry;
    private final PowerOptimizationService powerOptimizationService;

    public PowerOptimizationController(AssetRegistry assetRegistry, PowerOptimizationService powerOptimizationService) {
        this.assetRegistry = assetRegistry;
        this.powerOptimizationService = powerOptimizationService;
    }

    // NOTE: Assuming that returning a Map instead of a list is ok. Can easily be converted to a list if needed.
    @GetMapping("/{assetName}")
    public ResponseEntity<Map<Integer, Double>> optimizeAssetPower(@PathVariable String assetName) {
        Asset asset = assetRegistry.getByName(assetName);
        if (asset == null) {
            return ResponseEntity.notFound().build();
        }
        Map<Integer, Double> optimizedPowerUsage = powerOptimizationService.optimizePowerUsage(asset);
        return ResponseEntity.ok().body(optimizedPowerUsage);
    }

}
