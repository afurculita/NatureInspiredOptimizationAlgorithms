package net.furculita.optalgs.algorithm.crossover;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.individual.Population;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public abstract class CrossoverStrategy {
    private final double crossoverRate;

    CrossoverStrategy(double crossoverRate) {
        this.crossoverRate = crossoverRate;
    }

    public Population crossover(Population population, Problem problem) {
        return tournamentSelectionOfParents(population, problem);
    }

    private Population tournamentSelectionOfParents(Population population, Problem problem) {
        Population crossedPop = new Population(population);
        Individual mum;
        Individual dad;

        while (crossedPop.size() < population.size()) {
            do {
                mum = population.tournamentSelection();
                dad = population.tournamentSelection();
            } while (mum.getFitness() == dad.getFitness());

            crossedPop.add(crossover(mum, dad, problem));
        }

        population.addAll(crossedPop);

        return population;
    }

    private Population parentsAndChildren(Population population, Problem problem) {
        Population crossedPop = new Population(population);
        List<Integer> selectedIndexes = new ArrayList<>();

        for (int i = 0; i < population.size(); i++) {
            if (Randoms.percentage() < crossoverRate) {
                selectedIndexes.add(i);
            }
        }

        while (selectedIndexes.size() > 1) {
            int randomInt = Randoms.integer(selectedIndexes.size());
            int index1 = selectedIndexes.get(randomInt);
            selectedIndexes.remove(Integer.valueOf(index1));

            randomInt = Randoms.integer(selectedIndexes.size());
            int index2 = selectedIndexes.get(randomInt);
            selectedIndexes.remove(Integer.valueOf(index2));

            Individual crossoverResult = crossover(population.get(index1), population.get(index2), problem);
            crossedPop.add(Individual.clone(crossoverResult));
        }

        return crossedPop;
    }

    public abstract Individual crossover(Individual ind1, Individual ind2, Problem problem);
}
