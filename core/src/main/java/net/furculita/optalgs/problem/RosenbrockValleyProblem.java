package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class RosenbrockValleyProblem extends Problem {
    private static final double MIN = -2.048;
    private static final double MAX = 2.048;

    public RosenbrockValleyProblem(int dimension) {
        super(dimension, 0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;
        List<Chromosome> c = subject.getChromosomes();

        for (int i = 0; i < dimension - 1; i++) {
            sum += 100 * Math.pow(c.get(i + 1).asBoundedDecimal(MIN, MAX)
                    - Math.pow(c.get(i).asBoundedDecimal(MIN, MAX), 2), 2)
                    + Math.pow(1 - c.get(i).asBoundedDecimal(MIN, MAX), 2);
        }

        return sum;
    }

    @Override
    public String solutionsToString(List<Chromosome> solution) {
        return solution
                .stream()
                .map((Chromosome c) -> Double.toString(c.asBoundedDecimal(MIN, MAX)))
                .collect(Collectors.joining(", "));
    }
}
