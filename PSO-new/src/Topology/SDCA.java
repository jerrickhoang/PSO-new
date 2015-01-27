package Topology;

import java.util.Random;
import java.util.Vector;

import PSO.Particle;


public class SDCA implements SwarmTopology {
	
	public enum RULE{
		TOTALISTIC, OUTER_TOTALISTIC, RESTRICTED_TOTALISTIC
	}
	
	public int numParticles;
	
	// alpha0 and alpha1 for outer-totalistic.
	// alpha is for totalistic.
	public SDCARule alphaRule;
	public SDCARule betaRule;
	public SDCARule epsilonRule;
	public int sideLength;
	
	public SDCASite[] sites;
	public int[][] connectivityMatrix;

	public SDCA(int numParticles, SDCARule alphaRule, SDCARule betaRule, SDCARule epsilonRule) {
		this.numParticles = numParticles;
		if (Math.sqrt(numParticles) * Math.sqrt(numParticles) != numParticles) {
			System.out.println("number of particles has to be a square");
			System.exit(0);
		}
		this.alphaRule = alphaRule;
		this.betaRule = betaRule;
		this.epsilonRule = epsilonRule;
		initializeSiteValuesWithProb(0.5);
		initializeConnectivityMatrix();
		//initTest();
	}
	
	public void initializeSiteValuesWithProb(double p) {
		sideLength = (int) Math.sqrt(numParticles);
		sites = new SDCASite[numParticles];
		Random r = new Random();
		for (int i = 0; i < numParticles; i ++) {
			if (r.nextDouble() <= p) {
				sites[i] = new SDCASite(1, i);
			} else {
				sites[i] = new SDCASite(0, i);
			}
		}
	}
	
	public void initTest() {
		int[] ones = {1, 3, 4, 5, 7};
		for (Integer i: ones) sites[i].value = 1;
		initializeConnectivityMatrix();
	}
	
	public void initializeConnectivityMatrix() {
		connectivityMatrix = new int[numParticles][numParticles];
		for (int i = 0; i < sideLength; i ++) {
			for (int j = 0; j < sideLength; j ++) {
				for (int u = -1; u < 2; u ++) {
					for (int v = -1; v < 2; v ++) {
						if (isValid(i+u, j+v) && isNeighbor(i, j, i + u, j + v)) {
							connectivityMatrix[i*sideLength + j][(i+u)*sideLength + j+v] = 1;
							sites[i*sideLength + j].neighbors.add((i+u)*sideLength + j+v);
							sites[(i+u)*sideLength + j+v].neighbors.add(i*sideLength + j);
						}
					}
				}
			}
		}
	}
	
	public void update(int iteration) {
		// Caculate all new values for sites and links. We can not update in place because
		// this is a batch update meaning values from time t+1 should only depend on values
		// from time t, not both.
		int[] newVal = new int[numParticles];
		for (int i = 0; i < numParticles; i++) { 
			newVal[i] = updateSite(i); 
		}
		// update all links.
		int[][] newValLink = new int[numParticles][numParticles];
		for (int i = 0; i < numParticles; i++) {
			for (int j = 0; j < numParticles; j++) {
				newValLink[i][j] = updateLink(i, j);
			}
		}
		for (int i = 0; i < numParticles; i++) { sites[i].value = newVal[i]; }
		
		for (int i = 0; i < numParticles; i++) {
			for (int j = 0; j < numParticles; j++) {
				connectivityMatrix[i][j] = newValLink[i][j];
			}
		}
	}
	
	public int updateSite(int siteId) {
		int sum = 0;
		for (int j = 0; j < numParticles; j ++) {
			if (siteId != j) sum += connectivityMatrix[siteId][j] * sites[j].value;
		}
		return phiOperator(sum, sites[siteId].value);
	}
	
	public int updateLink(int siteId1, int siteId2) {
		int res = 0;
		int bVal = 0, aVal = 0;
		if (siteId1 == siteId2) return 0;
		int vVal = sites[siteId1].value + sites[siteId2].value;
		for (int i = 0; i < numParticles; i ++) {
			if (i != siteId1 && i != siteId2) {
				if (sites[i].neighbors.contains(siteId1) && sites[i].neighbors.contains(siteId2)){
					aVal += sites[i].value;
				} else if (sites[i].neighbors.contains(siteId1) || sites[i].neighbors.contains(siteId2)){
					bVal += sites[i].value;
				}
			}
		}
		if (isWithinDist2(siteId1, siteId2)) {
			res = omegaOperator(vVal, aVal, bVal, siteId1, siteId2);
		} else {
			res = psiOperator(vVal, aVal, bVal, siteId1, siteId2);
		}
		return res;
	}
	
	public boolean isValid(int i, int j) {
		return (i >= 0 && j >= 0 && i < sideLength && j < sideLength);
	}

