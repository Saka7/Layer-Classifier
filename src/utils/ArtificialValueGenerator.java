package utils;

import java.util.Arrays;
import java.util.Random;

/**
 * Реалізація методу генерації штучних змінних за правилом 3 сігма
 */
public class ArtificialValueGenerator {
	private static Random rand = new Random();

	// TODO Reimplement this shit
	public static double[] getRandom3Sigma(final double[][] samples) {
		
		// Show matrix ============================================
		for (int i = 0; i < samples.length; i++) {
			for (int j = 0; j < samples[i].length; j++) {
				System.out.println(samples[i][j]);
			}
			System.out.println();
		}
		//============================================

		int items = samples[0].length;
		int rows = samples.length;

		double[] randValue = new double[items];
		double[] median = new double[items];
		double[] variance = new double[items];
		double[] standartDeviation = new double[items];
		double[] average = new double[items];
		double[] sum = new double[items];

		// Find average
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < items; j++) {
				average[j] += samples[i][j];
			}
		}

		for (int j = 0; j < items; j++) {
			average[j] /= rows;
		}
		
		// Show average ============================================
		
		System.out.println("Average: " + Arrays.toString(average));
		
		// Show ============================================

		// Find variance
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < items; j++) {
				variance[j] += Math.pow(samples[i][j] - average[j], 2);
			}
		}

		for (int i = 0; i < items; i++) {
			variance[i] /= rows;
			standartDeviation[i] = Math.sqrt(variance[i]);
		}
		
		// Show variance and standard deviation ===================
		System.out.println("Standard deviation : " + Arrays.toString(standartDeviation));
		System.out.println("Variance : " + Arrays.toString(variance));
		// Show ============================================

		double[][] temp = new double[items][rows];
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < items; j++) {
				temp[j][i] = samples[i][j];
			}
		}

		// Find median
		for (int i = 0; i < items; i++) {
			Arrays.sort(temp[i]);
			if (rows % 2 == 0) {
				median[i] = (temp[i][rows / 2] + temp[i][rows / 2 - 1]) / 2;
			} else {
				median[i] = temp[i][rows / 2];
			}
		}
		
		for (int i = 0; i < items; i++) {
			System.out.println("Sorted array: " + Arrays.toString(temp[i]));
		}

		// Show median =================================================
		System.out.println("Median: " + Arrays.toString(median));
		// =============================================================
		
		for (int i = 0; i < items; i++) {
			randValue[i] = median[i] + (sum[i] - 6) * standartDeviation[i];
		}

		return randValue;
	}

	public static void main(String[] args) {
		final double[][] samples = new double[6][4];

		for (int i = 0; i < samples.length; i++) {
			for (int j = 0; j < samples[i].length; j++) {
				samples[i][j] = Math.random() * 5 + 5;
			}
		}

		System.out.println(Arrays.toString(getRandom3Sigma(samples)));

	}
}