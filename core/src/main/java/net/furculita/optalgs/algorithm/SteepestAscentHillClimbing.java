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
    private Individual firstIndividual = null;

    public SteepestAscentHillClimbing(Individual firstIndividual) {
        this.firstIndividual = firstIndividual;
    }

    public SteepestAscentHillClimbing() {
    }

    @Override
    public StateResult<Individual> solve(Problem problem) {
        StateResult<Individual> state = new StateResult<>();

        if (firstIndividual != null)
            state.addBest(firstIndividual);
        else
            state.addBest(Individual.generateNewIndividual(problem));

        boolean newBestFound;

        do {
            newBestFound = false;
            Individual neighbour = bestNeighbour(state.getBest());

            if (neighbour.betterThan(state.getBest())) {
                state.addBest(neighbour);

                newBestFound = true;
            } else {
                state.add(neighbour);
            }
        } while (newBestFound);

        return state;
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
