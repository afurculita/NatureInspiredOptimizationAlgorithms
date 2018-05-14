package net.furculita.optalgs.algorithm;

import net.furculita.optalgs.algorithm.distribution.ContinuousParticleDistribution;
import net.furculita.optalgs.algorithm.distribution.ParticleDistribution;
import net.furculita.optalgs.individual.Particle;
import net.furculita.optalgs.individual.Swarm;
import net.furculita.optalgs.individual.Vector;
import net.furculita.optalgs.problem.Problem;
import net.furculita.optalgs.problem.StateResult;

public class ParticleSwarmAlgorithm extends Algorithm {
    private static final double DEFAULT_OMEGA = -0.1618;
    private static final double DEFAULT_PHI_P = 1.8903;
    private static final double DEFAULT_PHI_G = 2.1225;
    private static final int NUM_PARTICLES = 100;
    private static final int ITERATIONS = 500;

    private int particlesNr;
    private double inertia;
    private double cognitiveComponent;
    private double socialComponent;
    private final ParticleDistribution particleDistribution;

    public ParticleSwarmAlgorithm(int particlesNr, double inertia, double cognitive, double social, ParticleDistribution particleDistribution) {
        this.particlesNr = particlesNr;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
        this.particleDistribution = particleDistribution;
    }

    public ParticleSwarmAlgorithm() {
        this(NUM_PARTICLES, DEFAULT_OMEGA, DEFAULT_PHI_P, DEFAULT_PHI_G, new ContinuousParticleDistribution());
    }

    @Override
    public StateResult solve(Problem problem) {
        StateResult<Particle> stateResult = new StateResult<>();
        Swarm swarm = new Swarm(problem, particlesNr);
        particleDistribution.populateSwarm(swarm);

        stateResult.addBest(swarm.getBest());

        for (int i = 0; i < ITERATIONS; i++) {
            updateParticleVelocities(swarm);
            particleDistribution.applyParticleVelocities(swarm, problem);

            for (Particle p : swarm) {
                p.updatePersonalBest();
                swarm.updateBest(p);
            }

            if (swarm.getBestFitness() < stateResult.getBest().getBestFitness()) {
                stateResult.addBest(swarm.getBest());
            } else {
                stateResult.add(swarm.getBest());
            }
        }

        return stateResult;
    }

    private void updateParticleVelocities(Swarm swarm) {
        for (Particle p : swarm) {
            updateParticleVelocity(p, swarm);
        }
    }

    private void updateParticleVelocity(Particle particle, Swarm swarm) {
        Vector pBest = particle.getBestPosition().copy();
        Vector gBest = swarm.getBestPosition().copy();
        Vector pos = particle.getPosition().copy();
        Vector newVelocity = particle.getVelocity().copy();

        Vector r1 = Vector.random(swarm.getBestPosition().size());
        Vector r2 = Vector.random(swarm.getBestPosition().size());

        pBest.subtract(pos);
        pBest.scalarMultiply(cognitiveComponent);
        pBest.multiply(r1);

        gBest.subtract(pos);
        gBest.scalarMultiply(socialComponent);
        gBest.multiply(r2);

        newVelocity.scalarMultiply(inertia);
        newVelocity.plus(pBest);
        newVelocity.plus(gBest);

        particle.setVelocity(newVelocity);
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
