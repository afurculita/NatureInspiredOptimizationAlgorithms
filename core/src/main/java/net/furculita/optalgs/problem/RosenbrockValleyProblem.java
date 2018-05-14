package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class RosenbrockValleyProblem extends Problem {
    private static final double MIN = -2.048;
    private static final double MAX = 2.048;

    public RosenbrockValleyProblem(int dimension) {
        super(dimension);
    }

    @Override
    public double evaluate(Individual subject) {
        double[] coordinates = subject.getChromosomes()
                .stream()
                .mapToDouble((Chromosome c) -> c.asBoundedDecimal(MIN, MAX))
                .toArray();

        return evaluate(coordinates);
    }

    @Override
    public double evaluate(double[] c) {
        double sum = 0;

        for (int i = 0; i < c.length - 1; i++) {
            sum += 100 * Math.pow(c[i + 1] - Math.pow(c[i], 2), 2) + Math.pow(1 - c[i], 2);
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

    @Override
    public int chromosomeSize() {
        return (int) Math.ceil(Math.log((MAX - MIN) * Math.pow(10, 0.01)));
    }

    @Override
    public double[] min() {
        double[] min = new double[this.dimension];

        for (int i = 0; i < dimension; i++) {
            min[i] = MIN;
        }

        return min;
    }

    @Override
    public double[] max() {
        double[] max = new double[this.dimension];

        for (int i = 0; i < dimension; i++) {
            max[i] = MAX;
        }

        return max;
    }
}