	// Is within distance 2 also means not connected.
	public boolean isWithinDist2(int siteId1, int siteId2) {
		if (connectivityMatrix[siteId1][siteId2] == 1) return false;
		for (Integer id: sites[siteId1].neighbors) {
			if (sites[id].neighbors.contains(siteId2)) return true;
		}
		return false;
	}
	
	public boolean isNeighbor(int i, int j, int x, int y) {
		return (i == x && j == y+1) || (i == x && j == y-1) || 
			   (j == y && i == x+1) || (j == y && i == x-1);
	}
 	
	// Site value operator. Alpha rule.
	public int phiOperator(int x, int a) {
		int res = 0;
		if (alphaRule.rule == SDCA.RULE.TOTALISTIC) {
			for (Integer i: alphaRule.ruleSet[0]) res += diracDelta(x + a, i);
		} else {
			int sum1 = 0, sum2 = 0;
			for (Integer i: alphaRule.ruleSet[0]) sum1 += diracDelta(x, i);
			for (Integer i: alphaRule.ruleSet[1]) sum2 += diracDelta(x, i);
			res = a * sum1 + (1 - a) * sum2;
		}
		return res;
	}
	
	// Linked sites operator. Use beta rule for Psi Operator, use epsilon rule for Omega operator.
	public int psiOperator(int x, int y, int z, int i, int j) {
		int sum = 0;
		if (betaRule.rule == SDCA.RULE.TOTALISTIC) {
			for (Integer k: betaRule.ruleSet[0]) sum += diracDelta(x + y + z, k);
		} else if (betaRule.rule == SDCA.RULE.OUTER_TOTALISTIC) {
			for (int m = 0; m < betaRule.ruleSet[0].length; m ++) {
				sum += diracDelta(x, betaRule.ruleSet[0][m]) * diracDelta(y + z, betaRule.ruleSet[1][m]);
			}
		} else if (betaRule.rule == SDCA.RULE.RESTRICTED_TOTALISTIC) {
			for (int m = 0; m < betaRule.ruleSet[0].length; m ++) {
				sum += diracDelta(x, betaRule.ruleSet[0][m]) * 
						diracDelta(y, betaRule.ruleSet[1][m]) * 
						diracDelta(z, betaRule.ruleSet[2][m]);
			}
		}
		return (1-sum) * connectivityMatrix[i][j];
	}
	
	// Distance-2 sites operator.
	// The 2 sites i, j must be of distance 2 away from each other. Epsilon rule.
	public int omegaOperator(int x, int y, int z, int i, int j) {
		int sum = 0;
		if (epsilonRule.rule == SDCA.RULE.TOTALISTIC) {
			for (Integer k: epsilonRule.ruleSet[0]) sum += diracDelta(x + y + z, k);
		} else if (epsilonRule.rule == SDCA.RULE.OUTER_TOTALISTIC) {
			for (int m = 0; m < epsilonRule.ruleSet[0].length; m ++) {
				sum += diracDelta(x, epsilonRule.ruleSet[0][m]) * diracDelta(y + z, epsilonRule.ruleSet[1][m]);
			}
		} else if (epsilonRule.rule == SDCA.RULE.RESTRICTED_TOTALISTIC) {
			for (int m = 0; m < epsilonRule.ruleSet[0].length; m ++) {
				sum += diracDelta(x, epsilonRule.ruleSet[0][m]) * 
						diracDelta(y, epsilonRule.ruleSet[1][m]) * 
						diracDelta(z, epsilonRule.ruleSet[2][m]);
			}
		}
		return sum;
	}
	
	public double diracDelta(double x, double y) {
		if (x == y) return 1;
		return 0;
	}
	
	public double averageNumberOfNeighbors() {
		int num = 0;
		for (int i = 0; i < connectivityMatrix.length; i ++) {
			for (int j = 0; j < connectivityMatrix[0].length; j ++) {
				if (connectivityMatrix[i][j] == 1) num ++;
			}
		}
		return (double) num / numParticles;
	}
	
	public void printSites() {
		for (int i = 0; i < sideLength; i ++) {
			for (int j = 0; j < sideLength; j ++) {
				System.out.print(sites[sideLength*i + j].value);
				System.out.print(' ');
			}
			System.out.println(' ');
		}
	}
	
	public void printMatrix() {
		for (int i = 0; i < numParticles; i ++) {
			for (int j = 0; j < numParticles; j ++) {
				System.out.print(connectivityMatrix[i][j]);
				System.out.print(' ');
			}
			System.out.println(' ');
		}
	}

	public Vector<Integer> getNeighborsFor(Particle p) {
		Vector<Integer> res = new Vector<Integer>();
		for (int i = 0; i < numParticles; i ++) {
			if (connectivityMatrix[p.particleID][i] == 1) {
				res.add(i);
			}
		}
		return res;
	}

	@Override
	public boolean isNeighbor(Particle p1, Particle p2) {
		// TODO Auto-generated method stub
		return false;
	}
}
