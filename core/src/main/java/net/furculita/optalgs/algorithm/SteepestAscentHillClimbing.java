package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.Problem;

import java.util.List;

/**
 * Best-improvement Hill Climbing.
 *
 * The entire neighborhood is explored and the best solution is returned
 *
 * http://www.cs.stir.ac.uk/~goc/papers/PPSN10FirstImprLON.pdf
 */
public class SteepestAscentHillClimbing extends Algorithm {
    @Override
    public History solve(Problem problem) {
        History history = new History();

        Individual currentBest = Individual.generateNewIndividual(problem);
        history.add(currentBest);

        boolean newBestFound;

        do {
            newBestFound = false;
            Individual neighbour = bestNeighbour(currentBest);

            if (neighbour.betterThan(currentBest)) {
                history.add(neighbour);
                currentBest = neighbour;

                newBestFound = true;
            }
        } while (newBestFound);

        return history;
    }

    private Individual bestNeighbour(Individual individual) {
        List<Chromosome> genome = individual.getChromosomes();
        Individual best = null;

        for (int i = 0; i < genome.size(); i++) {
            for (int j = 0; j < individual.getChromosomes().get(i).bitsNr(); j++) {

                Individual neighbour = new Individual(individual);

                neighbour.getChromosomes().get(i).flip(j);

                if (best == null || neighbour.betterThan(best)) {
                    best = neighbour;
                }
            }
        }

        return best;
    }

    @Override
    public String toString() {
        return "SteepestAscentHillClimbing{}";
    }
}
