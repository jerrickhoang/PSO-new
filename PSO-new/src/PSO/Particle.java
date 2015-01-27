package PSO;

import java.util.Random;

import utils.DoubleVector;
import TestFunctions.Function;


public class Particle {

	public static final double UNIVERSAL_MIN_INIT_SPEED = -2.0;
	public static final double UNIVERSAL_SPEED_RANGE = 4.0;
	
	public int particleID;
	public DoubleVector position;  
	public DoubleVector velocity;
	public Solution neighSolution;
	public Solution personalBest;
	
	public Particle(Function function, int particleID) {
		this.particleID = particleID;

		// Position and Velocity.
		// don't let the initial speed be greater than a small amount
		double minSpeed = function.PSOSpeedMinVal;
		double speedRange = function.PSOSpeedRange;
		if (minSpeed > UNIVERSAL_SPEED_RANGE) {
			minSpeed = UNIVERSAL_MIN_INIT_SPEED;
			speedRange = UNIVERSAL_SPEED_RANGE;
		} 
		this.position = new DoubleVector(function.dimension, function.initPSOParticlePositionVal);
		this.velocity = new DoubleVector(function.dimension, minSpeed);
		Random r;
		for (int i = 0; i < function.dimension; i ++) {
			r = new Random();
			this.position.set(i, this.position.get(i) + function.initPSOPositionRange * r.nextDouble());
			this.velocity.set(i, minSpeed + (speedRange * r.nextDouble()));
		}
		// Personal best and Neighborhood best.
		double[] results = function.evalWithError(this.position);
		personalBest = new Solution(position, results[Function.VAL_INDEX], results[Function.ERR_INDEX], 0, particleID, false);
		
	}
}


