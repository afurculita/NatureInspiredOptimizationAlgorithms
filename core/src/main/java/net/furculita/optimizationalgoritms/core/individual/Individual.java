package net.furculita.optimizationalgoritms.core.individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.DoubleStream;

public class Individual implements Comparable<Individual> {
    private List<Double> genome = new ArrayList<>();
    private double fitness;

    public Individual(DoubleStream doubles) {
        this.setGenome(doubles);
    }

    public Individual(Individual individual) {
        this.genome = individual.genome;
    }

    public List<Double> getGenome() {
        return genome;
    }

    public void addGene(Double gene) {
        this.genome.add(gene);
    }

    public void setGenome(List<Double> genome) {
        this.genome = genome;
    }

    public void setGenome(DoubleStream doubles) {
        doubles.forEach(this::addGene);
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    @Override
    public int compareTo(Individual o) {
        return this.betterThan(o) ? 1 : -1;
    }

    public boolean betterThan(Individual o) {
        return this.fitness < o.fitness;
    }

    @Override
    public String toString() {
        return Double.toString(fitness);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Individual)) return false;
        Individual that = (Individual) o;
        return Double.compare(that.fitness, fitness) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(fitness);
    }
}
