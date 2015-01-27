package TestFunctions;

import utils.DoubleVector;

public class Sphere extends Function {

	private static final double SPHERE_INIT_MIN_VAL = -100.0;
	private static final double SPHERE_INIT_MAX_VAL = 50.0;
	//	private static final double SPHERE_INIT_MIN_VAL = 50.0;      // from Pedersen paper: used when testing function shift of 25
	//	private static final double SPHERE_INIT_MAX_VAL = 100.0;     // from Pedersen paper: used when testing function shift of 25
	private static final double SPHERE_INIT_RANGE = SPHERE_INIT_MAX_VAL - SPHERE_INIT_MIN_VAL;

	private static final double SPHERE_SEARCH_SPACE_MIN_VAL = -100.0;
	private static final double SPHERE_SEARCH_SPACE_MAX_VAL = 100.0;
	//	private static final double SPHERE_SEARCH_SPACE_MIN_VAL = -100.0;
	//	private static final double SPHERE_SEARCH_SPACE_MAX_VAL = 100.0;
	private static final double SPHERE_SEARCH_SPACE_RANGE = SPHERE_SEARCH_SPACE_MAX_VAL - SPHERE_SEARCH_SPACE_MIN_VAL;

	private static final double SPHERE_SPEED_MIN_VAL = SPHERE_SEARCH_SPACE_MIN_VAL;
	private static final double SPHERE_SPEED_MAX_VAL = SPHERE_SEARCH_SPACE_MAX_VAL;
	private static final double SPHERE_SPEED_RANGE = SPHERE_SPEED_MAX_VAL - SPHERE_SPEED_MIN_VAL;

	private static double SPHERE_OPT_VALUE = 0.0;
	private static DoubleVector sphereShiftVector;

	public static final double SPHERE_OPT_COORD = 0.0; 
	public static final double SPHERE_SHIFT_RANGE = SPHERE_SEARCH_SPACE_MAX_VAL - SPHERE_OPT_COORD;
	
	public Sphere(int dimension) {
		super(dimension);
		this.initPSOParticlePositionVal = SPHERE_INIT_MIN_VAL;
		this.initPSOParticleSpeedVal = SPHERE_SPEED_MIN_VAL;
		this.initPSOPositionRange = SPHERE_INIT_MAX_VAL - SPHERE_INIT_MIN_VAL;
		this.PSOSpeedMinVal = SPHERE_SPEED_MIN_VAL;
		this.PSOSpeedMaxVal = SPHERE_SPEED_MAX_VAL;
		this.PSOSpeedRange = SPHERE_SPEED_MAX_VAL - SPHERE_SPEED_MIN_VAL;
		this.name = Function.Name.SPHERE;
	}

	// 	minimum is 0.0, which occurs at (0.0,...,0.0)
	public double eval(DoubleVector y) {
		shiftVector = new DoubleVector(y.size(), Sphere.SPHERE_SHIFT_RANGE);
		DoubleVector v = DoubleVector.sub(y, shiftVector);
		double sumSquares = 0.0;
		for(int i = 0 ; i < v.size() ; ++i) {
			double xi = v.get(i);
			sumSquares += xi * xi;
		}
		return sumSquares;   //  + sphereBiasValue;
	}
}
