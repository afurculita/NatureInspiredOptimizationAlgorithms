package net.furculita.optimizationalgoritms.hillclimbing;

import net.furculita.optimizationalgoritms.core.History;
import net.furculita.optimizationalgoritms.core.algorithm.Algorithm;
import net.furculita.optimizationalgoritms.core.algorithm.mutator.BitBasedRepresentationFlipMutator;
import net.furculita.optimizationalgoritms.core.algorithm.mutator.Mutator;
import net.furculita.optimizationalgoritms.core.individual.Individual;
import net.furculita.optimizationalgoritms.core.problem.Problem;

public class HillClimbingAlgorithm extends Algorithm {
    public History solve(Problem problem) {
        History history = new History();

        Individual best = problem.potential();
        Mutator mutator = new BitBasedRepresentationFlipMutator();

        while (true) {
            boolean newFound = false;

            for (int i = 0; i < problem.getIterations(); i++) {
                Individual mutated = mutator.mutate(best);
                mutated.setFitness(problem.fitness(mutated));

                if (mutated.betterThan(best)) {
                    history.add(mutated);
                    best = mutated;
                    newFound = true;
                }
            }

            if (!newFound) {
                break;
            }
        }

        return history;
    }
}
