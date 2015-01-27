package Topology;

import java.util.Vector;

import PSO.Particle;

public interface SwarmTopology {
	public boolean isNeighbor(Particle p1, Particle p2) ;

	public void update(int iteration) ;

	public Vector<Integer> getNeighborsFor(Particle p);
}
