package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

import java.util.*;

public abstract class Problem {
    protected int dimension;
    private Double min;

    private Map<Integer, DefinitionRange> ranges = new HashMap<>();

    public Problem(int dimension, double rangeMin, double rangeMax) {
        this(dimension, rangeMin, rangeMax, Double.NaN);
    }

    public Problem(int dimension, Map<Integer, DefinitionRange> ranges) {
        this.dimension = dimension;
        this.ranges = ranges;
    }

    public Problem(int dimension, double rangeMin, double rangeMax, double min) {
        this.dimension = dimension;
        this.min = min;

        DefinitionRange uniqueRange = new DefinitionRange(rangeMin, rangeMax);

        for (Integer i = 0; i < dimension; i++) {
            this.ranges.put(i, uniqueRange);
        }
    }

    public Individual potential() {
        List<Double> randoms = new ArrayList<>(this.dimension);

        for (int i = 0; i < this.dimension; i++) {
            randoms.add(i, ranges.get(i).randomBetweenBounds());
        }

        Individual potential = new Individual(randoms);

        potential.setFitness(this.fitness(potential));

        return potential;
    }

    public abstract double fitness(Individual subject);

    public boolean optimumAchieved(Double d) {
        return d.equals(min);
    }
}
