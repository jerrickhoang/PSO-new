package TestFunctions;

import utils.DoubleVector;

public class Rastrigin extends Function {

	//  Rastrigin Function
	// 	minimum is 0.0, which occurs at (0.0,...,0.0)
	public static final int RASTRIGIN_FUNCTION_NUM = 1;

	private static final double RASTRIGIN_INIT_MIN_VAL = -5.12;
	private static final double RASTRIGIN_INIT_MAX_VAL = 2.0;
	private static final double RASTRIGIN_INIT_RANGE = RASTRIGIN_INIT_MAX_VAL - RASTRIGIN_INIT_MIN_VAL;

	private static final double RASTRIGIN_SEARCH_SPACE_MIN_VAL = -5.12;
	private static final double RASTRIGIN_SEARCH_SPACE_MAX_VAL = 5.12;
	private static final double RASTRIGIN_SEARCH_SPACE_RANGE = RASTRIGIN_SEARCH_SPACE_MAX_VAL - RASTRIGIN_SEARCH_SPACE_MIN_VAL;

	private static final double RASTRIGIN_SPEED_MIN_VAL = RASTRIGIN_SEARCH_SPACE_MIN_VAL;
	private static final double RASTRIGIN_SPEED_MAX_VAL = RASTRIGIN_SEARCH_SPACE_MAX_VAL;
	private static final double RASTRIGIN_SPEED_RANGE = RASTRIGIN_SPEED_MAX_VAL - RASTRIGIN_SPEED_MIN_VAL;

	private static final double RASTRIGIN_OPT_VALUE = 0.0;

	public static final double RASTRIGIN_OPT_COORD = 0.0; 
	public static final double RASTRIGIN_SHIFT_RANGE = RASTRIGIN_SEARCH_SPACE_MAX_VAL - RASTRIGIN_OPT_COORD;
	
	public Rastrigin(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = RASTRIGIN_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = RASTRIGIN_SPEED_MIN_VAL;
		this.initPSOPositionRange = RASTRIGIN_INIT_MAX_VAL - RASTRIGIN_INIT_MIN_VAL;
		this.PSOSpeedMinVal = RASTRIGIN_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = RASTRIGIN_SPEED_MAX_VAL;
		this.PSOSpeedRange = RASTRIGIN_SPEED_MAX_VAL - RASTRIGIN_SPEED_MIN_VAL;
		this.name = Function.Name.RASTRIGIN;
	}

	// 	minimum is 0.0, which occurs at (0.0,...,0.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Rastrigin.RASTRIGIN_SHIFT_RANGE);
		DoubleVector v = DoubleVector.sub(y, shiftVector);
		double retVal = 0;
		for(int i = 0 ; i < v.size() ; ++i) {
			double xi = v.get(i);
			retVal += xi*xi - 10.0*Math.cos(2.0*Math.PI*xi) + 10.0;
		}
		return retVal;  // + rastriginBiasValue;

	}
}
