package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.Random;

public abstract class Problem {
    protected int dimension;
    private double rangeMin;
    private double rangeMax;

    public Problem(int dimension, double rangeMin, double rangeMax) {
        this.dimension = dimension;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
    }

    public Individual potential() {
        Individual potential = new Individual(new Random().doubles(this.dimension, rangeMin, rangeMax));

        potential.setFitness(this.fitness(potential));

        return potential;
    }

    public abstract double fitness(Individual subject);

    public Individual neighbour() {
        return null;
    }

    public int getIterations() {
        return 100 * this.dimension;
    }
}
