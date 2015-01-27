// DO NOT USE THIS FUNCTION. THE IMPLEMENTATION IS INCORRECT RIGHT NOW.
// TODO(jerrickhoang): correct eval method.

package TestFunctions;

import utils.DoubleVector;

public class Schwefel extends Function {
	public static final int SCHWEFEL_FUNCTION_NUM = 0;

	private static final double SCHWEFEL_INIT_MIN_VAL = -500.0;
	private static final double SCHWEFEL_INIT_MAX_VAL = 500.0;
	private static final double SCHWEFEL_SEARCH_SPACE_MIN_VAL = -500.0;
	private static final double SCHWEFEL_SEARCH_SPACE_MAX_VAL = 500.0;
	private static final double SCHWEFEL_SEARCH_SPACE_RANGE = SCHWEFEL_SEARCH_SPACE_MAX_VAL - SCHWEFEL_SEARCH_SPACE_MIN_VAL;
	private static final double SCHWEFEL_SPEED_MIN_VAL = SCHWEFEL_SEARCH_SPACE_MIN_VAL;
	private static final double SCHWEFEL_SPEED_MAX_VAL = SCHWEFEL_SEARCH_SPACE_MAX_VAL;
	private static double SCHWEFEL_OPT_VALUE = 0.0;

	private static final double SCHWEFEL_OPT_COORD = -420.9687;
	public static final double SCHWEFEL_SHIFT_RANGE = -(SCHWEFEL_SEARCH_SPACE_MIN_VAL - SCHWEFEL_OPT_COORD);

	public Schwefel(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = SCHWEFEL_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = SCHWEFEL_SPEED_MIN_VAL;
		this.initPSOPositionRange = SCHWEFEL_INIT_MAX_VAL - SCHWEFEL_INIT_MIN_VAL;
		this.PSOSpeedMinVal = SCHWEFEL_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = SCHWEFEL_SPEED_MAX_VAL;
		this.PSOSpeedRange = SCHWEFEL_SPEED_MAX_VAL - SCHWEFEL_SPEED_MIN_VAL;
	}

	//  Schwefel Problem 2.26
	// 	minimum is 0.0, which occurs at (-420.9687,...,-420.9687)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Schwefel.SCHWEFEL_SHIFT_RANGE);
		DoubleVector v = DoubleVector.sub(y, shiftVector);
		double sum = 0;
		for(int i = 0 ; i < v.size() ; ++i) {
			double xi = v.get(i);
			sum += xi * Math.sin(Math.sqrt(Math.abs(xi)));
		}
		return 418.9829 * v.size() + sum;  // + schwefelBiasValue;
	}
}
