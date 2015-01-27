package TestFunctions;

import utils.DoubleVector;

public class Rosenbrock extends Function {

	//  Rosenbrock Function
	// 	minimum is 0.0, which occurs at (1.0,...,1.0)
	public static final int ROSENBROCK_FUNCTION_NUM = 7;

	private static final double ROSENBROCK_INIT_MIN_VAL = -2.048;
	private static final double ROSENBROCK_INIT_MAX_VAL = 2.048;
	//	private static final double ROSENBROCK_INIT_MIN_VAL = 15.0;        // from Pedersen paper: used when testing function shift of 25
	//	private static final double ROSENBROCK_INIT_MAX_VAL = 30.0;        // from Pedersen paper: used when testing function shift of 25
	private static final double ROSENBROCK_INIT_RANGE = ROSENBROCK_INIT_MAX_VAL - ROSENBROCK_INIT_MIN_VAL;

	private static final double ROSENBROCK_SEARCH_SPACE_MIN_VAL = -2.048;
	private static final double ROSENBROCK_SEARCH_SPACE_MAX_VAL = 2.048;
	//	private static final double ROSENBROCK_SEARCH_SPACE_MIN_VAL = -100.0;     // from Pedersen paper: used when testing function shift of 25
	//	private static final double ROSENBROCK_SEARCH_SPACE_MAX_VAL = 100.0;      // from Pedersen paper: used when testing function shift of 25
	private static final double ROSENBROCK_SEARCH_SPACE_RANGE =ROSENBROCK_SEARCH_SPACE_MAX_VAL - ROSENBROCK_SEARCH_SPACE_MIN_VAL;

	private static final double ROSENBROCK_SPEED_MIN_VAL = ROSENBROCK_SEARCH_SPACE_MIN_VAL;
	private static final double ROSENBROCK_SPEED_MAX_VAL = ROSENBROCK_SEARCH_SPACE_MAX_VAL;
	private static final double ROSENBROCK_SPEED_RANGE = ROSENBROCK_SPEED_MAX_VAL - ROSENBROCK_SPEED_MIN_VAL;

	private static final double ROSENBROCK_OPT_VALUE = 0.0;

	public static final double ROSENBROCK_OPT_COORD = 1.0; 
	public static final double ROSENBROCK_SHIFT_RANGE = ROSENBROCK_SEARCH_SPACE_MAX_VAL - ROSENBROCK_OPT_COORD;

	public Rosenbrock(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = ROSENBROCK_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = ROSENBROCK_SPEED_MIN_VAL;
		this.initPSOPositionRange = ROSENBROCK_INIT_MAX_VAL - ROSENBROCK_INIT_MIN_VAL;
		this.PSOSpeedMinVal = ROSENBROCK_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = ROSENBROCK_SPEED_MAX_VAL;
		this.PSOSpeedRange = ROSENBROCK_SPEED_MAX_VAL - ROSENBROCK_SPEED_MIN_VAL;
		this.name = Function.Name.ROSENBROCK;
	}

	// 	minimum is 0.0, which occurs at (1.0,...,1.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Rosenbrock.ROSENBROCK_SHIFT_RANGE);
		DoubleVector v = DoubleVector.sub(y, shiftVector);
		double retVal = 0;
		// NOTE:  LAST DIMENSION SHOULD *NOT* BE INCLUDED IN CALCULATION
		for(int i = 0 ; i < v.size() - 1 ; ++i) {
			double xi = v.get(i);
			double xiPlusOne = v.get(i+1);
			retVal += 100.0 * Math.pow(xiPlusOne - xi*xi, 2.0) + Math.pow(xi-1.0, 2.0);
		}
		return retVal; // + rosenbrockBiasValue;

	}
}
