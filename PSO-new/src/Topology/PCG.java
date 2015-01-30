package Topology;

import java.util.Vector;

import PSO.Particle;

public class PCG implements SwarmTopology {
	
	public int numParticles;
	// Number of ways to split the particles.
	public int numWays;
	public int numRows;
	public int numCols;
	
	// 2D matrix with each row a fully connected graph.
	// if numParticles is not divisible by numWays, ignore some last
	// particles in the last row.
	public int[][] graph;
	
	public PCG(int numParticles, int numWays) {
		this.numParticles = numParticles;
		this.numWays = numWays;
		graph = new int[numWays][numParticles/numWays + 1];
		this.numCols = numParticles/numWays + 1;
		this.numRows = numWays; 
		initGraph();
	}
	
	public void initGraph() {
		int counter = 0;
		for (int i = 0; i < numWays; i ++) {
			for (int j = 0; j < numParticles/numWays + 1; j ++) {
				graph[i][j] = counter;
				counter ++;
			}
		}
	}

	@Override
	public boolean isNeighbor(Particle p1, Particle p2) {
		Vector<Integer> neighbors = this.getNeighborsFor(p1);
		for (Integer n : neighbors) {
			if (n == p2.particleID) return true;
		}
		return false;
	}

	@Override
	public void update(int iteration) {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public Vector<Integer> getNeighborsFor(Particle p) {
		Vector<Integer> res = new Vector<Integer>();
		if (p.particleID % this.numCols == 0) {
			// If this particle is the first particle in a row.
			for (int i = 0; i < numRows; i ++) {
				if (graph[i][0] != p.particleID) res.add(graph[i][0]);
			}
		}
		int row = (p.particleID / numRows);
		for (int i = 0; i < numCols; i ++) {
			// INCLUDE SELF
			if (row == numRows - 1) {
				if (i < (numParticles % numWays)) res.add(graph[row][i]);
			} else {
				res.add(graph[row][i]);
			}
		}
		return res;
	}
	
	public void printGraph() {
		for (int i = 0; i < numRows; i ++) {
			for (int j = 0; j < numCols; j ++) {
				System.out.print(graph[i][j]);
				System.out.print(" ");
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) {
		PCG p = new PCG(7, 3);
		p.printGraph();
		Vector<Integer> ns = p.getNeighborsFor(new Particle(null, 4));
		for (Integer n : ns) System.out.println(n);
	}
}
