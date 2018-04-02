package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.individual.Particle;
import net.furculita.optalgs.individual.Swarm;
import net.furculita.optalgs.individual.Vector;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.Problem;

public class ParticleSwarmAlgorithm extends Algorithm {
    private static final double DEFAULT_OMEGA = -0.1618;
    private static final double DEFAULT_PHI_P = 1.8903;
    private static final double DEFAULT_PHI_G = 2.1225;
    private static final int NUM_PARTICLES = 134;
    private static final int ITERATIONS = 1000;

    private int particlesNr;
    private double inertia, cognitiveComponent, socialComponent;

    public ParticleSwarmAlgorithm(int particlesNr, double inertia, double cognitive, double social) {
        this.particlesNr = particlesNr;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
    }

    public ParticleSwarmAlgorithm() {
        this(NUM_PARTICLES, DEFAULT_OMEGA, DEFAULT_PHI_P, DEFAULT_PHI_G);
    }

    @Override
    public History solve(Problem problem) {
        History history = new History();
        Swarm swarm = new Swarm(problem, particlesNr);

        Particle currentBest = swarm.getBest();
        history.add(currentBest);

        for (int i = 0; i < ITERATIONS; i++) {
            swarm.evaluate();

            for (Particle p : swarm) {
                update(p, swarm);
            }

            if (swarm.getBestFitness() < currentBest.getBestFitness()) {
                currentBest = swarm.getBest();
                history.add(currentBest);
            }
        }

        return history;
    }

    private void update(Particle particle, Swarm swarm) {
        Vector pBest = particle.getBestPosition().copy();
        Vector gBest = swarm.getBestPosition().copy();
        Vector pos = particle.getPosition().copy();
        Vector newVelocity = particle.getVelocity().copy();

        Vector r1 = Vector.random(swarm.getBestPosition().size());
        Vector r2 = Vector.random(swarm.getBestPosition().size());

        // The first product of the formula.
        newVelocity.scalarMultiply(inertia);

        // The second product of the formula.
        pBest.subtract(pos);
        pBest.scalarMultiply(cognitiveComponent);
        pBest.multiply(r1);
        newVelocity.plus(pBest);

        // The third product of the formula.
        gBest.subtract(pos);
        gBest.scalarMultiply(socialComponent);
        gBest.multiply(r2);
        newVelocity.plus(gBest);

        particle.setVelocity(newVelocity);
        particle.setPosition(pos.plus(newVelocity));
    }

    @Override
    public String toString() {
        return "ParticleSwarmAlgorithm{" +
                "particlesNr=" + particlesNr +
                ", inertia=" + inertia +
                ", cognitiveComponent=" + cognitiveComponent +
                ", socialComponent=" + socialComponent +
                '}';
    }
}
