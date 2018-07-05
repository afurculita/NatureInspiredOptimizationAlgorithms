package net.furculita.optalgs.tsp;

import org.moeaframework.problem.tsplib.Tour;

import java.util.ArrayList;
import java.util.List;

public class Swarm extends ArrayList<Particle> {
    private final TSPProblem problem;
    private final int numOfParticles;
    private Particle best;

    public Swarm(TSPProblem problem, int numOfParticles) {
        super(numOfParticles);
        this.problem = problem;
        this.numOfParticles = numOfParticles;
    }

    public void updateBest(Particle particle) {
        if (best == null || particle.getBestFitness() < this.getBestFitness()) {
            best = new Particle(particle);
        }
    }

    public Vector getBestPosition() {
        return this.getBest().getBestPosition();
    }

    public List<Tour> getBestTourPosition() {
        return this.getBest().getBestTourPosition();
    }

    public double getBestFitness() {
        return best.getBestFitness();
    }

    public Particle getBest() {
        return best;
    }

    public int getNumOfParticles() {
        return numOfParticles;
    }

    public TSPProblem getProblem() {
        return problem;
    }
}
