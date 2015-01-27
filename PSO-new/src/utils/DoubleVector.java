package utils;

import java.util.Random;

public class DoubleVector {

	private double[] vector;  

	// CONSTRUCTORS
	public DoubleVector(int size) {
		vector = new double[size];
	}

	public DoubleVector(int size, double initVal) {
		vector = new double[size];
		for(int i = 0 ; i < size ; ++i)
			vector[i] = initVal;
	}

	// creates a DoubleVector that is a copy of the DoubleVector sent in
	public DoubleVector(DoubleVector v) {
		vector = new double[v.size()];
		for(int i = 0 ; i < v.size() ; ++i)
			vector[i] = v.get(i);
	}

	// create and return a DoubleVector with elements in (-maxMagnitude, +maxMagnitude),
	// i.e. not including -maxMagnitude or +maxMagnitude
	public static DoubleVector randomVector(int size, double maxMagnitude) {
		Random r = new Random();
		DoubleVector v = new DoubleVector(size);
		for(int i = 0 ; i < size ; ++i) {
			double element = r.nextDouble() * maxMagnitude;
			if (r.nextDouble() < 0.5)
				element *= -1.0;
			v.set(i, element);
		}
		return v;
	}


	
	// COPIERS
	public DoubleVector getCopy() {
		DoubleVector v = new DoubleVector(vector.length);
		for(int i = 0 ; i < vector.length ; ++i)
			v.set(i, vector[i]);
		return v;
	}

	public void copyFrom(DoubleVector v) {
		if (vector.length != v.size()) {
			System.out.println("error:  vectors not same size in DoubleVector.copyFrom(DoubleVector)");
			System.exit(0);
		}
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] = v.get(i);
	}


	// GETTER AND SETTER
	public double get(int i) {
		if (i < 0 || i >= vector.length) {
			System.out.println("error: index out of bounds in DoubleVector.get(int)");
			System.exit(0);
		}
		return vector[i];
	}

	public void set(int i, double value) {
		if (i < 0 || i >= vector.length) {
			System.out.println("error: index out of bounds in DoubleVector.set(int)");
			System.exit(0);
		}
		vector[i] = value;
	}

	// BASIC VECTOR OPERATIONS
	public int size() {
		return vector.length;
	}

	public double distance(DoubleVector v) {
		double sumSquareDiffs = 0.0;
		for(int i = 0 ; i < vector.length ; ++i) {
			double diff = vector[i] - v.get(i);
			sumSquareDiffs += diff * diff;
		}
		return Math.sqrt(sumSquareDiffs);				
	}

	public double mag() {
		double sumSquares = 0.0;
		for(int i = 0 ; i < vector.length ; ++i)
			sumSquares += vector[i] * vector[i];
		return Math.sqrt(sumSquares);		
	}

	public void normalize () {
		divScalar(mag());
	}
	
	public boolean equal(DoubleVector v, double tolerance) {
		for(int i = 0 ; i < vector.length ; ++i) {	
			if (Math.abs(vector[i] - v.get(i)) > tolerance)
				return false;
		}
		return true;
	}


	// ADDITION
	public void addScalar (double scalar) {
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] += scalar;
	}

	public void addVector (DoubleVector v) {
		if (vector.length != v.size()) {
			System.out.println("error:  vectors not same size in DoubleVector.add(DoubleVector)");
			System.exit(0);
		}
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] += v.get(i);
	}

	// add two vectors and return a third vector that is the sum
	public static DoubleVector add(DoubleVector v1, DoubleVector v2) {
		if (v1.size() != v2.size()) {
			System.out.println("error:  vectors not same size in DoubleVector.add(DoubleVector, DoubleVector");
			System.exit(0);
		}
		DoubleVector v = new DoubleVector(v1.size());
		for(int i = 0 ; i < v.size() ; ++i)
			v.set(i, v1.get(i) + v2.get(i));
		return v;
	}

	// add a random scalar in (-magnitude, +magnitude) to each element
	// i.e. not including -magnitude or +magnitude
	public void addRandomScalarMagnitude(double magnitude) {
		Random r = new Random();
		for(int i = 0 ; i < vector.length ; ++i) {
			double element = r.nextDouble() * magnitude;
			if (r.nextDouble() < 0.5)
				element *= -1.0;
			vector[i] += element;
		}
	}

	// SUBTRACTION
	public void subScalar (double scalar) {
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] -= scalar;
	}

	public void subVector (DoubleVector v) {
		if (vector.length != v.size()) {
			System.out.println("error:  vectors not same size in DoubleVector.sub(DoubleVector)");
			System.exit(0);
		}
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] -= v.get(i);
	}

	// subtract two vectors and return a third vector that is the result
	public static DoubleVector sub(DoubleVector v1, DoubleVector v2) {
		if (v1.size() != v2.size()) {
			System.out.println("error:  vectors not same size in DoubleVector.vectorSub");
			System.exit(0);
		}
		DoubleVector v = new DoubleVector(v1.size());
		for(int i = 0 ; i < v.size() ; ++i)
			v.set(i, v1.get(i) - v2.get(i));
		return v;
	}

	// MULTIPLICATION
	public void multScalar (double scalar) {
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] *= scalar;
	}

	
	// multiply each element of the vector by a value in [lowVal, highVal)
	public void multRandomScalar(double lowVal, double highVal) {
		Random r = new Random();
		double range = highVal - lowVal;
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] *= lowVal + (r.nextDouble() * range);
	}

	// multiply a vector by a scalar and return a second vector that is the result
	public static DoubleVector multVectorScalar (DoubleVector vectorIn, double scalar) {
		DoubleVector v = new DoubleVector(vectorIn.size());
		for(int i = 0 ; i < v.size() ; ++i)
			v.set(i, vectorIn.get(i) * scalar);
		return v;
	}

	// DIVISION
	public static DoubleVector divVectorScalar (DoubleVector vectorIn, double scalar) {
		DoubleVector v = new DoubleVector(vectorIn.size());
		for(int i = 0 ; i < v.size() ; ++i)
			v.set(i, vectorIn.get(i) / scalar);
		return v;
	}

	public void divScalar (double scalar) {
		for(int i = 0 ; i < vector.length ; ++i)
			vector[i] /= scalar;
	}

	// PRINTING
	public void println() {
		print();
		System.out.println();
	}

	public void print() {
		System.out.print("("); 
		for(int i = 0 ; i < vector.length ; ++i) {
			if (vector[i] >= 0.0)
				System.out.printf(" %.4e", vector[i]);
			else
				System.out.printf("%.4e", vector[i]);
			if (i < vector.length - 1)
				System.out.print(", ");
		}
		System.out.print(")");
	}
}

