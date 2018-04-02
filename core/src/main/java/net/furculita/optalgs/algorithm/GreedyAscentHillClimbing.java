package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.Problem;

/**
 * First-improvement Hill Climbing.
 *
 * A solution is selected uniformly at random from the neighborhood.
 *
 * http://www.cs.stir.ac.uk/~goc/papers/PPSN10FirstImprLON.pdf
 */
public class GreedyAscentHillClimbing extends Algorithm {
    public History solve(Problem problem) {
        History history = new History();

        Individual currentBest = Individual.generateNewIndividual(problem);
        history.add(currentBest);

        int it = 0;

        do {
            Individual randomNeighbour = Individual.mutateOneRandomGeneFromOneRandomChromosome(currentBest);

            if (randomNeighbour.betterThan(currentBest)) {
                history.add(randomNeighbour);
                currentBest = randomNeighbour;
                it = 0;
            }

            it++;
        } while (!problem.optimumAchieved(currentBest.getFitness()) && it < 1000);

        return history;
    }

    @Override
    public String toString() {
        return "GreedyAscentHillClimbing{}";
    }
}
