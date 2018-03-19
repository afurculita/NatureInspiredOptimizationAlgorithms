package net.furculita.optalgs;

import net.furculita.optalgs.algorithm.*;
import net.furculita.optalgs.problem.*;

public class Main {
    public static void main(String[] args) {
//        solve(new SimpleHillClimbing());
//        solve(new AscentHillClimbing());
        solve(new GeneticAlgorithm(0.8, 0.3));
        solve(new MemeticAlgorithm(0.8, 0.3));
    }

    private static void solve(Algorithm algorithm) {
        History history;
        System.out.println("============================================");
        System.out.println(algorithm.getClass().getSimpleName());

        System.out.println("-----------------------------------------------");
        System.out.println("RastriginProblem");
        history = algorithm.solve(new RastriginProblem(30));
        System.out.println(history);
//
//        System.out.println("-----------------------------------------------");
//        System.out.println("GriewangkProblem");
//        history = algorithm.solve(new GriewangkProblem(3));
//        System.out.println(history);
//
//        System.out.println("-----------------------------------------------");
//        System.out.println("RosenbrockValleyProblem");
//        history = algorithm.solve(new RosenbrockValleyProblem(3));
//        System.out.println(history);
//
//        System.out.println("-----------------------------------------------");
//        System.out.println("SixHumpCamelBackProblem");
//        history = algorithm.solve(new SixHumpCamelBackProblem());
//        System.out.println(history);
    }
}
