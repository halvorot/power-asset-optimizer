package no.halvorteigen.powerassetoptimizer.controller;

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

    @PostMapping
    public ResponseEntity<AssetDto> registerAsset(@RequestBody AssetDto asset) {
        AssetEntity assetEntity = assetService.registerAsset(asset);
        return ResponseEntity.ok()
            .body(MapAssetEntityToAssetDto.map(assetEntity));
    }

    @PutMapping
    public ResponseEntity<AssetDto> updateAsset(@RequestBody AssetDto asset) {
        AssetEntity assetEntity = assetService.updateAsset(asset);
        return ResponseEntity.ok()
            .body(MapAssetEntityToAssetDto.map(assetEntity));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> removeAsset(@PathVariable String name) {
        boolean isUnregistered = assetService.unregisterAsset(name);
        if (!isUnregistered) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().build();
    }

}
