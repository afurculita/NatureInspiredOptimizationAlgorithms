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
    public double evaluate(Individual subject) {
        double x1 = subject.getChromosomes().get(0).asBoundedDecimal(-3, 3);
        double x2 = subject.getChromosomes().get(1).asBoundedDecimal(-2, 2);

        return evaluate(new double[]{x1, x2});
    }

    @Override
    public double evaluate(double[] coordinates) {
        double x1 = coordinates[0];
        double x2 = coordinates[1];

        return (4 - 2.1 * Math.pow(x1, 2) + Math.pow(x1, 4) / 3) * Math.pow(x1, 2)
                + x1 * x2
                + (-4 + 4 * Math.pow(x2, 2)) * Math.pow(x2, 2);
    }

    @Override
    public String solutionsToString(List<Chromosome> solution) {
        double x1 = solution.get(0).asBoundedDecimal(min()[0], max()[0]);
        double x2 = solution.get(1).asBoundedDecimal(min()[1], max()[1]);

        return x1 + ", " + x2;
    }

    @Override
    public int chromosomeSize() {
        return (int) Math.ceil(Math.log((3 - (-3)) * Math.pow(10, 0.00001)));
    }


    @Override
    public double[] min() {
        double[] min = new double[this.dimension];

        min[0] = -3;
        min[1] = -2;

        return min;
    }

    @Override
    public double[] max() {
        double[] max = new double[this.dimension];

        max[0] = 3;
        max[1] = 2;

        return max;
    }
}
