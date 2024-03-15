package no.halvorteigen.powerassetoptimizer.controller;

import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import no.halvorteigen.powerassetoptimizer.repository.AssetRepository;
import no.halvorteigen.powerassetoptimizer.service.PowerOptimizationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/optimize")
public class PowerOptimizationController {

    private final AssetRepository assetRepository;
    private final PowerOptimizationService powerOptimizationService;

    public PowerOptimizationController(AssetRepository assetRepository, PowerOptimizationService powerOptimizationService) {
        this.assetRepository = assetRepository;
        this.powerOptimizationService = powerOptimizationService;
    }

    @GetMapping("/{assetName}")
    public ResponseEntity<Map<Integer, Double>> optimizeAssetPower(@PathVariable String assetName) {
        Optional<AssetEntity> assetOpt = assetRepository.findByName(assetName);
        if (assetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<Integer, Double> optimizedPowerUsage = powerOptimizationService.optimizePowerUsage(assetOpt.get());
        return ResponseEntity.ok()
                             .body(optimizedPowerUsage);
    }

}
