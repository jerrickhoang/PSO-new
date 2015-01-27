package PSO;
import java.util.Vector;

import utils.DoubleVector;
import TestFunctions.Function;
import Topology.SwarmTopology;


public class Swarm {

	//Equation constants.
	public static final double PERSONAL_THETA = 2.05;
	public static final double NEIGHBORHOOD_THETA = 2.05;
	public static final double THETA = PERSONAL_THETA + NEIGHBORHOOD_THETA;
	public static final double CONSTRICTION_FACTOR = 2.0 / (THETA - 2.0 + Math.sqrt(THETA*THETA - 4.0 * THETA));

	
	public Particle[] particles;  
	public Solution globalBest;
	public SwarmTopology PSOTopology;
	public Function function;

	public Swarm(Function function, int swarmSize, SwarmTopology PSOTopology) {
		this.PSOTopology = PSOTopology;
		this.function = function;
		this.initializeParticles(swarmSize);
		globalBest = new Solution();
	}

	public void initializeParticles(int swarmSize) {
		this.particles = new Particle[swarmSize];
		for (int i = 0; i < swarmSize; i ++) {
			this.particles[i] = new Particle(function, i);
		}
	}
	
	public void update (int iteration) {
		PSOTopology.update(iteration);
		Solution newSolution;
		for (int i = 0; i < particles.length; i ++) {
			newSolution = update(particles[i], iteration);
			if (newSolution.value < globalBest.value) {
				globalBest.copyFrom(newSolution);
			} 
		}
	}
	
	public void updatePositionAndVelocity(Particle p) {
		DoubleVector acceleration = new DoubleVector(p.position.size(), 0.0);

		// neighborhood best component.
		DoubleVector neighborhoodComponent = getVectorToNeighBestPositionFor(p);
		neighborhoodComponent.multRandomScalar(0.0, Swarm.NEIGHBORHOOD_THETA);
		acceleration.addVector(neighborhoodComponent);

		// personal component.
		DoubleVector personalComponent = DoubleVector.sub(p.personalBest.position, p.position);
		personalComponent.multRandomScalar(0.0, Swarm.PERSONAL_THETA);
		acceleration.addVector(personalComponent);
		
		// update the velocity and apply the constriction factor.
		p.velocity.addVector(acceleration);
		p.velocity.multScalar(Swarm.CONSTRICTION_FACTOR);
		
		// bound velocity
		for (int i = 0 ; i < p.velocity.size() ; ++i) {
			if (p.velocity.get(i) < function.PSOSpeedMinVal)
				p.velocity.set(i, function.PSOSpeedMinVal);
			else if (p.velocity.get(i) > function.PSOSpeedMaxVal)
				p.velocity.set(i, function.PSOSpeedMaxVal);
		}
		
		// move the particle 
		p.position.addVector(p.velocity); 
	}
	
	public Solution update(Particle p, int iteration) {
		updatePositionAndVelocity(p);

		// evaluate the new position and set currentSolution
		double[] results = function.evalWithError(p.position);
		double newPositionValue = results[Function.VAL_INDEX];
		double newPositionError = results[Function.ERR_INDEX];
		Solution currSolution = new Solution(p.position, newPositionValue, newPositionError, iteration, p.particleID, false);

		// update the personal best, if necessary
		if (newPositionValue < p.personalBest.value) {
			p.personalBest.position.copyFrom(p.position);
			p.personalBest.value = newPositionValue;
			p.personalBest.error = newPositionError;
			p.personalBest.iterationCreated = iteration;
		} 
		return currSolution;
	}
	
	public DoubleVector getNeighBestPositionFor(Particle p) {
		Vector<Integer> neighborIDs = PSOTopology.getNeighborsFor(p);
		double minNeighFunctionValue = Double.MAX_VALUE;
		DoubleVector bestPosition = new DoubleVector(p.position.size());
		Particle currentParticle;
		double neighFunctionValue;
		for (Integer neighborID: neighborIDs) {
			currentParticle = getParticleWithId(neighborID);
			neighFunctionValue = currentParticle.personalBest.value;
			if (neighFunctionValue < minNeighFunctionValue) {
				minNeighFunctionValue = neighFunctionValue;
				bestPosition = currentParticle.personalBest.position;
			}
		}
		return bestPosition;
	}
	
	public DoubleVector getVectorToNeighBestPositionFor(Particle p) {
		DoubleVector neighBestPosition = getNeighBestPositionFor(p);
		return DoubleVector.sub(neighBestPosition, p.position);
	}
	
	public Particle getParticleWithId(int particleId) {
		return particles[particleId];
	}
}




