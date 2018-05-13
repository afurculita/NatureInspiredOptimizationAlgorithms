package net.furculita.optalgs;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;
import net.furculita.optalgs.algorithm.Algorithm;
import net.furculita.optalgs.algorithm.GeneticAlgorithm;
import net.furculita.optalgs.algorithm.MemeticAlgorithm;
import net.furculita.optalgs.algorithm.ParticleSwarmAlgorithm;
import net.furculita.optalgs.algorithm.crossover.OneRandomChromosomeCrossover;
import net.furculita.optalgs.problem.RastriginProblem;
import net.furculita.optalgs.problem.StateResult;

import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private static List<XYChart.Series<Number, Number>> seriesList = new ArrayList<>();

    public static void main(String[] args) {
//        solve(new GreedyAscentHillClimbing(), 10);
//        solve(new SteepestAscentHillClimbing(), 10);
        solve(new GeneticAlgorithm(new OneRandomChromosomeCrossover(0.95), 0.3), 10);
        solve(new MemeticAlgorithm(new OneRandomChromosomeCrossover(0.95), 0.1), 10);
        solve(new ParticleSwarmAlgorithm(100, 0.05, 0.5, 0.5), 10);

        displayChart();
    }

    private static void solve(Algorithm algorithm, int dimension) {
        StateResult stateResult;
        System.out.println("============================================");
        System.out.println(algorithm);

//        System.out.println("-----------------------------------------------");
//        System.out.println("TEST");
//        stateResult = algorithm.solve(new TestProblem());
//        System.out.println(stateResult);

        System.out.println("-----------------------------------------------");
        System.out.println("RastriginProblem");
        stateResult = algorithm.solve(new RastriginProblem(dimension));
        System.out.println(stateResult);
        createChartSeries(stateResult, algorithm);

//        System.out.println("-----------------------------------------------");
//        System.out.println("GriewangkProblem");
//        stateResult = algorithm.solve(new GriewangkProblem(dimension));
//        System.out.println(stateResult);
//        createChartSeries(stateResult, algorithm);
//
//        System.out.println("-----------------------------------------------");
//        System.out.println("RosenbrockValleyProblem");
//        stateResult = algorithm.solve(new RosenbrockValleyProblem(dimension));
//        System.out.println(stateResult);
//        createChartSeries(stateResult, algorithm);
//
//        System.out.println("-----------------------------------------------");
//        System.out.println("SixHumpCamelBackProblem");
//        stateResult = algorithm.solve(new SixHumpCamelBackProblem());
//        System.out.println(stateResult);
//        createChartSeries(stateResult, algorithm);
    }

    private static void createChartSeries(StateResult s, Algorithm alg) {
        XYChart.Series series = new XYChart.Series();
        series.setName(alg.getClass().getSimpleName());

        for (int i = 10; i <= s.size(); i++) {
            series.getData().add(new XYChart.Data(i, s.get(i - 1).getValue()));
        }

        seriesList.add(series);
    }

    @Override
    public void start(Stage stage) {
        stage.setTitle("Charts");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis();
        xAxis.setLabel("Iterations");

        final NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Optimum");

        //creating the chart
        final LineChart<Number, Number> lineChart = new LineChart<>(xAxis, yAxis);

        lineChart.setTitle("Convergence to optimum per algorithm");

        Scene scene = new Scene(lineChart, 800, 600);
        lineChart.getData().addAll(seriesList);

        stage.setScene(scene);
        stage.show();
    }

    private static void displayChart() {
        launch();
    }
}
