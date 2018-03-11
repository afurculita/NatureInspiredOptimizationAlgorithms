package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.HashMap;
import java.util.Map;

public class SixHumpCamelBackProblem extends Problem {
    public SixHumpCamelBackProblem() {
        super(2, SixHumpCamelBackProblem.ranges());
    }

    @Override
    public double fitness(Individual subject) {
        double x1 = subject.getChromosomes().get(0).asDecimal();
        double x2 = subject.getChromosomes().get(1).asDecimal();

        return (4 - 2.1 * Math.pow(x1, 2) + Math.pow(x1, 4) / 3) * Math.pow(x1, 2)
                + x1 * x2
                + (-4 + 4 * Math.pow(x2, 2)) * Math.pow(x2, 2);
    }

    private static Map<Integer, DefinitionRange> ranges() {
        Map<Integer, DefinitionRange> ranges = new HashMap<>();

        ranges.put(0, new DefinitionRange(-3, 3));
        ranges.put(1, new DefinitionRange(-2, 2));

        return ranges;
    }
}
