package no.halvorteigen.powerassetoptimizer.controller;

import no.halvorteigen.powerassetoptimizer.dto.AssetDto;
import no.halvorteigen.powerassetoptimizer.entity.AssetEntity;
import no.halvorteigen.powerassetoptimizer.mappers.MapAssetDtoToAssetEntity;
import no.halvorteigen.powerassetoptimizer.mappers.MapAssetEntityToAssetDto;
import no.halvorteigen.powerassetoptimizer.repository.AssetRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * NOTE: The asset registers through this API
 */
@RestController
@RequestMapping("api/v1/asset")
public class AssetController {

    private final AssetRepository assetRepository;

    public AssetController(AssetRepository assetRepository) {
        this.assetRepository = assetRepository;
    }

    @PostMapping
    public ResponseEntity<AssetDto> registerAsset(@RequestBody AssetDto asset) {
        AssetEntity assetEntity = assetRepository.save(MapAssetDtoToAssetEntity.map(asset));
        return ResponseEntity.ok()
                             .body(MapAssetEntityToAssetDto.map(assetEntity));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<String> removeAsset(@PathVariable String name) {
        if (!assetRepository.existsByName(name)) {
            return ResponseEntity.notFound().build();
        }
        assetRepository.deleteByName(name);
        return ResponseEntity.ok().build();
    }

}
