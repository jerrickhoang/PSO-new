package Topology;

public class SDCARule {
	public SDCA.RULE rule;
	public int[][] ruleSet;
	
	public SDCARule(SDCA.RULE rule, int[][] ruleSet) {
		this.rule = rule;
		this.ruleSet = ruleSet.clone();
	}
}
