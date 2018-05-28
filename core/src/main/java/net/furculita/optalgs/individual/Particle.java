package net.furculita.optalgs.individual;

import net.furculita.optalgs.Randoms;
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

    public Particle(Problem problem) {
        this.problem = problem;

        position = problem.randomVector();

        velocity = new Vector(problem.getDimension());
        for (int i = 0; i < problem.getDimension(); i++) {
            velocity.add(i, Randoms.between(-6, 6));
        }

        bestPosition = position;
        bestFitness = problem.evaluate(this);
    }

    public void updatePersonalBest() {
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
        return "f = " + bestFitness;
    }

    @Override
    public double getValue() {
        return this.getBestFitness();
    }
}
