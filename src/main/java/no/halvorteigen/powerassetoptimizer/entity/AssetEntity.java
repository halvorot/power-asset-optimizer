package no.halvorteigen.powerassetoptimizer.entity;

import jakarta.persistence.*;
import no.halvorteigen.powerassetoptimizer.enums.PriceArea;

@Entity
@Table(name = "ASSET")
public final class AssetEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    Long id;

    @Column(name = "NAME", nullable = false, unique = true)
    String name;

    @Column(name = "MIN_POWER_USAGE", nullable = false)
    Double minPowerUsage;

    @Column(name = "MAX_POWER_USAGE", nullable = false)
    Double maxPowerUsage;

    @Column(name = "TOTAL_ENERGY_USAGE_PER_24_HOURS", nullable = false)
    Double totalEnergyUsagePer24Hours;

    @Enumerated(EnumType.STRING)
    @Column(name = "PRICE_AREA", nullable = false)
    PriceArea priceArea;

    public AssetEntity() {
    }

    public AssetEntity(String name, Double minPowerUsage, Double maxPowerUsage, Double totalEnergyUsagePer24Hours, PriceArea priceArea) {
        this.name = name;
        this.minPowerUsage = minPowerUsage;
        this.maxPowerUsage = maxPowerUsage;
        this.totalEnergyUsagePer24Hours = totalEnergyUsagePer24Hours;
        this.priceArea = priceArea;
    }

    public String getName() {
        return name;
    }

    public Double getMinPowerUsage() {
        return minPowerUsage;
    }

    public Double getMaxPowerUsage() {
        return maxPowerUsage;
    }

    public Double getTotalEnergyUsagePer24Hours() {
        return totalEnergyUsagePer24Hours;
    }

    public PriceArea getPriceArea() {
        return priceArea;
    }
}


