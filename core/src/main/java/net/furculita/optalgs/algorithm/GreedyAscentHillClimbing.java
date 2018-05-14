package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.Problem;
import net.furculita.optalgs.problem.StateResult;

/**
 * First-improvement Hill Climbing.
 * <p>
 * A solution is selected uniformly at random from the neighborhood.
 * <p>
 * http://www.cs.stir.ac.uk/~goc/papers/PPSN10FirstImprLON.pdf
 */
public class GreedyAscentHillClimbing extends Algorithm {
    private Individual firstIndividual = null;

    public GreedyAscentHillClimbing(Individual firstIndividual) {
        this.firstIndividual = firstIndividual;
    }

    public GreedyAscentHillClimbing() {
    }

    public StateResult<Individual> solve(Problem problem) {
        StateResult<Individual> stateResult = new StateResult<>();

        if (firstIndividual != null)
            stateResult.addBest(firstIndividual);
        else
            stateResult.addBest(Individual.generateNewIndividual(problem));

        int it = 0;

        do {
            Individual randomNeighbour = Individual.mutateOneRandomGeneFromOneRandomChromosome(stateResult.getBest());

            if (randomNeighbour.betterThan(stateResult.getBest())) {
                stateResult.addBest(randomNeighbour);
                it = 0;
            } else {
                stateResult.add(randomNeighbour);
            }

            it++;
        } while (it < 1000);

        return stateResult;
    }

    @Override
    public String toString() {
        return "GreedyAscentHillClimbing{}";
    }
}
