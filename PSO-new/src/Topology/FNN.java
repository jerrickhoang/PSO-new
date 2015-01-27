package Topology;

import java.util.Random;
import java.util.Vector;

import PSO.Particle;

public class FNN implements SwarmTopology {
	
	public enum NeighborhoodType {
		MOORE, VON_NEUMANN
	}

	public Neuron[][] grid;
	public int numRows, numCols;
	public int numParticles;
	public Neuron[] neuronList;
	private NeighborhoodType neighborhoodType;
	
	public FNN(int numParticles, int latticeSize, FNN.NeighborhoodType type) {
		this.neighborhoodType = type;
		this.numParticles = numParticles;
		this.numRows = latticeSize;
		this.numCols = latticeSize;
		randomPopulate(numRows, numCols);
	}

	private void randomPopulate(int numRows, int numCols) {
		grid = new Neuron[numRows][numCols];
		neuronList = new Neuron[numParticles];
		for (int neuronID = 0 ; neuronID < numParticles; ++neuronID) {
			Neuron newNeuron = addRandomNeuron(neuronID);
			neuronList[neuronID] = newNeuron;
			// null if grid is full; should not happen
			if (newNeuron == null) { 
				System.out.println("error: grid too small in randomPopulate");
				System.exit(0);
			}
		}	
	}

	private Neuron addRandomNeuron(int neuronID) {

		if (gridFull()) {
			return null;
		}
		Random rand = new Random();

		int r = rand.nextInt(numRows);
		int c = rand.nextInt(numCols);

		while (grid[r][c] != null) {
			r = rand.nextInt(numRows);
			c = rand.nextInt(numCols);
		}

		grid[r][c] = new Neuron(neuronID, r, c);

		return grid[r][c];

	}

	private boolean gridFull() {
		for (int r = 0 ; r < grid.length ; ++r) {
			for (int c = 0 ; c < grid[r].length ; ++c) {
				if (grid[r][c] == null)
					return false;
			}
		}
		return true;
	}
	
	public void reset() {
		randomPopulate(this.numRows, this.numCols);
	}

	@Override
	public boolean isNeighbor(Particle p1, Particle p2) {
		Vector<Integer> NeighborIDs = this.getNeighborsFor(p1);
		for (Integer i: NeighborIDs) {
			if (i == p2.particleID) return true;
		}
		return false;
	}

	@Override
	public void update(int iteration) {
		return;
	}

	@Override
	public Vector<Integer> getNeighborsFor(Particle p) {
		Vector<Integer> res = new Vector<Integer>();
		Neuron current = neuronList[p.particleID];
		if (this.neighborhoodType == FNN.NeighborhoodType.MOORE) {
			for (int i = -1; i < 2; i++) {
				for (int j = -1; j < 2; j ++) {
					int r = current.r + i;
					int c = current.c + j;
					if ((i!= 0 || j != 0) && grid[r][c] != null) {
						res.add(grid[r][c].id);
					}
				}
			}
		} else if (this.neighborhoodType == FNN.NeighborhoodType.VON_NEUMANN) {
			int r = current.r + 1;
			int c = current.c;
			if (grid[r][c] != null) res.add(grid[r][c].id);
			
			r = current.r - 1;
			c = current.c;
			if (grid[r][c] != null) res.add(grid[r][c].id);

			r = current.r;
			c = current.c + 1;
			if (grid[r][c] != null) res.add(grid[r][c].id);
			
			r = current.r;
			c = current.c - 1;
			if (grid[r][c] != null) res.add(grid[r][c].id);
		}
		return res;
	}
}
