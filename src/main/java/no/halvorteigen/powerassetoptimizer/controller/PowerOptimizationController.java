package no.halvorteigen.powerassetoptimizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.halvorteigen.powerassetoptimizer.dto.PowerUsageDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import no.halvorteigen.powerassetoptimizer.repository.AssetRepository;
import no.halvorteigen.powerassetoptimizer.service.PowerOptimizationService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.time.LocalDate;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/optimize")
public class PowerOptimizationController {

    private final AssetRepository assetRepository;
    private final PowerOptimizationService powerOptimizationService;
    private final Clock clock;

    public PowerOptimizationController(
        final AssetRepository assetRepository,
        final PowerOptimizationService powerOptimizationService,
        final Clock clock
    ) {
        this.assetRepository = assetRepository;
        this.powerOptimizationService = powerOptimizationService;
        this.clock = clock;
    }

    /**
     * Optimizes the power usage for the given asset for the given date. If no prices are found for the given
     * date, a default price is used.
     *
     * @param assetName The name of the asset to optimize
     * @param date      The date to optimize for. If not provided, defaults to tomorrow
     * @return A map of the optimized power usage for each hour of the day from midnight (0) to 23:00 (23)
     */
    @Operation(summary = "Optimize the power usage for a given asset")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", description = "Power usage optimized successfully",
                content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PowerUsageDto.class)
                )}
            ),
            @ApiResponse(
                responseCode = "404", description = "Asset not found", content = @Content
            )
        }
    )
    @GetMapping("/{assetName}")
    public ResponseEntity<PowerUsageDto> optimizeAssetPower(
        @PathVariable String assetName,
        @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date
    ) {
        Optional<AssetEntity> assetOpt = assetRepository.findByName(assetName);
        if (assetOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Map<Integer, Double> optimizedPowerUsage = powerOptimizationService.optimizePowerUsage(
            assetOpt.get(),
            date != null
                ? date
                : LocalDate.now(clock).plusDays(1)
        );
        return ResponseEntity.ok()
            .body(new PowerUsageDto(optimizedPowerUsage));
    }

}
