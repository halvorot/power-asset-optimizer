package com.halvorteigen.assetoptimizer.controller;

import com.halvorteigen.assetoptimizer.service.PowerOptimizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class PowerOptimizationController {

    private final PowerOptimizationService powerOptimizationService;

    public PowerOptimizationController(PowerOptimizationService powerOptimizationService) {
        this.powerOptimizationService = powerOptimizationService;
    }

    @GetMapping("/optimize/{assetName}")
    public Map<Integer, Double> optimizeAssetPower(@PathVariable String assetName) {
        Map<Integer, Double> optimizedPowerUsage = powerOptimizationService.optimizePowerUsage(assetName);
        System.out.println(optimizedPowerUsage);
        return optimizedPowerUsage;
    }

}
