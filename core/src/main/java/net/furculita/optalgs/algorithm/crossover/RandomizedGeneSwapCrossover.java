package net.furculita.optalgs.algorithm.crossover;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public class RandomizedGeneSwapCrossover extends CrossoverStrategy {
    private final double uniformRate;

    public RandomizedGeneSwapCrossover(double crossoverRate, double uniformRate) {
        super(crossoverRate);
        this.uniformRate = uniformRate;
    }

    @Override
    public Individual crossover(Individual x, Individual y, Problem problem) {
        List<Chromosome> childChroms = new ArrayList<>();

        for (int i = 0; i < x.getChromosomes().size(); i++) {
            Chromosome newChromosome = Chromosome.generateNewChromosome(x.getChromosomeSize());
            for (int j = 0; j < x.getChromosomeSize(); j++) {
                if (Randoms.percentage() < uniformRate) {
                    newChromosome.set(j, x.chromosome(i).get(j));
                } else {
                    newChromosome.set(j, y.chromosome(i).get(j));
                }
            }
            childChroms.add(newChromosome);
        }

        return new Individual(childChroms, problem);
    }
}
