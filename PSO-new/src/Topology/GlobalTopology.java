package Topology;

import java.util.Vector;

import PSO.Particle;

public class GlobalTopology implements SwarmTopology {
	public boolean[][] connectivityMatrix;
	public int numParticles;
	
	public GlobalTopology(int numParticles) {
		this.numParticles = numParticles;
		this.initConnectivityMatrix();
	}
	
	public void initConnectivityMatrix() {
		this.connectivityMatrix = new boolean[numParticles][numParticles];
		for (int i = 0; i < numParticles; i ++){
			for (int j = 0; j < numParticles; j ++) {
				if (i != j) this.connectivityMatrix[i][j] = true;
				else this.connectivityMatrix[i][j] = false;
			}
		}
	}
	
	public boolean isNeighbor(Particle p1, Particle p2) {
		return connectivityMatrix[p1.particleID][p2.particleID];
	}

	public void update(int iteration) {
		return;
	}

	public Vector<Integer> getNeighborsFor(Particle p) {
		Vector<Integer> res = new Vector<Integer>();
		for (int i = 0; i < numParticles; i ++) {
			if (connectivityMatrix[p.particleID][i]) {
				res.add(i);
			}
		}
		return res;
	}
}
