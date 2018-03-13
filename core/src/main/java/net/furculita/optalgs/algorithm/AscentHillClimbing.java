package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;
import java.util.List;

public class AscentHillClimbing extends Algorithm {
    private static final int ITERATIONS = 100;

    @Override
    public History solve(Problem problem) {
        History history = new History();

        Individual currentBest = Individual.potential(problem);

        int it = 0;

        do {
            List<Individual> neighbours = neighbours(currentBest);

            for (Individual neighbour : neighbours) {
                if (neighbour.betterThan(currentBest)) {
                    history.add(neighbour);
                    currentBest = neighbour;

                    if (problem.optimumAchieved(currentBest.getFitness())) {
                        break;
                    }
                }
            }

            it++;
        } while (it <= ITERATIONS);

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
