package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class RastriginProblem extends Problem {
    private static final double MIN = -5.12;
    private static final double MAX = 5.12;

    public RastriginProblem(int dimension) {
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
    public double evaluate(double[] coordinates) {
        double sum = 0;

        for (double c : coordinates) {
            sum += (Math.pow(c, 2) - 10 * Math.cos(2 * Math.PI * c));
        }

        return 10 * this.dimension + sum;
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
        return 10;
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
