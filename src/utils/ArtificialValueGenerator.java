package utils;

import java.util.Arrays;
import java.util.Random;

/**
 * Implements 3 sigma method
 */
public class ArtificialValueGenerator {
	private static Random rand = new Random();
	
	// TODO Reimplement this shit
	public static double getRandom3Sigma() {
		return Math.abs(Math.round((rand.nextGaussian() * 15 + 30) * 1e4)/1e4);
	}
	
	
	public static double getRandom3Sigma(double[] array) {
		double[] randomVals = new double[array.length];
		double sum = 0;
		double median = 0;
		double mean = 0;
		double variance = 0;
		Random gausianRandom = new Random();

		for (int i = 0; i < array.length; i++) {
			sum += array[i];
		}
		
		// find mean
		mean = sum / array.length;

		// sort array
		double[] sortedArray = Arrays.copyOf(array, array.length);
		Arrays.sort(sortedArray);
		System.out.println("Sorted array: " + Arrays.toString(sortedArray));

		// find median
		median = (sortedArray[sortedArray.length / 2 - 1] + sortedArray[sortedArray.length / 2]) / 2.0;
		System.out.println("Median: " + median);

		// find variance
		double sumsq = 0.0;
		for (int i = 0; i < array.length; i++)
			sumsq += Math.pow(array[i], 2);
		System.out.println(sumsq + " : " + mean);
		double diff = (sumsq - array.length * Math.pow(mean, 2));
		System.out.println("Diff = " + diff);
		variance = diff / (array.length);

		// find sum of normally distributed random values
		for (int i = 0; i < array.length; i++) {
			randomVals[i] = gausianRandom.nextGaussian();
			sum += randomVals[i];
		}

		return median + (sum + 6) * variance;
	}

	public static void main(String[] args) {
		double[] array = new double[15];
		for (int i = 0; i < array.length; i++) {
			array[i] = Math.random() * 15 + 15;
		}
		System.out.println(getRandom3Sigma(array));
	}
}