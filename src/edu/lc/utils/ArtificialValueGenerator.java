package edu.lc.utils;

import java.util.Random;

/** 3 sigma artificial values generation */
public class ArtificialValueGenerator {
	
	/**
	 * 3 sigma artificial values generation
	 * @return - pseudo random number
	 */
	public static double getRandom3Sigma() {
		Random rand = new Random();
		double sum = 0;
		
		for (int i = 0; i < 20; i++)
			sum += rand.nextGaussian() * 1.1 + .7;
		
		return Math.round(Math.abs(((sum - 6) * 1.1 + .7))*10.0)/10.0;
	}
}