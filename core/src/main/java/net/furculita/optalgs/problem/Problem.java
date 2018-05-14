package net.furculita.optalgs.problem;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Particle;
import net.furculita.optalgs.individual.Vector;

import java.util.List;

public abstract class Problem {
    int dimension;
    private boolean maximize = false;

    public Problem(int dimension) {
        this(dimension, false);
    }

    public Problem(int dimension, boolean maximize) {
        this.dimension = dimension;
        this.maximize = maximize;
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

    public Vector randomVector() {
        double[] minPos = this.min();
        double[] maxPos = this.max();

        Vector position = new Vector(this.getDimension());
        for (int i = 0; i < this.getDimension(); i++) {
            position.add(i, Randoms.between(minPos[i], maxPos[i]));
        }

        return position;
    }
}
