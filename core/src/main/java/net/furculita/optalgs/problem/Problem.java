package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;

public abstract class Problem {
    protected int dimension;
    private Double min;

    public Problem(int dimension, double min) {
        this.dimension = dimension;
        this.min = min;
    }

    public Problem(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }

    public abstract double fitness(Individual subject);

    public abstract String solutionsToString(List<Chromosome> solution);

    public boolean optimumAchieved(Double d) {
        return d.equals(min);
    }
}
