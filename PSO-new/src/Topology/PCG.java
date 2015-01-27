package Topology;

import java.util.Vector;

import PSO.Particle;

public class PCG implements SwarmTopology {
	
	public int numParticles;
	
	public PCG(int numParticles) {
		this.numParticles = numParticles;
	}

	@Override
	public boolean isNeighbor(Particle p1, Particle p2) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void update(int iteration) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Vector<Integer> getNeighborsFor(Particle p) {
		// TODO Auto-generated method stub
		return null;
	}

}
