package PSO;

import utils.DoubleVector;


public class Solution {

	
	public DoubleVector position;
	public double value;
	public double error;
	public int iterationCreated;
	public int particleID;
	public boolean approximated = false;
	
	
	public Solution () {
		this.position = null;
		this.value = Integer.MAX_VALUE;
		this.error = 0.0;
		this.iterationCreated = 0;
		this.particleID = 0;
		this.approximated = false;
	}

	public Solution (DoubleVector position, double value, double error, int iterationNumCreated, int particleID, boolean approximated) {
		this.position = position.getCopy();
		this.value = value;
		this.error = error;
		this.iterationCreated = iterationNumCreated;
		this.particleID = particleID;			
		this.approximated = approximated;
	}
	
	public Solution getCopy() {
		return new Solution(position, value, error, iterationCreated, particleID, approximated);
	}

	public void copyFrom(Solution s) {
		if (this.position == null) this.position = new DoubleVector(s.position);
		else this.position.copyFrom(s.position);
		this.value = s.value;
		this.error = s.error;
		this.iterationCreated = s.iterationCreated;
		this.particleID = s.particleID;
		this.approximated = s.approximated;
	}
	
	public double distance (Solution s) {
		return position.distance(s.position);
		
	}

	public void print() {
		position.print();
		System.out.printf("%s%2d%s%.8e%s%.8e%s%d", "  particleID = ", particleID, "  val = ", value,"  err = ", error, "  iter = ", iterationCreated);
	}

	public void println() {
		print();
		System.out.println();
	}
}