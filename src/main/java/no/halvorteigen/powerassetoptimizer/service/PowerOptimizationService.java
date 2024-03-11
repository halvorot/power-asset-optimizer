package no.halvorteigen.powerassetoptimizer.service;

import no.halvorteigen.powerassetoptimizer.model.Asset;
import no.halvorteigen.powerassetoptimizer.model.PowerPrice;
import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;
import org.springframework.stereotype.Service;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

@Service
public class PowerOptimizationService {
    public static final int HOURS_PER_DAY = 24;
    private final PowerPriceService powerPriceService;
    private final Clock clock;

    public PowerOptimizationService(final PowerPriceService powerPriceService, Clock clock) {
        this.powerPriceService = powerPriceService;
        this.clock = clock;
    }

    public Map<Integer, Double> optimizePowerUsage(Asset asset) {

        // NOTE: Assuming min, max and total energy usage are valid and produce a feasible solution
        Double totalEnergyUsagePer24Hours = asset.totalEnergyUsagePer24Hours();
        Double minPowerUsage = asset.minPowerUsage();
        Double maxPowerUsage = asset.maxPowerUsage();

        LocalDateTime now = LocalDateTime.now(clock);
        List<PowerPrice> powerPrices = powerPriceService.getPowerPrices(
            now.getYear(),
            now.getMonthValue(),
            now.getDayOfMonth(),
            asset.priceArea()
        );

        /*
        Can set this up as a linear optimization problem
        Minimize f = sumFrom1To24(priceForHourX * powerUsageForHourX)
        subject to:
        sumFrom1To24(powerUsageForHourX) = totalEnergyUsagePer24Hours
        minPowerUsage <= powerUsageForHourX <= maxPowerUsage
        */
        double[] prices = powerPrices.stream().mapToDouble(PowerPrice::priceNokPerKwh).toArray();
        LinearObjectiveFunction objectiveFunction = new LinearObjectiveFunction(prices, 0);

        List<LinearConstraint> constraints = new ArrayList<>();

        double[] onesArray = DoubleStream.generate(() -> 1.0).limit(HOURS_PER_DAY).toArray();
        constraints.add(new LinearConstraint(onesArray, Relationship.EQ, totalEnergyUsagePer24Hours));

        for (int i = 0; i < HOURS_PER_DAY; i++) {
            double[] coefficients = new double[HOURS_PER_DAY];
            coefficients[i] = 1.0;
            constraints.add(new LinearConstraint(coefficients, Relationship.GEQ, minPowerUsage));
            constraints.add(new LinearConstraint(coefficients, Relationship.LEQ, maxPowerUsage));
        }

        SimplexSolver solver = new SimplexSolver();
        double[] solution = solver.optimize(
            new MaxIter(100),
            objectiveFunction,
            new LinearConstraintSet(constraints),
            GoalType.MINIMIZE,
            new NonNegativeConstraint(true) // NOTE: Assuming all power usage values must be non-negative
        ).getPoint();

        return IntStream.range(0, solution.length)
                        .boxed()
                        .collect(Collectors.toMap(index -> index, index -> solution[index]));
        // NOTE: May want to implement a semi optimal solution if the optimization fails,
        // or at least handle infeasible cases more gracefully
    }

}