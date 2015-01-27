package Topology;

import java.util.HashSet;

public class SDCASite {
	public int value;
	public int id;
	public HashSet<Integer> neighbors;
	public SDCASite(int value, int id) {
		this.value = value;
		this.id = id;
		this.neighbors = new HashSet<Integer>();
	}

}
