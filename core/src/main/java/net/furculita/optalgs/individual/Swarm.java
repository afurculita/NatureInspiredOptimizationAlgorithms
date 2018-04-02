package net.furculita.optalgs.individual;

import net.furculita.optalgs.problem.Problem;

import java.util.ArrayList;

public class Swarm extends ArrayList<Particle> {
    private final Problem problem;
    private final int numOfParticles;
    private Particle best;

    public Swarm(Problem problem, int numOfParticles) {
        super(numOfParticles);
        this.problem = problem;
        this.numOfParticles = numOfParticles;

        this.populate();
    }

    private void populate() {
        for (int i = 0; i < numOfParticles; i++) {
            Particle particle = new Particle(problem);
            this.add(particle);
            updateBest(particle);
        }
    }

    private void updateBest(Particle particle) {
        if (best == null || particle.getBestFitness() < this.getBestFitness()) {
            best = new Particle(particle);
        }
    }

    public Vector getBestPosition() {
        return this.getBest().getBestPosition();
    }

    public double getBestFitness() {
        return best.getBestFitness();
    }

    public Particle getBest() {
        return best;
    }

    public void evaluate() {
        for (Particle p : this) {
            p.updatePersonalBest();
            this.updateBest(p);
        }
    }
}
