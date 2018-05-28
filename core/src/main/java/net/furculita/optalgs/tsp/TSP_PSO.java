package net.furculita.optalgs.tsp;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.problem.StateResult;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * Other strategies: https://pdfs.semanticscholar.org/cdea/3bb774ea4cf6876fd131109210cc363636fe.pdf
 */
public class TSP_PSO {
    private static final double DEFAULT_OMEGA = 0.6;
    private static final double DEFAULT_PHI_P = 1.5;
    private static final double DEFAULT_PHI_G = 2;
    private static final int NUM_PARTICLES = 100;
    private static final int ITERATIONS = 1000;
    private static final double MUTATION_RATE = 2;

    private int particlesNr;
    private double inertia;
    private double cognitiveComponent;
    private double socialComponent;

    public TSP_PSO(int particlesNr, double inertia, double cognitive, double social) {
        this.particlesNr = particlesNr;
        this.inertia = inertia;
        this.cognitiveComponent = cognitive;
        this.socialComponent = social;
    }

    public TSP_PSO() {
        this(NUM_PARTICLES, DEFAULT_OMEGA, DEFAULT_PHI_P, DEFAULT_PHI_G);
    }

    public StateResult solve(TSPProblem problem) {
        StateResult<Particle> stateResult = new StateResult<>();
        Swarm swarm = new Swarm(problem, particlesNr);
        for (int i = 0; i < swarm.getNumOfParticles(); i++) {
            Particle particle = new Particle(swarm.getProblem());
            swarm.add(particle);
            swarm.updateBest(particle);
        }

        stateResult.addBest(swarm.getBest());

        for (int i = 0; i < ITERATIONS; i++) {
            updateParticles(swarm);

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

    private void updateParticles(Swarm swarm) {
        for (Particle p : swarm) {
            applySwapsStrategy1(p, swarm);
            /**
             * application of https://www.cs.york.ac.uk/rts/docs/CEC-2007/html/pdf/1262.pdf
             */
//            applySwapsStrategy2(p, swarm);

//            applyVelocityDependentSwapStrategy(p, swarm);
        }
    }

    /**
     * application of https://research.ijcaonline.org/volume47/number15/pxc3880348.pdf
     */
    private void applySwapsStrategy1(Particle p, Swarm swarm) {
        List<Pair<Integer, Integer>> newSwaps = new ArrayList<>();

        List<Pair<Integer, Integer>> b = swarm.getBestPosition().findSwapDifference(p.getPosition());
        double r2 = Randoms.next();

        for (Pair<Integer, Integer> aB : b) {
            if (Randoms.next() < r2 * socialComponent)
                newSwaps.add(aB);
        }

        List<Pair<Integer, Integer>> a = p.getBestPosition().findSwapDifference(p.getPosition());

        double r1 = Randoms.next();

        for (Pair<Integer, Integer> anA : a) {
            if (Randoms.next() < r1 * cognitiveComponent)
                newSwaps.add(anA);
        }

        for (Pair<Integer, Integer> swap : p.getSwaps()) {
            if (Randoms.next() < inertia) {
                newSwaps.add(swap);
            }
        }

        p.setSwaps(newSwaps);

        Vector pos = p.getPosition().copy();
        double r3 = Randoms.next();

        for (Pair<Integer, Integer> swap : p.getSwaps()) {
            if (Randoms.next() > r3 * MUTATION_RATE) {
                continue;
            }

            double temp = pos.get(swap.getLeft());
            pos.set(swap.getLeft(), pos.get(swap.getRight()));
            pos.set(swap.getRight(), temp);
        }

        p.setPosition(pos);
    }

    private void applyVelocityDependentSwapStrategy(Particle p, Swarm swarm) {
        Vector pos = p.getPosition().copy();
        Vector newVelocity = p.getVelocity().copy();
        Vector pBest = p.getBestPosition().copy();
        Vector gBest = swarm.getBestPosition().copy();

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

        if (Randoms.next() < MUTATION_RATE) {
            newVelocity.scalarMultiply(-1);
        }

        p.setVelocity(newVelocity);

        // The higher the velocity score, the more changes it will need.

//        Vector newPos = pos.copy();
//
//        NodeCoordinates coordinates = swarm.getProblem().getDistanceTable();
//
//        for (int i = 0; i < newPos.size(); i++) {
//            Node node = coordinates.get(newPos.getInt(i));
//            Node newNode;
//
//            double[] newNodePos = new double[node.getPosition().length];
//
//            for (int i1 = 0; i1 < node.getPosition().length; i1++) {
//                newNodePos[i1] = node.getPosition()[i1] + newVelocity.get(i);
//            }
//
//            newPos.set(i, (double) ((newPos.size() + Math.round(newPos.get(i))) % newPos.size()));
//        }
//
//        p.setPosition(newPos);
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
