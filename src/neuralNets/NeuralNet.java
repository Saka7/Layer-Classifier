package neuralNets;

public interface NeuralNet {
	String train(final double[][] inputs, final double[][] expected, double learningRate, double maxError,
			long maxIterations);

	double[] solve(final double[][] inputs);

	void saveWeights(String filename);

	void loadWeights(String filename);
}
