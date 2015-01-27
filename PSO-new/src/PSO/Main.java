package PSO;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.Vector;

import TestFunctions.Ackley;
import TestFunctions.Function;
import TestFunctions.Rastrigin;
import TestFunctions.Rosenbrock;
import TestFunctions.TestResult;
import Topology.GlobalTopology;
import Topology.SDCA;
import Topology.SDCARule;

public class Main {
	public static void main(String[] args) {
		int[] dimensions = {30};
		int numRuns = 50;
		int numIterations = 10000;
		int[] numParticles = {49};
		Vector<TestResult> testResults = new Vector<TestResult>();
		
		//classifySDCA(numIterations, numParticles[0]);

		
		for (Integer dimension: dimensions) {
			Vector<Function> testFunctions = new Vector<Function>();
            //testFunctions.add(new Ackley(dimension));
            //testFunctions.add(new Griewank(dimension));
            //testFunctions.add(new Penalized1(dimension));
            //testFunctions.add(new Penalized2(dimension));
            testFunctions.add(new Rastrigin(dimension));
            testFunctions.add(new Rosenbrock(dimension));
            //testFunctions.add(new Sphere(dimension));
            for (Function f: testFunctions) {
            	for (Integer particle: numParticles) {
            		testResults.add(
            				runExperiment(f, dimension, particle,numRuns, numIterations));
            	}
            }
		}
		printResults(testResults);
		System.out.println("Done");
	}
	
	public static ArrayList<Integer> genTotalistic(int n) {
		int x = 0;
		ArrayList<Integer> res = new ArrayList<Integer>();
		while((1 << x) <= n) {
			if ((n & (1 << x)) != 0) res.add(x);
			x ++;
		}
		return res;
	}

