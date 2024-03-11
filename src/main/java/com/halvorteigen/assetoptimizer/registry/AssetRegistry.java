package com.halvorteigen.assetoptimizer.registry;

import com.halvorteigen.assetoptimizer.model.Asset;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * NOTE: May be replaced with a database or other persistent storage
 */
@Repository
public class AssetRegistry {

    private final Map<String, Asset> assets = new HashMap<>();

    public Asset getByName(String name) {
        return assets.get(name);
    }

    public Asset register(Asset asset) {
        if (assets.containsKey(asset.name())) {
            throw new IllegalArgumentException("Asset with name " + asset.name() + " already exists");
        }
        assets.put(asset.name(), asset);
        return asset;
    }

    public Asset removeByName(String name) {
        return assets.remove(name);
    }

}
