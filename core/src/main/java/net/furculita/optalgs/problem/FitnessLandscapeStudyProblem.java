package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class FitnessLandscapeStudyProblem extends Problem {
    private static final int MIN = 0;
    private static final int MAX = 31;

    public FitnessLandscapeStudyProblem() {
        super(1);
    }

    @Override
    public double evaluate(Individual subject) {
        Chromosome c = subject.getChromosomes().get(0);

        return evaluate(new double[]{c.asBoundedDecimal(MIN, MAX)});
    }

    @Override
    public double evaluate(double[] coordinates) {
        double x = coordinates[0];

        return 1 / (Math.pow(x, 3) - 60 * Math.pow(x, 2) + 900 * x + 100);
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
        return 5;
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
