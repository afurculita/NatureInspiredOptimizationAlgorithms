package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Population;
import net.furculita.optalgs.problem.Problem;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * http://ijarcsse.com/Before_August_2017/docs/papers/Volume_3/12_December2013/V3I12-0158.pdf
 */
public class MemeticAlgorithm extends GeneticAlgorithm {
    private double hybridFactor;
    private int climbIterations;

    public MemeticAlgorithm(double crossOverRate, double mutationRate, double hybridFactor, int climbIterations) {
        super(crossOverRate, mutationRate);
        this.hybridFactor = hybridFactor;
        this.climbIterations = climbIterations;
    }

    public MemeticAlgorithm(double crossOverRate, double mutationRate) {
        this(crossOverRate, mutationRate, 0.3, 10);
    }

    @Override
    protected Population nextGeneration(Problem problem, Population population, Individual currentBest) {
        population = doSelection(population, currentBest);
        population = doMutation(population);
        population = doCrossover(population, problem);
        population = doHillClimbing(population);

        return population;
    }

    @NotNull
    private Population doHillClimbing(Population population) {
        Population newPop = new Population();
        Random random = new Random();

        for (Individual ind : population) {
            if (random.nextDouble() < this.hybridFactor) {
                newPop.add(getBetterNeighbour(ind));
            } else {
                newPop.add(new Individual(ind));
            }
        }

        return newPop;
    }

    private Individual getBetterNeighbour(Individual individual) {
        Individual best = individual;
        int it = 0;

        do {
            Individual mutated = Individual.mutateOneRandomGeneFromOneRandomChromosome(best);

            if (mutated.betterThan(best)) {
                best = mutated;
                it = 0;
            }

            it++;
        } while (it < climbIterations);

        return best;
    }
}
