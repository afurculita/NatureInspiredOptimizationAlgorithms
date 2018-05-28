package net.furculita.optalgs.tsp;

import net.furculita.optalgs.Randoms;
import net.furculita.optalgs.individual.Item;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.moeaframework.problem.tsplib.Tour;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Particle implements Item {
    private Vector position;
    private Vector velocity;
    private Vector bestPosition;
    private double bestFitness;
    private TSPProblem problem;

    private List<Pair<Integer, Integer>> swaps = new ArrayList<>();

    private List<Tour> tours = new ArrayList<>();

    Particle(Particle particle) {
        this.position = particle.position.copy();
        this.velocity = particle.velocity.copy();
        this.bestPosition = particle.bestPosition.copy();
        this.bestFitness = particle.bestFitness;
        this.problem = particle.problem;
        this.tours = new ArrayList<>(particle.tours);
        this.swaps = particle.swaps;
    }

    public Particle(TSPProblem problem) {
        this.problem = problem;

        position = problem.randomVector();

        generateTours();

        generateSwapsList(problem.getDimension());

        velocity = new Vector(problem.getDimension());
        for (int i = 0; i < problem.getDimension(); i++) {
            velocity.add(i, Randoms.between(-6, 6));
        }

        bestPosition = position;
        bestFitness = this.evaluate();
    }

    private void generateSwapsList(int dim) {
        Random random = new Random();
        int ra1;
        int ra2;

        int ra = random.nextInt(65535) % dim;
        for (int j = 0; j < ra; j++) {
            ra1 = random.nextInt(65535) % dim;
            while (ra1 == 0) {
                ra1 = random.nextInt(65535) % dim;
            }
            ra2 = random.nextInt(65535) % dim;
            while (ra1 == ra2 || ra2 == 0) {
                ra2 = random.nextInt(65535) % dim;
            }

            swaps.add(new ImmutablePair<>(ra1, ra2));
        }
    }

    private void generateTours() {
        for (int i = 0; i < problem.getSalesmanNr(); i++) {
            tours.add(new Tour());
        }

        Vector visitedNodes = new Vector(position);

        while (!visitedNodes.isEmpty()) {
            int rNodeIndex = Randoms.integer(visitedNodes.size());
            int rTourIndex = Randoms.integer(tours.size());

            tours.get(rTourIndex).add(visitedNodes.get(rNodeIndex).intValue());

            visitedNodes.remove(rNodeIndex);
        }
    }

    public void updatePersonalBest() {
        double fitness = this.evaluate();
        if (problem.isBetterThan(bestFitness, fitness)) {
            bestPosition = position.copy();
            bestFitness = fitness;
        }

        for (Tour tour : this.tours) {
            double l = tour.distance(this.problem.getDistanceTable());

            if (l > tour.getBestLength()) {
                tour.setBestLength(l);
            }
        }
    }

    public double evaluate() {
        double longestTourLength = 0;

        for (Tour tour : this.tours) {
            double l = tour.distance(this.problem.getDistanceTable());

            if (l > longestTourLength) {
                longestTourLength = l;
            }
        }

        return longestTourLength;
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

    @Override
    public double getValue() {
        return this.getBestFitness();
    }

    public List<Pair<Integer, Integer>> getSwaps() {
        return swaps;
    }

    public void setSwaps(List<Pair<Integer, Integer>> swaps) {
        this.swaps = swaps;
    }
}
