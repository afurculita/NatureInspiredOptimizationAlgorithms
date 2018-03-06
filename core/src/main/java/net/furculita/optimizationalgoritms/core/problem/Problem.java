package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.Random;

public abstract class Problem {
    protected int dimension;
    private double rangeMin;
    private double rangeMax;
    private Double min;

    public Problem(int dimension, double rangeMin, double rangeMax) {
        this.dimension = dimension;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
    }

    public Problem(int dimension, double rangeMin, double rangeMax, double min) {
        this.dimension = dimension;
        this.rangeMin = rangeMin;
        this.rangeMax = rangeMax;
        this.min = min;
    }

    public Individual potential() {
        Individual potential = new Individual(new Random().doubles(this.dimension, rangeMin, rangeMax));

        potential.setFitness(this.fitness(potential));

        return potential;
    }

    public boolean isValid(double gene) {
        return gene <= this.rangeMax && gene >= this.rangeMin;
    }

    public abstract double fitness(Individual subject);

    public boolean optimumAchieved(Double d) {
        return min != null && d.equals(min);
    }
}
