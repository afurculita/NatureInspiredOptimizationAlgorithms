package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class RastriginProblem extends Problem {
    private static final double MIN = -5.12;
    private static final double MAX = 5.12;

    public RastriginProblem(int dimension) {
        super(dimension, 0.0);
    }

    @Override
    public double func(Individual subject) {
        double sum = 0;

        for (Chromosome c : subject.getChromosomes()) {
            sum += (Math.pow(c.asBoundedDecimal(MIN, MAX), 2) - 10 * Math.cos(2 * Math.PI * c.asBoundedDecimal(MIN, MAX)));
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
        return (int) Math.ceil(Math.log((MAX - MIN) * Math.pow(10, 0.01)));
    }
}
