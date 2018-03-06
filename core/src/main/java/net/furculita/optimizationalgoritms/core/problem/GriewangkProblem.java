package net.furculita.optimizationalgoritms.core.problem;

import net.furculita.optimizationalgoritms.core.individual.Individual;

public class GriewangkProblem extends Problem {
    public GriewangkProblem(int dimension) {
        super(dimension, -600, 600);
    }

    @Override
    public double fitness(Individual subject) {
        double sum = 0;

        for (double gene : subject.getGenome()) {
            sum += Math.pow(gene, 2) / 4000;
        }

        double prod = 1;
        int i = 1;
        for (double gene : subject.getGenome()) {
            prod *= Math.cos(gene) / Math.sqrt(i);
            i++;
        }

        return sum - prod + 1;
    }
}
