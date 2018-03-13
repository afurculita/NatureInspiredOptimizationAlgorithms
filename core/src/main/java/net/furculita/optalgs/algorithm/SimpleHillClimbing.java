package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.Problem;

public class SimpleHillClimbing extends Algorithm {
    public History solve(Problem problem) {
        History history = new History();

        Individual best = Individual.potential(problem);
        int it = 0;

        do {
            Individual mutated = Individual.mutateOneRandomGeneFromOneRandomChromosome(best);

            if (mutated.betterThan(best)) {
                history.add(mutated);
                best = mutated;
                it = 0;

                if (problem.optimumAchieved(best.getFitness())) {
                    break;
                }
            }

            it++;
        } while (it < 1000);

        return history;
    }
}
