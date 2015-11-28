package utils;

import java.util.Random;

/** Реалізація методу генерації штучних змінних за правилом 3 сігма */
public class ArtificialValueGenerator {
	
	/**
	 * Генерація штучних змінних за правилом 3-ох сігма
	 * @return - псевдовипадкове число 
	 */
	public static double getRandom3Sigma() {
		Random rand = new Random();
		double sum = 0;
		
		/* Сумування 20-ти випадкових чисел із нормального розподілу
		   медіана = 0.7; дисперсія = 1.1 */
		for (int i = 0; i < 20; i++)
			sum += rand.nextGaussian() * 1.1 + .7;
		
		return Math.round(Math.abs(((sum - 6) * 1.1 + .7))*10.0)/10.0;
	}
}