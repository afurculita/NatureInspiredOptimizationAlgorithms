package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

public class RastriginProblem extends Problem {
    public RastriginProblem(int dimension) {
        super(dimension, -5.12, 5.12, 0.0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;

        for (double gene : subject.getGenome()) {
            sum += Math.pow(gene, 2) - 10 * Math.cos(2 * Math.PI * gene);
        }

        return 10 * this.dimension + sum;
    }
}
