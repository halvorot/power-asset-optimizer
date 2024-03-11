package no.halvorteigen.assetoptimizer.controller;

import no.halvorteigen.assetoptimizer.model.Asset;
import no.halvorteigen.assetoptimizer.registry.AssetRegistry;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * NOTE: The asset registers through this API
 */
@RestController
@RequestMapping("api/v1/asset")
public class AssetController {

    private final AssetRegistry assetRegistry;

    public AssetController(AssetRegistry assetRegistry) {
        this.assetRegistry = assetRegistry;
    }

    @PostMapping
    public ResponseEntity<Asset> registerAsset(@RequestBody Asset asset) {
        return ResponseEntity.ok().body(assetRegistry.register(asset));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Asset> removeAsset(@PathVariable String name) {
        Asset removedAsset = assetRegistry.removeByName(name);
        if (removedAsset == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(removedAsset);
    }

}
