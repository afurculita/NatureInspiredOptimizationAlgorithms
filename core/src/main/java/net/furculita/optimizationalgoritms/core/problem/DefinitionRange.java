package net.furculita.optimizationalgoritms.core.problem;

import java.util.Random;

class DefinitionRange {
    private double min;
    private double max;

    DefinitionRange(double min, double max) {
        this.min = min;
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double randomBetweenBounds() {
        double start = this.min;
        double end = this.max;
        double random = new Random().nextDouble();

        return start + (random * (end - start));
    }
}
