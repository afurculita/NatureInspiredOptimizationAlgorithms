package net.furculita.optalgs.algorithm.crossover;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public class OneRandomChromosomeCrossover extends CrossoverStrategy {

    public OneRandomChromosomeCrossover(double crossoverRate) {
        super(crossoverRate);
    }

    public Individual crossover(Individual x, Individual y, Problem problem) {
        List<Chromosome> childChroms = new ArrayList<>();

        int selectedChromosomeIndex = Randoms.integer(x.getChromosomes().size());
        int selectedGeneIndex = Randoms.integer(x.getChromosomeSize());

        Individual first, second;
        if (Randoms.bool()) {
            first = x;
            second = y;
        } else {
            first = y;
            second = x;
        }

        for (int i = 0; i < x.getChromosomes().size(); i++) {
            if (i < selectedChromosomeIndex) {
                childChroms.add(Chromosome.clone(first.chromosome(i)));
            } else if (i > selectedChromosomeIndex) {
                childChroms.add(Chromosome.clone(second.chromosome(i)));
            } else {
                Chromosome middle = Chromosome.generateNewChromosome(x.getChromosomeSize());
                for (int j = 0; j < x.getChromosomeSize(); j++) {
                    if (j < selectedGeneIndex) {
                        middle.set(j, first.chromosome(i).get(j));
                    } else {
                        middle.set(j, second.chromosome(i).get(j));
                    }
                }
                childChroms.add(middle);
            }
        }

        return new Individual(childChroms, problem);
    }
}
