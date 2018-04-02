package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Particle;

import java.util.List;

public abstract class Problem {
    int dimension;
    private Double min;
    private boolean maximize = false;

    public Problem(int dimension, double min) {
        this(dimension, min, false);
    }

    public Problem(int dimension, double min, boolean maximize) {
        this.dimension = dimension;
        this.min = min;
        this.maximize = maximize;
    }

    public Problem(int dimension) {
        this.dimension = dimension;
    }

    public int getDimension() {
        return dimension;
    }

    public abstract double evaluate(Individual subject);

    public double evaluate(Particle subject) {
        double[] coordinates = subject.getPosition().arr();

        return evaluate(coordinates);
    }

    public abstract double evaluate(double coordinates[]);

    public boolean isBetterThan(double fitness, double otherValue) {
        if (maximize) {
            return otherValue > fitness;
        } else {
            return otherValue < fitness;
        }
    }

    public abstract String solutionsToString(List<Chromosome> solution);

    public boolean optimumAchieved(Double d) {
        return d.equals(min);
    }

    public abstract int chromosomeSize();

    public double[] min() {
        double[] min = new double[this.dimension];

        for (int i = 0; i < dimension; i++) {
            min[i] = Double.NEGATIVE_INFINITY;
        }

        return min;
    }

    public double[] max() {
        double[] max = new double[this.dimension];

        for (int i = 0; i < dimension; i++) {
            max[i] = Double.POSITIVE_INFINITY;
        }

        return max;
    }
}
