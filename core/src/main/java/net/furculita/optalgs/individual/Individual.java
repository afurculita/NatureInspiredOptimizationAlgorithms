package net.furculita.optalgs.individual;

import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Individual implements Comparable<Individual> {
    private static final int CHROMOSOME_SIZE = 5;

    private List<Chromosome> chromosomes = new ArrayList<>();
    private double fitness = Double.NaN;
    private Problem problem;

    public Individual(List<Chromosome> doubles, Problem problem) {
        this.problem = problem;
        this.setChromosomes(doubles);
    }

    public Individual(Individual individual) {
        this.problem = individual.problem;
        chromosomes.clear();

        for (Chromosome c : individual.getChromosomes()) {
            chromosomes.add(Chromosome.clone(c));
        }
    }

    public List<Chromosome> getChromosomes() {
        return chromosomes;
    }

    public Chromosome chromosome(int index) {
        return chromosomes.get(index);
    }

    public int getChromosomeSize() {
        return CHROMOSOME_SIZE;
    }

    public void setChromosomes(List<Chromosome> chromosomes) {
        this.chromosomes = chromosomes;
    }

    public double getFitness() {
        if (Double.isNaN(fitness)) {
            fitness = problem.fitness(this);
        }

        return fitness;
    }

    @Override
    public int compareTo(Individual o) {
        return this.betterThan(o) ? 1 : -1;
    }

    public boolean betterThan(Individual o) {
        return o == null || this.getFitness() < o.getFitness();
    }

    @Override
    public String toString() {
        return "F("
                + problem.solutionsToString(chromosomes)
                + ")="
                + Double.toString(getFitness());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Individual)) return false;
        Individual that = (Individual) o;
        return Double.compare(that.getFitness(), getFitness()) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getFitness());
    }

    public static Individual generateNewIndividual(Problem problem) {
        List<Chromosome> chromosomes = new ArrayList<>();

        for (int i = 0; i < problem.getDimension(); i++) {
            chromosomes.add(Chromosome.generateNewChromosome(CHROMOSOME_SIZE));
        }

        return new Individual(chromosomes, problem);
    }

    public static Individual potential(Problem problem) {
        return Individual.generateNewIndividual(problem);
    }

    public static Individual clone(Individual ind) {
        return new Individual(ind);
    }

    public static Individual mutateOneRandomGeneFromOneRandomChromosome(Individual parent) {
        Individual mutated = new Individual(parent);
        List<Chromosome> genome = mutated.getChromosomes();

        Random randomGenerator = new Random();

        int randomGeneIndex = randomGenerator.nextInt(genome.size());

        Chromosome mutatedGene = genome.get(randomGeneIndex);
        mutatedGene.flip(randomGenerator.nextInt(mutatedGene.getInitialBitCount()));

        genome.set(randomGeneIndex, mutatedGene);
        mutated.setChromosomes(genome);

        return mutated;
    }
}