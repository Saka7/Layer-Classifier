package neuralNets;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.MLDataSet;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.back.Backpropagation;
import org.encog.persist.EncogDirectoryPersistence;

public class BackPropagationNeuralNet implements NeuralNet {

	private BasicNetwork network;
	private MLDataSet trainingSet;
	private Backpropagation train;

	private List<Integer> iterations;
	private List<Double> errors;

	public void saveWeights(String filename) {
		if (!filename.substring(filename.length() - 3, filename.length()).equals(".eg")) {
			filename += ".eg";
		}
		EncogDirectoryPersistence.saveObject(new File(filename), network);
	}

	public void loadWeights(String filename) {
		network = (BasicNetwork) EncogDirectoryPersistence.loadObject(new File(filename));
	}

	private double learningRate;

	public BackPropagationNeuralNet() {
		iterations = new ArrayList<>();
		errors = new ArrayList<>();
		
		network = new BasicNetwork();
		network.addLayer(new BasicLayer(null, true, 4));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 10));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
		network.getStructure().finalizeStructure();
		network.reset();
		new ConsistentRandomizer(-1, 1, 500).randomize(network);
	}

	public BackPropagationNeuralNet(String filename) {
		loadWeights(filename);
	}

	public String train(final double[][] inputs, final double[][] expected, double learningRate, double maxError,
			long maxIterations) {

		this.learningRate = learningRate;
		trainingSet = new BasicMLDataSet(inputs, expected);
		train = new Backpropagation(network, trainingSet, this.learningRate, 0.3);
		train.fixFlatSpot(false);

		int epoch = 0;

		do {
			iterations.add(new Integer(epoch));
			
			train.iteration();
			
			errors.add(new Double(train.getError()));
			
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			if (epoch++ > maxIterations)
				break;
		} while (train.getError() > maxError);

		return new String(epoch + " " + train.getError());
	}

	public double[] solve(final double[][] inputs) {
		double[] results = new double[inputs.length];
		trainingSet = new BasicMLDataSet(inputs, null);
		int i = 0;
		for (MLDataPair pair : trainingSet) {
			final MLData output = network.compute(pair.getInput());
			results[i++] = output.getData(0);
		}

		Encog.getInstance().shutdown();
		return results;
	}

	@Override
	public List<Integer> getIterations() {
		return iterations;
	}

	@Override
	public List<Double> getErrors() {
		return errors;
	}

	@Override
	public String getWeights() {
		return network.dumpWeights();
	}
}