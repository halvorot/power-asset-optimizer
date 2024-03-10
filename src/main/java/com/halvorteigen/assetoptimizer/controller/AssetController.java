package com.halvorteigen.assetoptimizer.controller;

import com.halvorteigen.assetoptimizer.model.Asset;
import com.halvorteigen.assetoptimizer.registry.AssetRegistry;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/asset")
public class AssetController {

    private final AssetRegistry assetRegistry;

    public AssetController(AssetRegistry assetRegistry) {
        this.assetRegistry = assetRegistry;
    }

    @PostMapping
    public Asset registerAsset(@RequestBody Asset asset) {
        return assetRegistry.register(asset);
    }

    @DeleteMapping("/{name}")
    public Asset removeAsset(@PathVariable String name) {
        return assetRegistry.removeByName(name);
    }

}
