package TestFunctions;

import utils.DoubleVector;

public class Griewank extends Function {

	// have also seen (300.0, 600.0) for initialization and (-600.0, 600.0) for search range
	private static final double GRIEWANK_INIT_MIN_VAL = -600.0;
	private static final double GRIEWANK_INIT_MAX_VAL = 200.0;
	private static final double GRIEWANK_INIT_RANGE = GRIEWANK_INIT_MAX_VAL - GRIEWANK_INIT_MIN_VAL;

	private static final double GRIEWANK_SEARCH_SPACE_MIN_VAL = -600.0;
	private static final double GRIEWANK_SEARCH_SPACE_MAX_VAL = 600.0;
	private static final double GRIEWANK_SEARCH_SPACE_RANGE = GRIEWANK_SEARCH_SPACE_MAX_VAL -  GRIEWANK_SEARCH_SPACE_MIN_VAL;

	private static final double GRIEWANK_SPEED_MIN_VAL = GRIEWANK_SEARCH_SPACE_MIN_VAL;
	private static final double GRIEWANK_SPEED_MAX_VAL = GRIEWANK_SEARCH_SPACE_MAX_VAL;
	private static final double GRIEWANK_SPEED_RANGE = GRIEWANK_SPEED_MAX_VAL - GRIEWANK_SPEED_MIN_VAL;

	private static final double GRIEWANK_OPT_VALUE = 0.0;

	public static final double GRIEWANK_OPT_COORD = 0.0; 
	public static final double GRIEWANK_SHIFT_RANGE = GRIEWANK_SEARCH_SPACE_MAX_VAL - GRIEWANK_OPT_COORD;
	

	public Griewank(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = GRIEWANK_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = GRIEWANK_SPEED_MIN_VAL;
		this.initPSOPositionRange = GRIEWANK_INIT_MAX_VAL - GRIEWANK_INIT_MIN_VAL;
		this.PSOSpeedMinVal = GRIEWANK_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = GRIEWANK_SPEED_MAX_VAL;
		this.PSOSpeedRange = GRIEWANK_SPEED_MAX_VAL - GRIEWANK_SPEED_MIN_VAL;
		this.name = Function.Name.GRIEWANK;
	}

	// 	minimum is 0.0, which occurs at (0.0,...,0.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Griewank.GRIEWANK_SHIFT_RANGE);
		DoubleVector v = DoubleVector.sub(y, shiftVector);
		double sumSquares = 0.0;
		double productCos = 1.0;
		for(int i = 0 ; i < v.size() ; ++i) {
			double xi = v.get(i);
			sumSquares += xi * xi;
			productCos *= Math.cos(xi/Math.sqrt(i+1));
		}
		return sumSquares/4000.0 - productCos + 1.0;   // + griewankBiasValue;

	}
}
