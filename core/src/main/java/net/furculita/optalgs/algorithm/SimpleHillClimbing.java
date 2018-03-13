package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.individual.Individual;
import net.furculita.optalgs.problem.Problem;

public class SimpleHillClimbing extends Algorithm {
    public History solve(Problem problem) {
        History history = new History();

        Individual best = Individual.potential(problem);
        int iterationsWithoutNewSolutions = 0;

        while (true) {
            Individual mutated = Individual.mutateOneRandomGeneFromOneRandomChromosome(best);

            if (mutated.betterThan(best)) {
                history.add(mutated);
                best = mutated;
                iterationsWithoutNewSolutions = 0;

                if (problem.optimumAchieved(best.getFitness())) {
                    break;
                }
            } else {
                iterationsWithoutNewSolutions++;
            }

            if (iterationsWithoutNewSolutions == 1000) {
                break;
            }
        }

        return history;
    }
}
