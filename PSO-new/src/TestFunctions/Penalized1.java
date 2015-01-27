package TestFunctions;

import utils.DoubleVector;

public class Penalized1 extends Function {

	// Penalized Function 1
	// 	minimum is 0.0, which occurs at (1.0,...,1.0)   // I think this is wrong even though it's what everyone says...
	// 	minimum is 0.0, which occurs at (-1.0,...,-1.0)
	public static final int PENALIZED_FUNCTION_1_NUM = 4;

	private static final double PENALIZED_FUNCTION_1_INIT_MIN_VAL = 5.0;
	private static final double PENALIZED_FUNCTION_1_INIT_MAX_VAL = 50.0;
	private static final double PENALIZED_FUNCTION_1_INIT_RANGE = PENALIZED_FUNCTION_1_INIT_MAX_VAL - PENALIZED_FUNCTION_1_INIT_MIN_VAL;

	private static final double PENALIZED_FUNCTION_1_SEARCH_SPACE_MIN_VAL = -50.0;
	private static final double PENALIZED_FUNCTION_1_SEARCH_SPACE_MAX_VAL = 50.0;
	private static final double PENALIZED_FUNCTION_1_SEARCH_SPACE_RANGE = PENALIZED_FUNCTION_1_SEARCH_SPACE_MAX_VAL -  PENALIZED_FUNCTION_1_SEARCH_SPACE_MIN_VAL;

	private static final double PENALIZED_FUNCTION_1_SPEED_MIN_VAL = PENALIZED_FUNCTION_1_SEARCH_SPACE_MIN_VAL;
	private static final double PENALIZED_FUNCTION_1_SPEED_MAX_VAL = PENALIZED_FUNCTION_1_SEARCH_SPACE_MAX_VAL;
	private static final double PENALIZED_FUNCTION_1_SPEED_RANGE = PENALIZED_FUNCTION_1_SPEED_MAX_VAL - PENALIZED_FUNCTION_1_SPEED_MIN_VAL;

	private static final double PENALIZED_FUNCTION_1_OPT_VALUE = 0.0;

	public static final double PENALIZED_FUNCTION_1_OPT_COORD = -1.0; 
	public static final double PENALIZED_FUNCTION_1_SHIFT_RANGE = -(PENALIZED_FUNCTION_1_SEARCH_SPACE_MIN_VAL - PENALIZED_FUNCTION_1_OPT_COORD);

	public Penalized1(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = PENALIZED_FUNCTION_1_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = PENALIZED_FUNCTION_1_SPEED_MIN_VAL;
		this.initPSOPositionRange = PENALIZED_FUNCTION_1_INIT_MAX_VAL - PENALIZED_FUNCTION_1_INIT_MIN_VAL;
		this.PSOSpeedMinVal = PENALIZED_FUNCTION_1_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = PENALIZED_FUNCTION_1_SPEED_MAX_VAL;
		this.PSOSpeedRange = PENALIZED_FUNCTION_1_SPEED_MAX_VAL - PENALIZED_FUNCTION_1_SPEED_MIN_VAL;
		this.name = Function.Name.PENALIZED1;
	}

	// 	minimum is 0.0, which occurs at (-1.0,...,-1.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Penalized1.PENALIZED_FUNCTION_1_SHIFT_RANGE);
		DoubleVector x = DoubleVector.sub(y, shiftVector);

		int xSize = x.size();

		double firstSum = 0.0;
		for(int i = 0 ; i < xSize - 1 ; ++i) {
			double yiAti = yi(x.get(i));
			double sinTerm = Math.sin(Math.PI*yi(x.get(i+1)));
			firstSum += (yiAti-1.0) * (yiAti-1.0) * (1.0 + 10.0*sinTerm*sinTerm);
		}

		double uSum = 0.0;
		for(int i = 0 ; i < xSize ; ++i) {
			uSum += u(x.get(i), 10.0, 100.0, 4.0);
		}

		double firstSinTerm = Math.sin(Math.PI*yi(x.get(0)));
		double yiAtnMinus1 = yi(x.get(xSize-1));

		// some papers have PI/30.0 (first coefficient), but more papers have PI/n  where n = number of dimensions
		return Math.PI/xSize * (10.0*firstSinTerm*firstSinTerm + firstSum + (yiAtnMinus1-1.0)*(yiAtnMinus1-1.0)) + uSum;   // + penalizedFunction1BiasValue;

	}
	private double yi(double xi) {
		return 1.0 + (xi + 1.0) / 4.0;
	}
	
	private double u(double xi, double a, double k, double m) {

		double retVal = 0;
		if (xi > a) {
			retVal = k * Math.pow(xi-a, m);
		}
		else if (xi <= a && xi >= -a) {
			retVal = 0.0;
		}
		else {  // xi < -a
			retVal = k * Math.pow(-xi-a, m);
		}

		return retVal;

	}

}
