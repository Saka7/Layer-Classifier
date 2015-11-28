package neuralNets;

import java.util.List;

/** Інтерфейс нейромереж */
public interface NeuralNet {
	
	/**
	 * Тренування нейромережі
	 * @param inputs - входи
	 * @param expected - очікувані виходи
	 * @param learningRate - швидкість навчання
	 * @param maxError - максимальна похибка
	 * @param maxIterations - максимальна к-сть ітерацій
	 * @return - помилки на кожній ітерації
	 */
	String train(final double[][] inputs, final double[][] expected, double learningRate, double maxError,
			long maxIterations);

	/**
	 * Класифікація образів нейромережею
	 * @param inputs - входи
	 * @return - виходи
	 */
	double[] solve(final double[][] inputs);

	/**
	 * Збереження ваг у файл
	 * @param filename - назва файлу
	 */
	void saveWeights(String filename);

	/**
	 * Завантаження нейромереж із файлу
	 * @param filename - назва файлу
	 */
	void loadWeights(String filename);

	/** @return ітерації */
	List<Integer> getIterations();

	/** @return помилки на кожній ітерації */
	List<Double> getErrors();
	
	/** @return ваги */
	String getWeights();
}