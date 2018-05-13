package net.furculita.optalgs.problem;

import net.furculita.optalgs.individual.Item;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.exception.MathIllegalArgumentException;
import org.apache.commons.math3.stat.descriptive.SummaryStatistics;

import java.util.LinkedList;

public class StateResult extends LinkedList<Item> {
    private SummaryStatistics stats = new SummaryStatistics();
    private Item best = null;

    public void setBest(Item best) {
//        System.out.print(best.toString());

        this.best = best;
    }

    private Item getBest() {
        return best;
    }

    @Override
    public String toString() {
        return "Best: " + this.getBest()
//                + "\nFitness history: \n"
//                + this.stream()
//                .map(Item::toString)
//                .collect(Collectors.joining("\n"))
                + "\n\nStats:\n" + stats();
    }

    private String stats() {
        return "Min: " + this.getMin()
                + "\nMax: " + this.getMax()
                + "\nMean: " + this.getMean()
                + "\nStandard deviation: " + this.getStandardDeviation()
                + "\n" + String.format(
                "Confidence Interval 95%%: %f - %f, %f + %f",
                getMean(), getConfidenceInterval(0.95), getMean(),
                getConfidenceInterval(0.95));
    }

    @Override
    public boolean add(Item item) {
        boolean added = super.add(item);

        if (added) {
            stats.addValue(item.getValue());
        }

        return added;
    }

    public double getMin() {
        return stats.getMin();
    }

    public double getMax() {
        return stats.getMax();
    }

    public double getMean() {
        return stats.getMean();
    }

    public double getStandardDeviation() {
        return stats.getStandardDeviation();
    }

    public double getConfidenceInterval(double confidenceLevel) {
        try {
            // Create T Distribution with N-1 degrees of freedom
            TDistribution tDist = new TDistribution(stats.getN() - 1);
            // Calculate critical value
            double critVal = tDist.inverseCumulativeProbability(1.0 - (1 - confidenceLevel) / 2);
            // Calculate confidence interval
            return critVal * stats.getStandardDeviation() / Math.sqrt(stats.getN());
        } catch (MathIllegalArgumentException e) {
            return Double.NaN;
        }
    }
}
