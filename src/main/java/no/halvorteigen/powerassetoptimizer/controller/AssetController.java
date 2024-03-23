package no.halvorteigen.powerassetoptimizer.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import no.halvorteigen.powerassetoptimizer.dto.AssetDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import no.halvorteigen.powerassetoptimizer.mappers.MapAssetEntityToAssetDto;
import no.halvorteigen.powerassetoptimizer.service.AssetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/asset")
public class AssetController {

    private final AssetService assetService;

    public AssetController(AssetService assetService) {
        this.assetService = assetService;
    }

    @Operation(summary = "Register a new asset")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", description = "Asset registered successfully",
                content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssetDto.class)
                )}
            )}
    )
    @PostMapping
    public ResponseEntity<AssetDto> registerAsset(@RequestBody AssetDto asset) {
        AssetEntity assetEntity = assetService.registerAsset(asset);
        return ResponseEntity.ok()
            .body(MapAssetEntityToAssetDto.map(assetEntity));
    }

    @Operation(summary = "Update an existing asset")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", description = "Asset updated successfully",
                content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssetDto.class)
                )}
            ),
            @ApiResponse(
                responseCode = "404", description = "Asset not found", content = @Content
            )
        }
    )
    @PutMapping
    public ResponseEntity<AssetDto> updateAsset(@RequestBody AssetDto asset) {
        AssetEntity assetEntity = assetService.updateAsset(asset);
        if (assetEntity == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
            .body(MapAssetEntityToAssetDto.map(assetEntity));
    }

    @Operation(summary = "Unregister an existing asset")
    @ApiResponses(
        value = {
            @ApiResponse(
                responseCode = "200", description = "Asset unregistered successfully",
                content = {@Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = AssetDto.class)
                )}
            ),
            @ApiResponse(
                responseCode = "404", description = "Asset not found", content = @Content
            )
        }
    )
    @DeleteMapping("/{name}")
    public ResponseEntity<String> removeAsset(@PathVariable String name) {
        boolean isUnregistered = assetService.unregisterAsset(name);
        if (!isUnregistered) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
