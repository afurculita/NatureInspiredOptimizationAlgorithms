package net.furculita.optalgs;

import org.jetbrains.annotations.NotNull;
import org.moeaframework.problem.tsplib.TSPInstance;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Main2 {
    private static final int PARTICLE_COUNT = 10;
    private static final int V_MAX = 4;

    private static final int MAX_EPOCHS = 10000;

    private static ArrayList<Particle> particles = new ArrayList<>();
    private static ArrayList<cCity> map = new ArrayList<>();
    private static int DIMENSION;

    private static void initializeMap() {
        TSPInstance tspInstance;
        try {
            tspInstance = new TSPInstance(new File("./data/tsp/eil51.tsp"));
        } catch (IOException e) {
            e.printStackTrace();

            return;
        }

        DIMENSION = tspInstance.getDimension();

        for (int i = 1; i <= DIMENSION; i++) {
            cCity city = new cCity();

            double[] pos = tspInstance.getDistanceTable().get(i).getPosition();

            city.x(pos[0]);
            city.y(pos[1]);
            map.add(city);
        }
    }

    private static void PSOAlgorithm() {
        Particle aParticle;
        int epoch = 0;

        initialize();

        while (epoch < MAX_EPOCHS) {
            for (int i = 0; i < PARTICLE_COUNT; i++) {
                aParticle = particles.get(i);
                System.out.print("Particle: ");
                for (int j = 0; j < DIMENSION; j++) {
                    System.out.print(aParticle.data(j) + ", ");
                }

                getTotalDistance(i);
                System.out.print("Distance: " + aParticle.pBest() + "\n");
            }

            bubbleSort(); // sort particles by their pBest scores, best to worst.

            getVelocity();

            updateParticles();

            System.out.println("epoch number: " + epoch);

            epoch++;
        }
    }

    private static void initialize() {
        for (int i = 0; i < PARTICLE_COUNT; i++) {
            Particle newParticle = new Particle();
            for (int j = 0; j < DIMENSION; j++) {
                newParticle.data(j, j);
            }
            particles.add(newParticle);
            for (int j = 0; j < 10; j++) {
                randomlyArrange(particles.indexOf(newParticle));
            }
            getTotalDistance(particles.indexOf(newParticle));
        }
    }

    private static void randomlyArrange(final int index) {
        int cityA = new Random().nextInt(DIMENSION);
        int cityB = 0;
        boolean done = false;
        while (!done) {
            cityB = new Random().nextInt(DIMENSION);
            if (cityB != cityA) {
                done = true;
            }
        }

        int temp = particles.get(index).data(cityA);
        particles.get(index).data(cityA, particles.get(index).data(cityB));
        particles.get(index).data(cityB, temp);
    }

    private static void getVelocity() {
        double worstResults;
        double vValue;

        // after sorting, worst will be last in list.
        worstResults = particles.get(PARTICLE_COUNT - 1).pBest();

        for (int i = 0; i < PARTICLE_COUNT; i++) {
            vValue = (V_MAX * particles.get(i).pBest()) / worstResults;

            if (vValue > V_MAX) {
                particles.get(i).velocity(V_MAX);
            } else if (vValue < 0.0) {
                particles.get(i).velocity(0.0);
            } else {
                particles.get(i).velocity(vValue);
            }
        }
    }

    private static void updateParticles() {
        // Best is at index 0, so start from the second best.
        for (int i = 1; i < PARTICLE_COUNT; i++) {
            // The higher the velocity score, the more changes it will need.
            int changes = (int) Math.floor(Math.abs(particles.get(i).velocity()));
            System.out.println("Changes in City Positions(Swapping) " + i + ": " + changes);
            for (int j = 0; j < changes; j++) {
                if (new Random().nextBoolean()) {
                    randomlyArrange(i);
                }
                // Push it closer to it's best neighbor.
                copyFromParticle(i - 1, i);
            }

            // Update pBest value.
            getTotalDistance(i);
        }
    }

    private static void printBestSolution() {
        System.out.print("Shortest Route: ");
        for (int j = 0; j < DIMENSION; j++) {
            System.out.print(particles.get(0).data(j) + ", ");
        } // j
        System.out.print("Distance: " + particles.get(0).pBest() + "\n");
    }

    private static void copyFromParticle(final int source, final int destination) {
        // push destination's data points closer to source's data points.
        Particle best = particles.get(source);
        int targetA = new Random().nextInt(DIMENSION); // source's city to target.
        int targetB = 0;
        int indexA = 0;
        int indexB = 0;
        int tempIndex = 0;

        // targetB will be source's neighbor immediately succeeding targetA (circular).
        int i = 0;
        for (; i < DIMENSION; i++) {
            if (best.data(i) == targetA) {
                if (i == DIMENSION - 1) {
                    targetB = best.data(0); // if end of array, take from beginning.
                } else {
                    targetB = best.data(i + 1);
                }
                break;
            }
        }

        // Move targetB next to targetA by switching values.
        for (int j = 0; j < DIMENSION; j++) {
            if (particles.get(destination).data(j) == targetA) {
                indexA = j;
            }
            if (particles.get(destination).data(j) == targetB) {
                indexB = j;
            }
        }
        // get temp index succeeding indexA.
        if (indexA != DIMENSION - 1) {
            tempIndex = indexA + 1;
        }

        // Switch indexB value with tempIndex value.
        int temp = particles.get(destination).data(tempIndex);
        particles.get(destination).data(tempIndex, particles.get(destination).data(indexB));
        particles.get(destination).data(indexB, temp);
    }

    private static void getTotalDistance(final int index) {
        Particle thisParticle;
        thisParticle = particles.get(index);
        thisParticle.pBest(0.0);

        for (int i = 0; i < DIMENSION; i++) {
            if (i == DIMENSION - 1) {
                thisParticle.pBest(thisParticle.pBest() + getDistance(thisParticle.data(DIMENSION - 1), thisParticle.data(0))); // Complete trip.
            } else {
                thisParticle.pBest(thisParticle.pBest() + getDistance(thisParticle.data(i), thisParticle.data(i + 1)));
            }
        }
    }

    private static double getDistance(final int firstCity, final int secondCity) {
        cCity cityA;
        cCity cityB;
        double a2;
        double b2;
        cityA = map.get(firstCity);
        cityB = map.get(secondCity);
        a2 = Math.pow(Math.abs(cityA.x() - cityB.x()), 2);
        b2 = Math.pow(Math.abs(cityA.y() - cityB.y()), 2);

        return Math.sqrt(a2 + b2);
    }

    private static void bubbleSort() {
        boolean done = false;
        while (!done) {
            int changes = 0;
            int listSize = particles.size();
            for (int i = 0; i < listSize - 1; i++) {
                if (particles.get(i).compareTo(particles.get(i + 1)) > 0) {
                    Particle temp = particles.get(i);
                    particles.set(i, particles.get(i + 1));
                    particles.set(i + 1, temp);
                    changes++;
                }
            }
            if (changes == 0) {
                done = true;
            }
        }
    }

    private static class Particle implements Comparable<Particle> {
        private int mData[] = new int[DIMENSION];
        private double mpBest;
        private double mVelocity;

        public Particle() {
            this.mpBest = 0;
            this.mVelocity = 0.0;
        }

        public int compareTo(@NotNull Particle that) {
            return Double.compare(this.pBest(), that.pBest());
        }

        int data(final int index) {
            return this.mData[index];
        }

        void data(final int index, final int value) {
            this.mData[index] = value;
        }

        double pBest() {
            return this.mpBest;
        }

        void pBest(final double value) {
            this.mpBest = value;
        }

        double velocity() {
            return this.mVelocity;
        }

        void velocity(final double velocityScore) {
            this.mVelocity = velocityScore;
        }
    } // Particle

    private static class cCity {
        private double mX = 0;
        private double mY = 0;

        double x() {
            return mX;
        }

        void x(final double xCoordinate) {
            mX = xCoordinate;
        }

        double y() {
            return mY;
        }

        void y(final double yCoordinate) {
            mY = yCoordinate;
        }
    }

    public static void main(String[] args) {
        initializeMap();
        PSOAlgorithm();
        printBestSolution();
    }
}