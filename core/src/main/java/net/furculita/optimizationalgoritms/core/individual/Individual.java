package net.furculita.optimizationalgoritms.core.individual;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Individual implements Comparable<Individual> {
    private static final int CHROMOSOME_SIZE = 5;

    private List<Chromosome> chromosomes = new ArrayList<>();
    private double fitness;

    public Individual(List<Chromosome> doubles) {
        this.setChromosomes(doubles);
    }

    public Individual(Individual individual) {
        chromosomes.clear();

        for (Chromosome c : individual.getChromosomes()) {
            chromosomes.add(Chromosome.clone(c));
        }
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public void setChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
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
        return "F("
                + chromosomes
                .stream()
                .map((Chromosome c) -> Double.toString(c.asDecimal()))
                .collect(Collectors.joining(", "))
                + ")="
                + Double.toString(fitness);
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

    public static Individual generateNewIndividual(int dimension) {
        List<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < dimension; i++) {
            chromosomes.add(Chromosome.generateNewChromosome(CHROMOSOME_SIZE));
        }

        return new Individual(chromosomes);
    }
}
