package neuralNets;

import java.util.ArrayList;

import org.encog.Encog;
import org.encog.engine.network.activation.ActivationSigmoid;
import org.encog.mathutil.randomize.ConsistentRandomizer;
import org.encog.ml.data.MLData;
import org.encog.ml.data.MLDataPair;
import org.encog.ml.data.basic.BasicMLDataSet;
import org.encog.neural.networks.BasicNetwork;
import org.encog.neural.networks.layers.BasicLayer;
import org.encog.neural.networks.training.propagation.resilient.ResilientPropagation;

public class ExtendedDeltaBarDeltaNeuralNet extends BackPropagationNeuralNet implements NeuralNet {

	private ResilientPropagation train;

	public ExtendedDeltaBarDeltaNeuralNet() {
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

	public ExtendedDeltaBarDeltaNeuralNet(String filename) {
		this();
		loadWeights(filename);
	}

	public String train(final double[][] inputs, final double[][] expected, double learningRate, double maxError,
			long maxIterations) {

		this.learningRate = learningRate;
		trainingSet = new BasicMLDataSet(inputs, expected);
		train = new ResilientPropagation(network, trainingSet, this.learningRate, 0.3);
		train.fixFlatSpot(false);

		int epoch = 0;

		do {
			iterations.add(new Integer(epoch));
			train.iteration();
			errors.add(new Double(train.getError()));
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			if (epoch++ > maxIterations) break;
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
}