package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;
import java.util.stream.Collectors;

public class GriewangkProblem extends Problem {
    private static final int MIN = -600;
    private static final int MAX = 600;

    public GriewangkProblem(int dimension) {
        super(dimension, 0.0);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;

        for (Chromosome c : subject.getChromosomes()) {
            sum += Math.pow(c.asBoundedDecimal(MIN, MAX), 2) / 4000;
        }

        double prod = 1;
        int i = 1;
        for (Chromosome c : subject.getChromosomes()) {
            prod *= Math.cos(c.asBoundedDecimal(MIN, MAX)) / Math.sqrt(i);
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
}
