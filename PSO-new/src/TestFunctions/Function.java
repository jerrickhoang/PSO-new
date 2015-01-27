package TestFunctions;

import utils.DoubleVector;

public abstract class Function {
	
	public enum Name {
		SPHERE("Sphere"), ACKLEY("Ackley"), GRIEWANK("Griewank"),
		PENALIZED1("Penalized1"), PENALIZED2("Penalized2"), RASTRIGIN("Rastrigin"),
		ROSENBROCK("Rosenbrock");
		
		private final String name;
		
		private Name(String s) {
			this.name = s;
		}
		
		public String toString() {
			return name;
		}
	}
	
	public static final int VAL_INDEX = 0;
	public static final int ERR_INDEX = 1;
	
	public double initPSOParticlePositionVal;
	public double initPSOParticleSpeedVal;
	public double initPSOPositionRange;
	public double PSOSpeedRange;
	public int dimension;
	public double PSOSpeedMinVal;
	public double PSOSpeedMaxVal;
	public DoubleVector shiftVector;
	public double opt_value = 0.0;
	public Function.Name name;

	public Function(int dimension) {
		this.dimension = dimension;
	}
	
	public double[] evalWithError(DoubleVector v) {
		double value = eval(v);
		double error = value - opt_value;  // schwefelShiftedOptValue;
		double[] retArray = new double[2];
		retArray[VAL_INDEX] = value;
		retArray[ERR_INDEX] = error;
		return retArray;
	}
	public double eval(DoubleVector v) { return 0.0; }
}
