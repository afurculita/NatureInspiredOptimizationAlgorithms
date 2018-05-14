package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class GriewangkProblem extends Problem {
    private static final int MIN = -600;
    private static final int MAX = 600;

    public GriewangkProblem(int dimension) {
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
            sum += Math.pow(c, 2) / 4000;
        }

        double prod = 1;
        int i = 1;
        for (double c : coordinates) {
            prod *= Math.cos(c) / Math.sqrt(i);
            i++;
        }

        return sum - prod + 1;
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
