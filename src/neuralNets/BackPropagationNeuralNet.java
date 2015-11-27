package neuralNets;

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

public class BackPropagationNeuralNet {

	public static void calculate(final double[][] inputs, final double[][] expected, double learningRate,
			double maxError, long maxIterations) {

		// create a neural network, without using a factory
		BasicNetwork network = new BasicNetwork();
		network.addLayer(new BasicLayer(null, true, 4));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), true, 10));
		network.addLayer(new BasicLayer(new ActivationSigmoid(), false, 1));
		network.getStructure().finalizeStructure();
		network.reset();
		new ConsistentRandomizer(-1, 1, 500).randomize(network);
		System.out.println(network.dumpWeights());

		MLDataSet trainingSet = new BasicMLDataSet(inputs, expected);

		final Backpropagation train = new Backpropagation(network, trainingSet, learningRate, 0.3);
		train.fixFlatSpot(false);

		int epoch = 1;

		do {
			train.iteration();
			System.out.println("Epoch #" + epoch + " Error:" + train.getError());
			if (epoch++ > maxIterations)
				break;
		} while (train.getError() > maxError);

		// test the neural network
		System.out.println("Neural Network Results:");
		for (MLDataPair pair : trainingSet) {
			final MLData output = network.compute(pair.getInput());
			System.out.println(pair.getInput().getData(0) + "," + pair.getInput().getData(1) + ", actual="
					+ output.getData(0) + ",ideal=" + pair.getIdeal().getData(0));
		}

		Encog.getInstance().shutdown();
	}
}