package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Chromosome;
import net.furculita.optimizationalgoritms.core.individual.Individual;

public class GriewangkProblem extends Problem {
    public GriewangkProblem(int dimension) {
        super(dimension, -600, 600, 0.0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;

        for (Chromosome c : subject.getChromosomes()) {
            sum += Math.pow(c.asDecimal(), 2) / 4000;
        }

        double prod = 1;
        int i = 1;
        for (Chromosome c : subject.getChromosomes()) {
            prod *= Math.cos(c.asDecimal()) / Math.sqrt(i);
            i++;
        }

        return sum - prod + 1;
    }
}
