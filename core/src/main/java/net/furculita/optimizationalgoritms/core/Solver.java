package net.furculita.optimizationalgoritms.core;

import net.furculita.optimizationalgoritms.core.algorithm.Algorithm;
import net.furculita.optimizationalgoritms.core.problem.Problem;

public class Solver {
    public History solve(Problem problem, Algorithm algorithm) {
        return algorithm.solve(problem);
    }
}
