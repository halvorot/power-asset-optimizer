package com.halvorteigen.assetoptimizer.registry;

import com.halvorteigen.assetoptimizer.model.Asset;
import jakarta.annotation.Nonnull;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class AssetRegistry {

    private final List<Asset> assets = new ArrayList<>();

    public List<Asset> getAll() {
        return assets;
    }

    @Nonnull
    public Asset getByName(String name) {
        return assets.stream()
                     .filter(asset -> asset.name().equals(name))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("No asset with name " + name + " found"));
    }

    public Asset register(Asset asset) {
        if (assets.stream().anyMatch(a -> a.name().equals(asset.name()))) {
            throw new IllegalArgumentException("Asset with name " + asset.name() + " already exists");
        }
        assets.add(asset);
        return asset;
    }

    public Asset removeByName(String name) {
        Asset asset = getByName(name);
        assets.remove(asset);
        return asset;
    }

    public void removeAll() {
        assets.clear();
    }


}
