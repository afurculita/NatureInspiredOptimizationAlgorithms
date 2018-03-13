package net.furculita.optalgs.individual;

import java.util.BitSet;
import java.util.Random;

public class Chromosome extends BitSet {
    private static final long serialVersionUID = 1L;
    private int bitsNr;

    private Chromosome(int nBits) {
        super(nBits);
        bitsNr = nBits;
    }

    public static Chromosome generateNewChromosome(int size) {
        Chromosome output = new Chromosome(size);
        Random r = new Random();
        for (int i = 0; i < output.bitsNr(); i++) {
            boolean value = r.nextBoolean();
            output.set(i, value);
        }

        return output;
    }

    public static Chromosome clone(Chromosome initialIndiv) {
        Chromosome output = new Chromosome(initialIndiv.bitsNr());
        output.clear();
        output.or(initialIndiv);

        return output;
    }

    public int bitsNr() {
        return bitsNr;
    }

    private int asInteger() {
        long value = 0L;
        for (int i = 0; i < this.bitsNr(); ++i) {
            value += this.get(i) ? (1L << i) : 0L;
        }

        return (int) value;
    }

    public double asBoundedDecimal(double a, double b) {
        return Chromosome.map(this.asInteger(), 1, Math.pow(2, this.bitsNr), a, b);
    }

    private static double map(int s, double a1, double a2, double b1, double b2) {
        return b1 + (s - a1) * (b2 - b1) / (a2 - a1);
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();

        for (int i = 0; i < this.bitsNr(); i++) {
            str.append(get(i) ? 1 : 0);
        }

        return str.toString();
    }
}
