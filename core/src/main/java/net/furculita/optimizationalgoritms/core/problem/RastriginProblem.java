package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Chromosome;
import net.furculita.optimizationalgoritms.core.individual.Individual;

public class RastriginProblem extends Problem {
    public RastriginProblem(int dimension) {
        super(dimension, -5.12, 5.12, 0.0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;

        for (Chromosome c : subject.getChromosomes()) {
            sum += Math.pow(c.asDecimal(), 2) - 10 * Math.cos(2 * Math.PI * c.asDecimal());
        }

        return 10 * this.dimension + sum;
    }
}
