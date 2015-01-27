package TestFunctions;


public class TestResult {
	public Function.Name functionName;
	public int numRuns, numIterations;
	public double[] runResults;
	public double averageResults;
	public int numParticles;
	
	public TestResult(int numRuns, int numIterations, int numParticles, Function.Name name) {
		this.numRuns = numRuns;
		this.numIterations = numIterations;
		this.numParticles = numParticles;
		this.functionName = name;
		this.runResults = new double[numRuns];
		this.averageResults = 0;
		this.numParticles = 0;
	}
}
