package net.furculita.optalgs.individual;

import java.util.ArrayList;
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

    public double[] arr() {
        double[] arr = new double[this.dimension];

        for (int i = 0; i < this.dimension; i++) {
            arr[i] = this.get(i);
        }

        return arr;
    }

    public Vector copy() {
        return new Vector(this);
    }
}
