package net.furculita.optalgs.individual;

import net.furculita.optalgs.problem.Problem;

import java.util.Random;

public class Particle implements Item {
    private Vector position;
    private Vector velocity;
    private Vector bestPosition;
    private double bestFitness;
    private Problem problem;
    private static Random r = new Random();

    Particle(Particle particle) {
        this.position = particle.position.copy();
        this.velocity = particle.velocity.copy();
        this.bestPosition = particle.bestPosition.copy();
        this.bestFitness = particle.bestFitness;
        this.problem = particle.problem;
    }

    Particle(Problem problem) {
        this.problem = problem;

        double[] minPos = problem.min();
        double[] maxPos = problem.max();

        position = new Vector(problem.getDimension());
        for (int i = 0; i < problem.getDimension(); i++) {
            position.add(i, rand(minPos[i], maxPos[i]));
        }

        velocity = new Vector(problem.getDimension());
        for (int i = 0; i < problem.getDimension(); i++) {
            velocity.add(i, (maxPos[i] - minPos[i]) / 2.0);
        }

        bestPosition = position;
        bestFitness = problem.evaluate(this);
    }

    private static double rand(double beginRange, double endRange) {
        return (endRange - beginRange) * Particle.r.nextDouble() + beginRange;
    }

    void updatePersonalBest() {
        double fitness = problem.evaluate(this);
        if (problem.isBetterThan(bestFitness, fitness)) {
            bestPosition = position.copy();
            bestFitness = fitness;
        }
    }

    public Vector getPosition() {
        return position;
    }

    public Vector getVelocity() {
        return velocity;
    }

    public Vector getBestPosition() {
        return bestPosition;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setVelocity(Vector velocity) {
        this.velocity = velocity;
    }

    public void setPosition(Vector position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "f(" + bestPosition + ") = " + bestFitness;
    }
}
