package TestFunctions;

import utils.DoubleVector;

public class Ackley extends Function {
	
	// have also seen (10.0, 20.0) for initialization and (-32.0, 32.0) for search range
	private static final double ACKLEY_INIT_MIN_VAL = -32.768; 
	private static final double ACKLEY_INIT_MAX_VAL = 16.0;   
	//	private static final double ACKLEY_INIT_MIN_VAL = 15.0; 
	//	private static final double ACKLEY_INIT_MAX_VAL = 30.0;   
	private static final double ACKLEY_INIT_RANGE = ACKLEY_INIT_MAX_VAL - ACKLEY_INIT_MIN_VAL;

	private static final double ACKLEY_SEARCH_SPACE_MIN_VAL = -32.768;   
	private static final double ACKLEY_SEARCH_SPACE_MAX_VAL = 32.768;   
	//	private static final double ACKLEY_SEARCH_SPACE_MIN_VAL = -30.0;   
	//	private static final double ACKLEY_SEARCH_SPACE_MAX_VAL = 30.0;   
	private static final double ACKLEY_SEARCH_SPACE_RANGE = ACKLEY_SEARCH_SPACE_MAX_VAL - ACKLEY_SEARCH_SPACE_MIN_VAL;

	private static final double ACKLEY_SPEED_MIN_VAL = ACKLEY_SEARCH_SPACE_MIN_VAL;
	private static final double ACKLEY_SPEED_MAX_VAL = ACKLEY_SEARCH_SPACE_MAX_VAL;
	private static final double ACKLEY_SPEED_RANGE = ACKLEY_SPEED_MAX_VAL - ACKLEY_SPEED_MIN_VAL;

	private static final double ACKLEY_OPT_VALUE = 0.0;

	public static final double ACKLEY_OPT_COORD = 0.0; 
	public static final double ACKLEY_SHIFT_RANGE = ACKLEY_SEARCH_SPACE_MAX_VAL - ACKLEY_OPT_COORD;

	public Ackley(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = ACKLEY_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = ACKLEY_SPEED_MIN_VAL;
		this.initPSOPositionRange = ACKLEY_INIT_MAX_VAL - ACKLEY_INIT_MIN_VAL;
		this.PSOSpeedMinVal = ACKLEY_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = ACKLEY_SPEED_MAX_VAL;
		this.PSOSpeedRange = ACKLEY_SPEED_MAX_VAL - ACKLEY_SPEED_MIN_VAL;
		this.name = Function.Name.ACKLEY;
	}

	// 	minimum is 0.0, which occurs at (0.0,...,0.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Ackley.ACKLEY_SHIFT_RANGE);
		DoubleVector v = DoubleVector.sub(y, shiftVector);
		double firstSum = 0.0;
		double secondSum = 0.0;
		for(int i = 0 ; i < v.size() ; ++i) {
			double xi = v.get(i);
			firstSum += xi * xi;
			secondSum += Math.cos(2.0*Math.PI*xi);
		}

		return -20.0 * Math.exp(-0.2 * Math.sqrt(firstSum/v.size())) - 
					Math.exp(secondSum/v.size()) + 20.0 + Math.E;   // + ackleyBiasValue;
	}
}
