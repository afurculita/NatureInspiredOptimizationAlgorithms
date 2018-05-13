package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.Problem;
import net.furculita.optalgs.problem.StateResult;

import java.util.List;

/**
 * Best-improvement Hill Climbing.
 * <p>
 * The entire neighborhood is explored and the best solution is returned
 * <p>
 * http://www.cs.stir.ac.uk/~goc/papers/PPSN10FirstImprLON.pdf
 */
public class SteepestAscentHillClimbing extends Algorithm {
    @Override
    public StateResult solve(Problem problem) {
        StateResult stateResult = new StateResult();

        Individual currentBest = Individual.generateNewIndividual(problem);
        stateResult.add(currentBest);
        stateResult.setBest(currentBest);

        boolean newBestFound;

        do {
            newBestFound = false;
            Individual neighbour = bestNeighbour(currentBest);

            stateResult.add(neighbour);

            if (neighbour.betterThan(currentBest)) {
                currentBest = neighbour;
                stateResult.setBest(currentBest);

                newBestFound = true;
            }
        } while (newBestFound);

        return stateResult;
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
