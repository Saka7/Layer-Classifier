package edu.nnc.neuralNets;

import java.util.List;

public interface NeuralNet {
	
	/**
	 * Neural Network Training
	 * @param inputs
	 * @param expected - expected outputs
	 * @param learningRate - neural net's learning rate
	 * @param maxError - maximum error of neural net
	 * @param maxIterations - maximum number of iterations
	 * @return - error on each iteration
	 */
	String train(final double[][] inputs, final double[][] expected, double learningRate, double maxError,
			long maxIterations);

	/**
	 * Objects classification
	 * @param inputs
	 * @return - classified outputs
	 */
	double[] solve(final double[][] inputs);

	/**
	 * Saves weights to the file
	 * @param filename
	 */
	void saveWeights(String filename);

	/**
	 * Loads weights from the file
	 * @param filename
	 */
	void loadWeights(String filename);

	/** @return iterations */
	List<Integer> getIterations();

	/** @return error on each iteration */
	List<Double> getErrors();
	
	/** @return weights */
	String getWeights();
}