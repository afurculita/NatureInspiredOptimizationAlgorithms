package net.furculita.optalgs.algorithm.distribution;

import net.furculita.optalgs.individual.Particle;
import net.furculita.optalgs.individual.Swarm;
import net.furculita.optalgs.problem.Problem;

public class DiscreteParticleDistribution implements ParticleDistribution {
    @Override
    public void applyParticleVelocities(Swarm swarm, Problem problem) {

    }

    @Override
    public void populateSwarm(Swarm swarm) {
        for (int i = 0; i < swarm.getNumOfParticles(); i++) {
            Particle particle = new Particle(swarm.getProblem());
            swarm.add(particle);
            swarm.updateBest(particle);
        }
    }
}
