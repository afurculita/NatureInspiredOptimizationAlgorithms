package net.furculita.optimizationalgoritms.core.algorithm;

import net.furculita.optimizationalgoritms.core.History;
import net.furculita.optimizationalgoritms.core.problem.Problem;

public abstract class Algorithm {
    public abstract History solve(Problem problem);
}
