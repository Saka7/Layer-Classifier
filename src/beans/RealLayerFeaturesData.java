package beans;

public class RealLayerFeaturesData {
	protected int number;
	protected double sponginess;
	protected double amountOfClay;
	protected double amountOfCarbonate;
	protected double vPAmplitude;
	 
	 public RealLayerFeaturesData() {
		 this(0, 0.0, 0.0, 0.0, 0.0);
	 }
	 
	 public RealLayerFeaturesData(int number, double sponginess, double amountOfClay, double amountOfCarbonate, double vPAmplitude) {
		 this.number = number;
		 this.sponginess = sponginess;
		 this.amountOfClay = amountOfClay;
		 this.amountOfCarbonate = amountOfCarbonate;
		 this.vPAmplitude = vPAmplitude;
	 }
	 
	 @Override
	 public String toString() {
		 return String.format("%d,%f,%f,%f,%f\n", number, sponginess, amountOfClay, amountOfCarbonate, vPAmplitude);
	 }
	
}
