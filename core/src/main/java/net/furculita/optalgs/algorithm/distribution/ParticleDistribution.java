package net.furculita.optalgs.algorithm.distribution;

import net.furculita.optalgs.individual.Swarm;
import net.furculita.optalgs.problem.Problem;

public interface ParticleDistribution {
    public void applyParticleVelocities(Swarm swarm, Problem problem);

    public void populateSwarm(Swarm swarm);
}
