package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.StateResult;
import net.furculita.optalgs.problem.Problem;

/**
 * First-improvement Hill Climbing.
 *
 * A solution is selected uniformly at random from the neighborhood.
 *
 * http://www.cs.stir.ac.uk/~goc/papers/PPSN10FirstImprLON.pdf
 */
public class GreedyAscentHillClimbing extends Algorithm {
    public StateResult solve(Problem problem) {
        StateResult stateResult = new StateResult();

        Individual currentBest = Individual.generateNewIndividual(problem);
        stateResult.add(currentBest);
        stateResult.setBest(currentBest);

        int it = 0;

        do {
            Individual randomNeighbour = Individual.mutateOneRandomGeneFromOneRandomChromosome(currentBest);

            stateResult.add(randomNeighbour);

            if (randomNeighbour.betterThan(currentBest)) {
                currentBest = randomNeighbour;
                stateResult.setBest(currentBest);
                it = 0;
            }

            it++;
        } while (!problem.optimumAchieved(currentBest.getFitness()) && it < 1000);

        return stateResult;
    }

    @Override
    public String toString() {
        return "GreedyAscentHillClimbing{}";
    }
}
