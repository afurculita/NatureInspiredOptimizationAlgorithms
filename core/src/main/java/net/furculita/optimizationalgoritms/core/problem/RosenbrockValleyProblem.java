package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Chromosome;
import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.List;

public class RosenbrockValleyProblem extends Problem {
    public RosenbrockValleyProblem(int dimension) {
        super(dimension, -2.048, 2.048, 0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;
        List<Chromosome> c = subject.getChromosomes();

        for (int i = 0; i < dimension - 1; i++) {
            sum += 100 * Math.pow(c.get(i + 1).asDecimal() - Math.pow(c.get(i).asDecimal(), 2), 2)
                    + Math.pow(1 - c.get(i).asDecimal(), 2);
        }

        return sum;
    }
}
