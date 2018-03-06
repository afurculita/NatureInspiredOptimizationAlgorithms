package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.List;

public class RosenbrockValleyProblem extends Problem {
    public RosenbrockValleyProblem(int dimension) {
        super(dimension, -2.048, 2.048, 0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;
        List<Double> genome = subject.getGenome();

        for (int i = 0; i < dimension - 1; i++) {
            sum += 100 * Math.pow(genome.get(i + 1) - Math.pow(genome.get(i), 2), 2) + Math.pow(1 - genome.get(i), 2);
        }

        return sum;
    }
}
