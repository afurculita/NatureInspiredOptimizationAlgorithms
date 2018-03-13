package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public class AscentHillClimbing extends Algorithm {
    @Override
    public History solve(Problem problem) {
        History history = new History();

        Individual currentBest = Individual.potential(problem);

        while (true) {
            List<Individual> neighbours = neighbours(currentBest);
            boolean newBestFound = false;

            for (Individual neighbour : neighbours) {
                if (neighbour.betterThan(currentBest)) {
                    history.add(neighbour);
                    currentBest = neighbour;
                    newBestFound = true;

                    if (problem.optimumAchieved(currentBest.getFitness())) {
                        break;
                    }
                }
            }

            if (!newBestFound) {
                break;
            }
        }

        return history;
    }

    private List<Individual> neighbours(Individual individual) {
        List<Individual> neighbours = new ArrayList<>();

        List<Chromosome> genome = individual.getChromosomes();

        for (int i = 0; i < genome.size(); i++) {
            for (int j = 0; j < individual.getChromosomes().get(i).getInitialBitCount(); j++) {

                Individual neighbour = new Individual(individual);

                neighbour.getChromosomes().get(i).flip(j);

                neighbours.add(neighbour);
            }
        }

        return neighbours;
    }
}
