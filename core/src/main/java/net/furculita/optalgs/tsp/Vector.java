package net.furculita.optalgs.tsp;

import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class Vector extends ArrayList<Double> {
    private final int dimension;

    public Vector(Vector v1) {
        this.dimension = v1.dimension;

        this.addAll((ArrayList<Double>) v1.clone());
    }

    public Vector(int dimension) {
        super(dimension);

        this.dimension = dimension;
    }

    public static Vector random(int dimension) {
        Vector vector = new Vector(dimension);
        Random r = new Random();

        for (int i = 0; i < dimension; i++) {
            vector.add(r.nextDouble());
        }

        return vector;
    }

    public Vector plus(Vector v2) {
        for (int i = 0; i < this.dimension; i++) {
            this.set(i, this.get(i) + v2.get(i));
        }

        return this;
    }

    public void plus(double x) {
        for (int i = 0; i < this.dimension; i++) {
            this.set(i, this.get(i) + x);
        }
    }

    public void subtract(Vector v2) {
        for (int i = 0; i < this.dimension; i++) {
            this.set(i, this.get(i) - v2.get(i));
        }
    }

    public void multiply(Vector v2) {
        for (int i = 0; i < this.dimension; i++) {
            this.set(i, this.get(i) * v2.get(i));
        }
    }

    public void scalarMultiply(double c) {
        for (int i = 0; i < this.dimension; i++) {
            this.set(i, this.get(i) * c);
        }
    }

    public int[] arr() {
        double[] arr = new double[this.dimension];

        for (int i = 0; i < this.dimension; i++) {
            arr[i] = this.get(i);
        }

        return Arrays.stream(arr).mapToInt(x -> Double.valueOf(x).intValue()).toArray();
    }

    public List<Pair<Integer, Integer>> findSwapDifference(Vector b) {
        Vector clone = b.copy();
        List<Pair<Integer, Integer>> list = new ArrayList<>();

        for (int i = 0; i < this.size(); i++) {
            if (this.get(i).equals(clone.get(i))) {
                continue;
            }

            // find the same index as a[i] in temp[]
            int j = findSameIndex(clone, this.get(i));

            // swap
            double temp2 = clone.get(i);
            clone.set(i, clone.get(j));
            clone.set(j, temp2);

            list.add(new ImmutablePair<>(i, j));
        }
        return list;
    }

    private int findSameIndex(Vector arr, double num) {
        for (int i = 0; i < arr.size(); i++) {
            if (arr.get(i) == num) {
                return i;
            }
        }

        return -1;
    }

    public Vector copy() {
        return new Vector(this);
    }

    public int getInt(int index) {
        return this.get(index).intValue();
    }
}
