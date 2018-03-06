package net.furculita.optimizationalgoritms.core.algorithm.mutator;

import net.furculita.optimizationalgoritms.core.individual.Individual;
import net.furculita.optimizationalgoritms.core.problem.Problem;

public abstract class Mutator {
    public abstract Individual mutate(Individual individual, Problem problem);
}