	public static void classifySDCA(int numIterations, int numParticles) {
		int[][] alphaSet;
		int[][] betaSet;
		int[][] epsilonSet;
		ArrayList<Integer> alphaList;
		ArrayList<Integer> betaList;
		ArrayList<Integer> epsilonList;
		Integer[] alpha;
		Integer[] beta;
		Integer[] epsilon;
        String[] testNames = generateTestResultNames();
        String directoryName = testNames[0];
        String allResultsFilePath = testNames[1] + "-ALL-RESULTS";
        String bestResultsFilePath = testNames[1] + "-BEST-RESULTS";
	    createResultsDirectory(directoryName);
	    PrintWriter allWriter, bestWriter;	
        try {
			allWriter = new PrintWriter(allResultsFilePath, "UTF-8");
			bestWriter = new PrintWriter(bestResultsFilePath, "UTF-8");
			Random r = new Random();
            for (int i = 1; i <= 1 << numParticles; i += r.nextInt(100)) {
            	for (int j = 1; j <= 1 << numParticles; j += r.nextInt(100)) {
            		for (int k = 1; k <= 1 << numParticles; k += r.nextInt(100)) {
            			alphaList = genTotalistic(i);
            			betaList = genTotalistic(j);
						epsilonList = genTotalistic(k);
						alpha = alphaList.toArray(new Integer[alphaList.size()]);
						beta = betaList.toArray(new Integer[betaList.size()]);
						epsilon = epsilonList.toArray(new Integer[epsilonList.size()]);
						alphaSet = new int[1][alpha.length];
						betaSet = new int[1][beta.length];
						epsilonSet = new int[1][epsilon.length];
						for (int t = 0; t < alpha.length; t ++){
							alphaSet[0][t] = alpha[t];
						}
						for (int t = 0; t < beta.length; t ++) {
							betaSet[0][t] = beta[t];
						}
						for (int t = 0; t < epsilon.length; t ++) {
							epsilonSet[0][t] = epsilon[t];
						}
						runSingleExperimentWithRule(allWriter, bestWriter, alphaSet, betaSet, epsilonSet, 
								numIterations, numParticles);
					}
				}
			}
            allWriter.close(); bestWriter.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void runSingleExperimentWithRule(PrintWriter allWriter, PrintWriter bestWriter, int[][] alphaSet, 
			int[][] betaSet, int[][] epsilonSet, int numIterations, int numParticles) {
		SDCARule alphaRule = new SDCARule(SDCA.RULE.TOTALISTIC, alphaSet);
		SDCARule betaRule = new SDCARule(SDCA.RULE.TOTALISTIC, betaSet);
		SDCARule epsilonRule = new SDCARule(SDCA.RULE.TOTALISTIC, epsilonSet);
        SDCA topo = new SDCA(numParticles, alphaRule, betaRule, epsilonRule);
        double[] results = new double[numIterations];
        boolean goodResults = false;
        for(int i = 0; i < numIterations; i ++) {
        	topo.update(i);
        	results[i] = topo.averageNumberOfNeighbors();
        }
        for (int i = numIterations - 1; i > numIterations - 5; i --) {
        	if (results[i] != results[i-1]) goodResults = true; 
        }
        if (goodResults) {
	        allWriter.println("Average number of connections for SDCA");
	        allWriter.printf("Num particles = %s \n", numParticles);
	        allWriter.printf("Num iterations = %s \n", numIterations);
	        allWriter.print("Alpha rule = ");
            for (Integer x : alphaSet[0]) allWriter.printf(" %s, ", x);
	        allWriter.println();
	        allWriter.print("Beta rule = ");
	        for (Integer x : betaSet[0]) allWriter.printf(" %s, ", x);
	        allWriter.println();
	        allWriter.print("Epsilon rule = ");
	        for (Integer x : epsilonSet[0]) allWriter.printf(" %s, ", x);
	        allWriter.println();
	        for (int i = 0 ; i < numIterations; i ++) {
	        	allWriter.println(results[i]);
	        }
        }
	}
	
	public static TestResult runExperiment(Function f, int dimension, int numParticles,
			int numRuns, int numIterations) {
		int[][] alphaSet = {{0,1,2,4,5,6,8,9,11,12,13,16}};
		int[][] betaSet = {{0}};
		int[][] epsilonSet = {{1,2,3,4,5,7,8,9,10,11,14,15}};
		SDCARule alphaRule = new SDCARule(SDCA.RULE.TOTALISTIC, alphaSet);
		SDCARule betaRule = new SDCARule(SDCA.RULE.TOTALISTIC, betaSet);
		SDCARule epsilonRule = new SDCARule(SDCA.RULE.TOTALISTIC, epsilonSet);
		TestResult result = new TestResult(numRuns, numIterations, numParticles, f.name);
        for (int run = 0; run < numRuns; run ++) {
        	SDCA topo = new SDCA(numParticles, alphaRule, betaRule, epsilonRule);
        	// GlobalTopology topo = new GlobalTopology(numParticles);
            Swarm s = new Swarm(f, numParticles, topo);
            for (int iter = 0; iter < numIterations; iter++) {
				s.update(iter);
			}
			result.runResults[run] = s.globalBest.value;
		}
		result.averageResults = average(result.runResults);
		return result;
	}
	
	public static double average(double[] s) {
		double sum = 0;
		for (Double d: s) sum += d;
		return sum / s.length;
	}
	
	public static void printResults(Vector<TestResult> testResults) {
		String currentDirectory = System.getProperty("user.dir");
		String resultsDirectory = currentDirectory + "/results";
	    Date dNow = new Date();
	    String folderNameByDate = new SimpleDateFormat ("E yyyy.MM.dd").format(dNow);
	    String fileNameByTime = new SimpleDateFormat("hh_mm_ss a zzz").format(dNow);
	    String directoryName = resultsDirectory + "/" + folderNameByDate;
	    String filePath = directoryName + "/" + fileNameByTime;
		
	    createResultsDirectory(directoryName);
	    writeToFile(filePath, testResults);
	}
	
	public static String[] generateTestResultNames() {
	    String currentDirectory = System.getProperty("user.dir");
		String resultsDirectory = currentDirectory + "/results";
	    Date dNow = new Date();
	    String folderNameByDate = new SimpleDateFormat ("E yyyy.MM.dd").format(dNow);
	    String fileNameByTime = new SimpleDateFormat("hh_mm_ss a zzz").format(dNow);
	    String directoryName = resultsDirectory + "/" + folderNameByDate;
	    String filePath = directoryName + "/" + fileNameByTime;
	    String[] res = new String[2];
	    res[0] = directoryName;
	    res[1] = filePath;
	    return res;
	}
	
	public static void writeToFile(String filePath, Vector<TestResult> testResults) {
		PrintWriter writer = null;

		try {
			writer = new PrintWriter(filePath, "UTF-8");
			for(TestResult t: testResults) {
				writeToFileSingleResult(writer, t);
			}
			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	public static void writeToFileSingleResult(PrintWriter writer, TestResult t) {
		writer.println("====================================================");
		writer.println("USING SDCA TOPOLOGY");
		writer.printf("FUNCTION %s \n", t.functionName.name());
		writer.printf("AVERAGE OVER %s RUNS, EACH USING %s ITERATIONS\n", 
				t.numRuns, t.numIterations);
		for (int i = 0; i < t.numRuns; i ++) {
			writer.printf("RUN #%s: MIN FOUND = %s \n", i, t.runResults[i]);
		}
		writer.printf("AVERAGE OVER ALL RUNS %s \n", t.averageResults);
		writer.println("====================================================");
	}
	
	public static void createResultsDirectory(String dirName) {
		File file = new File(dirName);
		if (!file.exists()) {
			if (file.mkdir()) {
				System.out.println("Directory is created!");
			} else {
				System.out.println("Failed to create directory!");
			}
		}
	}
}
