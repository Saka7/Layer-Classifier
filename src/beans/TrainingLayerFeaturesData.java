package beans;

public class TrainingLayerFeaturesData extends RealLayerFeaturesData {
	private int type;

	public TrainingLayerFeaturesData(int number, double sponginess, double amountOfClay, double amountOfCarbonate,
			double vPAmplitude, int type) {
		super(number, sponginess, amountOfClay, amountOfCarbonate, vPAmplitude);
		this.type = type;
	}

	@Override
	public String toString() {
		return String.format("%d,%f,%f,%f,%f,%d\n", super.number, super.sponginess, super.amountOfClay,
				super.amountOfCarbonate, super.vPAmplitude, type);
	}

}
