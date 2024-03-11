package com.halvorteigen.assetoptimizer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Assumptions:
 * - Tests are not a priority for this case
 * - The asset name is unique and can be used as an identifier
 * - All power usage values must be non-negative, i.e. Assets are consumers and not generators
 * - Using an in-memory registry is sufficient for this case
 * - We optimize based on the power price in NOK
 * - Error handling and logging are not prioritized in this case
 * <p>
 * Other assumptions are marked with 'NOTE:' in the code
 */

@SpringBootApplication
public class AssetOptimizerApplication {

    public static void main(String[] args) {
        SpringApplication.run(AssetOptimizerApplication.class, args);
    }

}
