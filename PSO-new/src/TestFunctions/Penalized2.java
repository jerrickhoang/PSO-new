package TestFunctions;

import utils.DoubleVector;

public class Penalized2 extends Function {

	// Penalized Function 2
	// 	minimum is 0.0, which occurs at (1.0,...,1.0)
	public static final int PENALIZED_FUNCTION_2_NUM = 5;

	private static final double PENALIZED_FUNCTION_2_INIT_MIN_VAL = 5.0;
	private static final double PENALIZED_FUNCTION_2_INIT_MAX_VAL = 50.0;
	private static final double PENALIZED_FUNCTION_2_INIT_RANGE = PENALIZED_FUNCTION_2_INIT_MAX_VAL - PENALIZED_FUNCTION_2_INIT_MIN_VAL;

	private static final double PENALIZED_FUNCTION_2_SEARCH_SPACE_MIN_VAL = -50.0;
	private static final double PENALIZED_FUNCTION_2_SEARCH_SPACE_MAX_VAL = 50.0;
	private static final double PENALIZED_FUNCTION_2_SEARCH_SPACE_RANGE = PENALIZED_FUNCTION_2_SEARCH_SPACE_MAX_VAL -  PENALIZED_FUNCTION_2_SEARCH_SPACE_MIN_VAL;

	private static final double PENALIZED_FUNCTION_2_SPEED_MIN_VAL = PENALIZED_FUNCTION_2_SEARCH_SPACE_MIN_VAL;
	private static final double PENALIZED_FUNCTION_2_SPEED_MAX_VAL = PENALIZED_FUNCTION_2_SEARCH_SPACE_MAX_VAL;
	private static final double PENALIZED_FUNCTION_2_SPEED_RANGE = PENALIZED_FUNCTION_2_SPEED_MAX_VAL - PENALIZED_FUNCTION_2_SPEED_MIN_VAL;

	private static final double PENALIZED_FUNCTION_2_OPT_VALUE = 0.0;

	public static final double PENALIZED_FUNCTION_2_OPT_COORD = 1.0; 
	public static final double PENALIZED_FUNCTION_2_SHIFT_RANGE = PENALIZED_FUNCTION_2_SEARCH_SPACE_MAX_VAL - PENALIZED_FUNCTION_2_OPT_COORD;
	
	public Penalized2(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = PENALIZED_FUNCTION_2_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = PENALIZED_FUNCTION_2_SPEED_MIN_VAL;
		this.initPSOPositionRange = PENALIZED_FUNCTION_2_INIT_MAX_VAL - PENALIZED_FUNCTION_2_INIT_MIN_VAL;
		this.PSOSpeedMinVal = PENALIZED_FUNCTION_2_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = PENALIZED_FUNCTION_2_SPEED_MAX_VAL;
		this.PSOSpeedRange = PENALIZED_FUNCTION_2_SPEED_MAX_VAL - PENALIZED_FUNCTION_2_SPEED_MIN_VAL;
		this.name = Function.Name.PENALIZED2;
	}

	// 	minimum is 0.0, which occurs at (-1.0,...,-1.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Penalized2.PENALIZED_FUNCTION_2_SHIFT_RANGE);
		DoubleVector x = DoubleVector.sub(y, shiftVector);

		int xSize = x.size();

		double firstSum = 0.0;
		for(int i = 0 ; i < xSize - 1 ; ++i) {
			double xi = x.get(i);
			double sinTerm = Math.sin(3.0*Math.PI*x.get(i+1));
			firstSum += (xi-1.0)*(xi-1.0) * (1.0 + sinTerm*sinTerm);
		}

		double uSum = 0.0;
		for(int i = 0 ; i < xSize ; ++i) {
			uSum += u(x.get(i), 5.0, 100.0, 4.0);
		}

		double firstSinTerm = Math.sin(3.0*Math.PI*x.get(0));
		double lastXi = x.get(xSize-1);
		double lastSinTerm = Math.sin(2.0*Math.PI*lastXi);
		return 0.1 * (firstSinTerm*firstSinTerm + firstSum + (lastXi-1.0)*(lastXi-1.0) * (1.0 + lastSinTerm*lastSinTerm)) + uSum ;  // + penalizedFunction2BiasValue;

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
