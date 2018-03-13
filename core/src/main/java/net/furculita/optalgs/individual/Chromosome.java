package net.furculita.optalgs.individual;

import java.util.BitSet;
import java.util.Random;

public class Chromosome extends BitSet {
    private static final long serialVersionUID = 1L;
    private int initialBitCount;

    private Chromosome(int nBits) {
        super(nBits);
        initialBitCount = nBits;
    }

    public static Chromosome generateNewChromosome(int size) {
        Chromosome output = new Chromosome(size);
        Random r = new Random();
        for (int i = 0; i < output.getInitialBitCount(); i++) {
            boolean value = r.nextBoolean();
            output.set(i, value);
        }

        return output;
    }

    public static Chromosome clone(Chromosome initialIndiv) {
        Chromosome output = new Chromosome(initialIndiv.getInitialBitCount());
        output.clear();
        output.or(initialIndiv);

        return output;
    }

    public int getInitialBitCount() {
        return initialBitCount;
    }

    public int asInteger() {
        int output = 0;
        int multiplier = 1;
        for (int i = getInitialBitCount() - 1; i >= 0; i--) {
            if (get(i)) {
                output += multiplier;
            }
            multiplier *= 2;
        }

        return output;
    }

    public double asBoundedDecimal(double a, double b) {
        return a + (b - a) * this.asInteger() / (Math.pow(2, this.asInteger()) - 1);
    }
}
