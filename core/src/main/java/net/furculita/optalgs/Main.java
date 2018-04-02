package net.furculita.optalgs;

import net.furculita.optalgs.algorithm.Algorithm;
import net.furculita.optalgs.algorithm.ParticleSwarmAlgorithm;
import net.furculita.optalgs.problem.History;
import net.furculita.optalgs.problem.RastriginProblem;

public class Main {
    public static void main(String[] args) {
//        solve(new GreedyAscentHillClimbing());
//        solve(new SteepestAscentHillClimbing());
//        solve(new GeneticAlgorithm(new OneRandomChromosomeCrossover(0.95), 0.3));
//        solve(new MemeticAlgorithm(new OneRandomChromosomeCrossover(0.95), 0.1));
        solve(new ParticleSwarmAlgorithm(161, -0.2089, -0.0787, 3.7637));
        solve(new ParticleSwarmAlgorithm(149, -0.3236, -0.1136, 3.9789));
        solve(new ParticleSwarmAlgorithm(60, -0.4736, -0.97, 3.7904));
        solve(new ParticleSwarmAlgorithm(256, -0.3499, -0.0513, 4.9087));
        solve(new ParticleSwarmAlgorithm(134, -0.1618, 1.8903, 2.1225));
        solve(new ParticleSwarmAlgorithm(95, -0.6031, -0.6485, 2.6475));
        solve(new ParticleSwarmAlgorithm(106, -0.2256, -0.1564, 3.8876));
    }

    private static void solve(Algorithm algorithm) {
        History history;
        System.out.println("============================================");
        System.out.println(algorithm);

//        System.out.println("-----------------------------------------------");
//        System.out.println("TEST");
//        history = algorithm.solve(new TestProblem());
//        System.out.println(history);

        System.out.println("-----------------------------------------------");
        System.out.println("RastriginProblem");
        history = algorithm.solve(new RastriginProblem(30));
        System.out.println(history);

//        System.out.println("-----------------------------------------------");
//        System.out.println("GriewangkProblem");
//        history = algorithm.solve(new GriewangkProblem(3));
//        System.out.println(history);

//        System.out.println("-----------------------------------------------");
//        System.out.println("RosenbrockValleyProblem");
//        history = algorithm.solve(new RosenbrockValleyProblem(3));
//        System.out.println(history);

//        System.out.println("-----------------------------------------------");
//        System.out.println("SixHumpCamelBackProblem");
//        history = algorithm.solve(new SixHumpCamelBackProblem());
//        System.out.println(history);
    }
}
