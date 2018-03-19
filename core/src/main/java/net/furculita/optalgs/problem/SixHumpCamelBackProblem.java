package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Chromosome;
import net.furculita.optalgs.individual.Individual;

import java.util.List;

/**
 * https://www.sfu.ca/~ssurjano/camel6.html
 */
public class SixHumpCamelBackProblem extends Problem {
    public SixHumpCamelBackProblem() {
        super(2);
    }

    @Override
    public double func(Individual subject) {
        double x1 = subject.getChromosomes().get(0).asBoundedDecimal(-3, 3);
        double x2 = subject.getChromosomes().get(1).asBoundedDecimal(-2, 2);

        return (4 - 2.1 * Math.pow(x1, 2) + Math.pow(x1, 4) / 3) * Math.pow(x1, 2)
                + x1 * x2
                + (-4 + 4 * Math.pow(x2, 2)) * Math.pow(x2, 2);
    }

    @Override
    public String solutionsToString(List<Chromosome> solution) {
        double x1 = solution.get(0).asBoundedDecimal(-3, 3);
        double x2 = solution.get(1).asBoundedDecimal(-2, 2);

        return x1 + ", " + x2;
    }

    @Override
    public int chromosomeSize() {
        return (int) Math.ceil(Math.log((3 - (-3)) * Math.pow(10, 0.00001)));
    }
}
