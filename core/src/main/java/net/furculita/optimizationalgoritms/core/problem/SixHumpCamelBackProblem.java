package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

public class SixHumpCamelBackProblem extends Problem {
    public SixHumpCamelBackProblem(int dimension) {
        super(dimension, rangeMin, rangeMax, min);
    }

    @Override
    public double fitness(Individual subject) {
        return 0;
    }
}
